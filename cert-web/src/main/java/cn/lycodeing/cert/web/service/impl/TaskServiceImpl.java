package cn.lycodeing.cert.web.service.impl;

import cn.lycodeing.cert.web.domain.Instance;
import cn.lycodeing.cert.web.domain.Task;
import cn.lycodeing.cert.web.dto.request.TaskDTO;
import cn.lycodeing.cert.web.enums.InstanceStatusEnum;
import cn.lycodeing.cert.web.mapper.TaskMapper;
import cn.lycodeing.cert.web.service.InstanceService;
import cn.lycodeing.cert.web.service.TaskService;
import cn.lycodeing.cert.web.utils.GsonUtil;
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
        task.setCertProvider(taskDTO.getCertProvider());
        task.setApiKey(taskDTO.getApiKey());
        task.setDomainsList(GsonUtil.toJson(taskDTO.getDomainsList()));
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
     * 按照时间生成路径名称：日期
     */
    private static String getPath() {
        LocalDateTime now = LocalDateTime.now();
        return now.getYear() + File.separator + now.getMonthValue() + File.separator +
                now.getDayOfMonth() + File.separator + now.getHour() + now.getMinute() + now.getSecond();
    }
}




