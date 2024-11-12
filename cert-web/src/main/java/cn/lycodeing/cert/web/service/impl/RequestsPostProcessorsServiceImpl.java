package cn.lycodeing.cert.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.lycodeing.cert.web.domain.RequestsPostProcessors;
import cn.lycodeing.cert.web.service.RequestsPostProcessorsService;
import cn.lycodeing.cert.web.mapper.RequestsPostProcessorsMapper;
import org.springframework.stereotype.Service;

/**
* @author lycodeing
* @description 针对表【cert_requests_post_processors(证书请求与后置处理器关联表)】的数据库操作Service实现
* @createDate 2024-11-12 22:56:07
*/
@Service
public class RequestsPostProcessorsServiceImpl extends ServiceImpl<RequestsPostProcessorsMapper, RequestsPostProcessors>
    implements RequestsPostProcessorsService{

}




