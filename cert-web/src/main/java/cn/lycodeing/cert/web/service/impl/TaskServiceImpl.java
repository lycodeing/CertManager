package cn.lycodeing.cert.web.service.impl;

import cn.lycodeing.cert.web.domain.Instance;
import cn.lycodeing.cert.web.domain.PostProcessors;
import cn.lycodeing.cert.web.domain.Task;
import cn.lycodeing.cert.web.dto.request.TaskDTO;
import cn.lycodeing.cert.web.enums.InstanceStatusEnum;
import cn.lycodeing.cert.web.mapper.TaskMapper;
import cn.lycodeing.cert.web.service.InstanceService;
import cn.lycodeing.cert.web.service.TaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;

/**
 * @author lycodeing
 * @description 针对表【cert_task(用于保存证书申请信息的表)】的数据库操作Service实现
 * @createDate 2024-11-12 22:56:07
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task>
        implements TaskService {

    @Value("${ssl.path}")
    private String sslPath;

    private final InstanceService instanceService;

    public TaskServiceImpl(InstanceService instanceService) {
        this.instanceService = instanceService;
    }


    @Override
    public void createOrUpdate(TaskDTO taskDTO) {
        Task task = builderRequest(taskDTO);
        if (task.getId() == null) {
            this.baseMapper.insert(task);
        } else {
            this.baseMapper.updateById(task);
        }
    }

    private Task builderRequest(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setDomain(taskDTO.getDomain());
        task.setEmail(taskDTO.getEmail());
        task.setDnsType(taskDTO.getDnsType());
        task.setAccessKey(taskDTO.getAccessKey());
        task.setAccessSecret(taskDTO.getAccessSecret());
        task.setCertProvider(taskDTO.getCertProvider());
        task.setApiKey(taskDTO.getApiKey());
        task.setDomainsList(taskDTO.getDomainsList());
        return task;
    }

    @Override
    public void execute(String requestId) {
        // 查询
        Task task = this.baseMapper.selectById(requestId);
        // 生成执行记录
        Instance entity = new Instance();
        entity.setTaskId(task.getId());
        entity.setStatus(InstanceStatusEnum.WAITING.getCode());
        entity.setAddress(sslPath + getPath());
        entity.setActionTime(LocalDateTime.now());
        instanceService.save(entity);
    }


    /**
     * 创建复合L1实例
     *
     * @param task 任务
     * @return
     */
    public Integer saveComplexInstance(Task task) {
        return 0;
    }


    /**
     * 创建L1类型的实例
     *
     * @param task             任务
     * @param parentInstanceId 父实例id
     */
    public void saveCertInstance(Task task, Integer parentInstanceId) {

    }


    /**
     * 创建证书任务的后置任务实例
     *
     * @param postProcessors   后置处理器任务
     * @param parentInstanceId 父实例id
     */
    public void savePostInstance(PostProcessors postProcessors, Integer parentInstanceId) {

    }


    /**
     * 按照时间生成路径名称：日期
     */
    private static String getPath() {
        LocalDateTime now = LocalDateTime.now();
        return now.getYear() + File.separator + now.getMonthValue() + File.separator +
                now.getDayOfMonth() + File.separator + now.getHour() + now.getMinute() + now.getSecond();
    }
}




