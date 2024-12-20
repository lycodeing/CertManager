package cn.lycodeing.cert.web.service;

import cn.lycodeing.cert.web.domain.Task;
import cn.lycodeing.cert.web.dto.request.TaskDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author lycodeing
* @description 针对表【cert_task(用于保存证书申请信息的表)】的数据库操作Service
* @createDate 2024-11-12 22:56:07
*/
public interface TaskService extends IService<Task> {

    /**
     * 创建或更新证书申请信息
     * @param request 请求参数
     */
    void createOrUpdate(TaskDTO request);




    void execute(String taskId);

}
