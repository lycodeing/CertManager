package cn.lycodeing.cert.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestRequest {
    private Integer id;

    /**
     * 申请证书的域名
     */
    private String domain;

    /**
     * 联系人邮箱地址
     */
    private String email;

    /**
     * 使用的DNS提供商类型
     */
    private String dnsType;

    /**
     * 访问密钥
     */
    private String accessKey;

    /**
     * 访问密钥的密钥
     */
    private String accessSecret;

    /**
     * 证书提供商类型
     */
    private String certProvider;

    /**
     * API密钥
     */
    private String apiKey;

    /**
     * 要申请证书的域名列表
     */
    private Object domainsList;

}
