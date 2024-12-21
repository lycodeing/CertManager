package cn.lycodeing.cert.task.task.ssl;

import cn.lycodeing.cert.common.context.CertTaskData;
import cn.lycodeing.cert.common.context.Context;
import cn.lycodeing.cert.common.enums.DnsEnum;
import cn.lycodeing.cert.task.client.ssl.LetsEncryptCertClient;
import cn.lycodeing.cert.task.client.ssl.ZeroSslCertClient;
import cn.lycodeing.cert.task.constant.CommonConstant;
import cn.lycodeing.cert.task.dns.DNSProviderFactory;
import cn.lycodeing.cert.task.dns.DNSProviderFactoryUtils;
import cn.lycodeing.cert.task.task.Task;
import cn.lycodeing.cert.task.utils.GsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.shredzone.acme4j.*;
import org.shredzone.acme4j.challenge.Challenge;
import org.shredzone.acme4j.challenge.Dns01Challenge;
import org.shredzone.acme4j.exception.AcmeException;
import org.shredzone.acme4j.util.KeyPairUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.List;

/**
 *  创建证书任务
 * @author lycodeing
 */
@Data
@Slf4j
public class CreateCertTask implements Task {

    private static final Duration TIMEOUT = Duration.ofSeconds(180);

    private static final String TYPE = "TXT";

    private DNSProviderFactory dnsProviderFactory;
    private Context context;
    protected CertTaskData certData;

    private void setDnsProviderFactory(DnsEnum dns, String accessKey, String accessSecret) {
        try {
            dnsProviderFactory = DNSProviderFactoryUtils.createDnsProviderFactory(dns, accessKey, accessSecret);
        } catch (IOException e) {
            handleException("Failed to query the Dns resolution provider due to I/O error", e, dns, accessKey);
        } catch (IllegalArgumentException e) {
            handleException("Failed to query the Dns resolution provider due to invalid argument", e, dns, accessKey);
        } catch (Exception e) {
            handleException("Failed to query the Dns resolution provider", e, dns, accessKey);
        }
    }

    private void handleException(String message, Exception e, DnsEnum dns, String accessKey) {
        String maskedAccessKey = maskSensitiveInfo(accessKey);
        log.error("{} (DNS: {}, AccessKey: {}), {}", message, dns, maskedAccessKey, e.getMessage(), e);
        throw new RuntimeException(message, e);
    }

    private String maskSensitiveInfo(String info) {
        if (info == null || info.isEmpty()) {
            return info;
        }
        int length = info.length();
        int visibleLength = Math.min(4, length);
        return info.substring(0, visibleLength) + "*".repeat(length - visibleLength);
    }


    @Override
    public int execute(Context context) {
        this.context = context;
        CertTaskData certData = GsonUtil.fromJson(context.getData(), CertTaskData.class);

        if (!certData.isValid()) {
            log.error("The certificate data is invalid, please check the certificate data");
            throw new RuntimeException("The certificate data is invalid, please check the certificate data");
        }
        long startTime = System.currentTimeMillis();
        log.info("The current certificate vendor is:{}", certData.getCertProvider().getType());
        setDnsProviderFactory(DnsEnum.valueOf(certData.getDns().getDnsType()), certData.getDns().getAccessKey(), certData.getDns().getAccessSecret());
        // 查询当前申请证书的记录
        try {
            Certificate certificate = obtainCertificate(certData.getCertProvider().getType().getCaURI(), certData.getDomain(), certData.getCertPath(), certData.getEmail(), certData.getDomains(), certData.getCertProvider().getApiKey());
            log.info("Success! The certificate for domains {} has been generated!", certData.getDomains());
            log.info("Certificate URL: {}", certificate.getLocation());
        } catch (Exception ex) {
            log.error("Failed to generate certificate for domains {},{}", certData.getDomain(), ex.getMessage(), ex);
            throw new RuntimeException("Failed to generate certificate for domains " + certData.getDomain());
        }
        log.info("Total time: {} ms", System.currentTimeMillis() - startTime);
        log.info("Certificate generation completed");
        // 创建后置处理器，用于处理完成以后的数据，例如保存输出的数据
        return 0;
    }

    private Certificate obtainCertificate(String caUrl, String domain, String certPath, String email, List<String> domains, String apiKey) throws Exception {
        KeyPair rsaKeyPair = createKeyPair();

        Session session = new Session(caUrl);

        Account acct = switch (certData.getCertProvider().getType()) {
            case LETS_ENCRYPT -> LetsEncryptCertClient.findOrRegisterAccount(session, rsaKeyPair, email, apiKey);
            case ZERO_SSL -> ZeroSslCertClient.findOrRegisterAccount(session, rsaKeyPair, email, apiKey);
        };

        KeyPair domainKeyPair = createKeyPair();
        Order order = acct.newOrder().domains(domains).create();

        authorizeDomains(order, domain);

        order.execute(domainKeyPair);

        order.waitForCompletion(TIMEOUT);

        validateOrder(order);

        Certificate certificate = order.getCertificate();

        writeCertificates(certificate, domainKeyPair, certPath, domain);
        return certificate;
    }

