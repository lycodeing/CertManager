package cn.lycodeing.cert.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.lycodeing.cert.web.domain.Requests;
import cn.lycodeing.cert.web.service.RequestsService;
import cn.lycodeing.cert.web.mapper.RequestsMapper;
import org.springframework.stereotype.Service;

/**
* @author lycodeing
* @description 针对表【cert_requests(用于保存证书申请信息的表)】的数据库操作Service实现
* @createDate 2024-11-12 22:56:07
*/
@Service
public class RequestsServiceImpl extends ServiceImpl<RequestsMapper, Requests>
    implements RequestsService{

}




