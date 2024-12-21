package cn.lycodeing.cert.web.service;

import cn.lycodeing.cert.web.domain.Instance;
import cn.lycodeing.cert.web.domain.Task;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author lycodeing
* @description 针对表【cert_requests_log(证书请求操作日志)】的数据库操作Service
* @createDate 2024-11-12 22:56:07
*/
public interface InstanceService extends IService<Instance> {



    void createInstance(Task task);
}