    private void validateOrder(Order order) throws AcmeException {
        if (!order.getStatus().equals(Status.VALID)) {
            log.error("Order has failed, reason: {}", order.getError());
            throw new AcmeException("Order failed... Giving up.");
        }
    }

    private void authorizeDomains(Order order, String domain) throws Exception {
        for (Authorization auth : order.getAuthorizations()) {
            authorize(auth, domain);
        }
        order.waitUntilReady(TIMEOUT);
    }


    private void authorize(Authorization auth, String domain) throws Exception {

        String dnsRecord = createDnsRecordName(domain, auth.getIdentifier().getDomain());
        log.info("Checking authorization for dnsRecord {}", dnsRecord);
        try {
            Challenge challenge = checkAndTriggerChallenge(auth, domain, dnsRecord);
            if (challenge == null || challenge.getStatus() != Status.VALID) {
                String errorMsg = challenge != null ? challenge.getError()
                        .map(Problem::getDetail)
                        .map(String::valueOf)
                        .orElse("unknown") : "unknown";
                log.error("Challenge for domain {} failed, reason: {}", auth.getIdentifier().getDomain(), errorMsg);
                throw new AcmeException(errorMsg);
            }
        } catch (Exception e) {
            log.error("Challenge for domain {} failed, reason: {}", auth.getIdentifier().getDomain(), e.getMessage(), e);
            throw new AcmeException("Challenge failed... Giving up.");
        } finally {
            dnsProviderFactory.deleteSubDomainRecord();
            log.info("DeleteSubDomainRecord type:{} ,domainName:{} , dnsRecord:{} SUCCESS", TYPE, domain, dnsRecord);
        }
        log.info("Challenge for domain {} has been completed", auth.getIdentifier().getDomain());
    }

    private Challenge checkAndTriggerChallenge(Authorization auth, String domain, String dnsRecord) throws Exception {
        if (auth.getStatus() != Status.PENDING) {
            return null;
        }

        Dns01Challenge challenge = auth.findChallenge(Dns01Challenge.TYPE).map(Dns01Challenge.class::cast).orElse(null);
        if (challenge == null) {
            throw new AcmeException("No DNS challenge found for the domain " + auth.getIdentifier().getDomain());
        }

        dnsProviderFactory.addDomainRecord(domain, dnsRecord, TYPE, challenge.getDigest(), 600L);
        log.info("AddDomainRecord type:{}, rr: {} , value:{} SUCCESS", TYPE, dnsRecord, challenge.getDigest());
        challenge.trigger();

        challenge.waitForCompletion(TIMEOUT);
        return challenge;
    }

    /**
     * 写入证书和密钥
     */
    private void writeCertificates(Certificate certificate, KeyPair domainKeyPair, String certPath, String domain) throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        String fileName = domain + CommonConstant.DOT + currentTimeMillis;

        // 写入证书
        File certFile = new File(certPath, fileName + CommonConstant.CERT_SUFFIX);
        try (FileWriter fw = new FileWriter(certFile)) {
            certificate.writeCertificate(fw);
        }
        log.info("Wrote certificate to {}", certFile);

        // 写入PEM文件
        File pemFile = new File(certPath, fileName + CommonConstant.PEM_SUFFIX);
        try (FileWriter pemFw = new FileWriter(pemFile)) {
            certificate.writeCertificate(pemFw);
        }
        log.info("Wrote certificate to {}", pemFile);

        // 写入密钥
        File keyFile = new File(certPath, domain + CommonConstant.DOT + currentTimeMillis + CommonConstant.KEY_SUFFIX);
        try (FileWriter fw = new FileWriter(keyFile)) {
            KeyPairUtils.writeKeyPair(domainKeyPair, fw);
        }
        context.getOutput().put("crtFilePath", certFile.getAbsolutePath());
        context.getOutput().put("keyFilePath", keyFile.getAbsolutePath());
        context.getOutput().put("pemFilePath", pemFile.getAbsolutePath());
        Thread.sleep(1000 * 3);
        log.info("Wrote key to {}", keyFile);
    }


    /**
     * 创建DNS记录名称
     * 该方法用于根据域名和子域名生成相应的DNS记录名称它遵循特定的逻辑来构造DNS记录名，
     * 以适应ACME协议中DNS-01挑战的需要
     *
     * @param domain    域名，例如"example.com"
     * @param subDomain 子域名，例如"sub.example.com"
     * @return 生成的DNS记录名称
     */
    private String createDnsRecordName(String domain, String subDomain) {
        // 当域名和子域名相同时，直接返回DNS-01挑战记录的前缀
        if (domain.equals(subDomain)) {
            return Dns01Challenge.RECORD_NAME_PREFIX;
        }

        // 从子域名中分离出域名部分，并进一步分割得到首个子域名前缀
        // 这里解释了为什么要这样做：为了构造符合ACME协议要求的DNS记录名称
        String subStr = subDomain.split(domain)[0].split("\\.")[0];

        // 返回构造的DNS记录名称，格式为：_acme-challenge.子域名前缀
        // 这样做的目的是确保生成的记录名称符合ACME协议中对DNS-01挑战的要求
        return Dns01Challenge.RECORD_NAME_PREFIX + CommonConstant.DOT + subStr;
    }



    /**
     * 生成RSA密钥对
     */
    private KeyPair createKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }
}
