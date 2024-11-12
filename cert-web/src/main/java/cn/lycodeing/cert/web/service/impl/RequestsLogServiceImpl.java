package cn.lycodeing.cert.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.lycodeing.cert.web.domain.RequestsLog;
import cn.lycodeing.cert.web.service.RequestsLogService;
import cn.lycodeing.cert.web.mapper.RequestsLogMapper;
import org.springframework.stereotype.Service;

/**
* @author lycodeing
* @description 针对表【cert_requests_log(证书请求操作日志)】的数据库操作Service实现
* @createDate 2024-11-12 22:56:07
*/
@Service
public class RequestsLogServiceImpl extends ServiceImpl<RequestsLogMapper, RequestsLog>
    implements RequestsLogService{

}




