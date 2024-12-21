package cn.lycodeing.cert.web.service.impl;

import cn.lycodeing.cert.common.context.CertTaskData;
import cn.lycodeing.cert.common.enums.CertProviderEnum;
import cn.lycodeing.cert.common.enums.DnsEnum;
import cn.lycodeing.cert.common.enums.TaskTypeEnum;
import cn.lycodeing.cert.web.domain.*;
import cn.lycodeing.cert.web.enums.InstanceStatusEnum;
import cn.lycodeing.cert.web.mapper.InstanceMapper;
import cn.lycodeing.cert.web.service.DnsProvidersService;
import cn.lycodeing.cert.web.service.InstanceService;
import cn.lycodeing.cert.web.service.PostProcessorsService;
import cn.lycodeing.cert.web.service.TaskPostProcessorsService;
import cn.lycodeing.cert.web.utils.GsonUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lycodeing
 * @description 针对表【cert_requests_log(证书请求操作日志)】的数据库操作Service实现
 * @createDate 2024-11-12 22:56:07
 */
@Service
public class InstanceServiceImpl extends ServiceImpl<InstanceMapper, Instance>
        implements InstanceService {

    @Value("${ssl.path}")
    private String sslPath;

    private final PostProcessorsService postProcessorsService;

    private final TaskPostProcessorsService taskPostProcessorsService;

    private final DnsProvidersService dnsProvidersService;


    public InstanceServiceImpl(PostProcessorsService postProcessorsService,
                                TaskPostProcessorsService taskPostProcessorsService,
                                DnsProvidersService dnsProvidersService) {
        this.postProcessorsService = postProcessorsService;
        this.taskPostProcessorsService = taskPostProcessorsService;
        this.dnsProvidersService = dnsProvidersService;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createInstance(Task task) {

        Integer instanceId = saveComplexInstance(task);

        saveCertInstance(task, instanceId);

        savePostInstance(task, instanceId);
    }


    /**
     * 创建复合L1实例
     *
     * @param task 任务
     * @return L1实例id
     */
    public Integer saveComplexInstance(Task task) {
        Instance instance = new Instance();
        instance.setName(task.getDomain());
        instance.setParentInstanceId(null);
        instance.setStatus(InstanceStatusEnum.WAITING.getCode());
        instance.setTaskType(TaskTypeEnum.COMPOUND.name());
        instance.setCreateTime(LocalDateTime.now());
        return this.baseMapper.insert(instance) > 1 ? instance.getId() : null;
    }


    /**
     * 创建L1类型的实例
     *
     * @param task             任务
     * @param parentInstanceId 父实例id
     */
    public void saveCertInstance(Task task, Integer parentInstanceId) {
        Instance instance = new Instance();
        instance.setName(task.getDomain());
        instance.setParentInstanceId(parentInstanceId);
        instance.setStatus(InstanceStatusEnum.WAITING.getCode());
        instance.setTaskType(TaskTypeEnum.SSL.name());
        instance.setTaskId(task.getId());
        instance.setCreateTime(LocalDateTime.now());
        instance.setActionTime(LocalDateTime.now());
        instance.setMessage("申请证书任务实例");
        instance.setAddress(sslPath + getPath());
        instance.setJsonData(buildCertTaskData(task));
        this.baseMapper.insert(instance);
    }

    /**
     * 构建证书任务数据
     *
     * @param task 任务
     * @return jsonData
     */
    public String buildCertTaskData(Task task) {
        DnsProvider dns = dnsProvidersService.getById(task.getDnsId());
        CertTaskData certTaskData = new CertTaskData();
        certTaskData.setEmail(task.getEmail());
        certTaskData.setCertPath(sslPath + getPath());
        certTaskData.setDomain(task.getDomain());
        List<String> domains = GsonUtil.fromJson(task.getDomainsList(), new TypeToken<List<String>>() {
        }.getType());
        certTaskData.setDomains(domains);
        certTaskData.setDns(new CertTaskData.DnsData(dns.getAccessKey(), dns.getSecretKey(), DnsEnum.valueOf(dns.getProviderType())));
        CertTaskData.CertProviderData certProvider = new CertTaskData.CertProviderData(CertProviderEnum.valueOf(task.getCertProvider()), task.getApiKey());
        certTaskData.setCertProvider(certProvider);
        return GsonUtil.toJson(certTaskData);
    }


    /**
     * 创建证书任务的后置任务实例
     *
     * @param task             任务
     * @param parentInstanceId 父实例id
     */
    public void savePostInstance(Task task, Integer parentInstanceId) {
        LambdaQueryWrapper<TaskPostProcessor> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(TaskPostProcessor::getTaskId, task.getId());
        queryWrapper.orderByAsc(TaskPostProcessor::getSort);
        List<TaskPostProcessor> taskPostProcessorList = taskPostProcessorsService.list(queryWrapper);
        if (CollectionUtils.isEmpty(taskPostProcessorList)) {
            return;
        }
        // 获取PostProcessors
        List<Integer> processorIds = taskPostProcessorList.stream()
                .map(TaskPostProcessor::getProcessorId)
                .toList();
        List<PostProcessor> processorsList = postProcessorsService.listByIds(processorIds);
        if (CollectionUtils.isEmpty(processorsList)) {
            return;
        }
        Map<Integer, PostProcessor> id2Entity = processorsList.stream().collect(Collectors.toMap(PostProcessor::getId, p -> p));
        for (TaskPostProcessor taskPostProcessor : taskPostProcessorList) {
            PostProcessor postProcessor = id2Entity.get(taskPostProcessor.getProcessorId());
            // 创建L2实例
            Instance instance = new Instance();
            instance.setName(task.getDomain());
            instance.setParentInstanceId(parentInstanceId);
            instance.setStatus(InstanceStatusEnum.WAITING.getCode());
            instance.setTaskType(postProcessor.getProcessorType());
            instance.setActionTime(LocalDateTime.now());
            instance.setCreateTime(LocalDateTime.now());
            instance.setAddress(sslPath + getPath());
            instance.setJsonData(postProcessor.getJsonData());
        }
    }


    private static String getPath() {
        LocalDateTime now = LocalDateTime.now();
        return now.getYear() + File.separator + now.getMonthValue() + File.separator +
                now.getDayOfMonth() + File.separator + now.getHour() + now.getMinute() + now.getSecond();
    }
}




