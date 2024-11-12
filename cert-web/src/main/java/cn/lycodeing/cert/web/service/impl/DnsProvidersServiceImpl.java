package cn.lycodeing.cert.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.lycodeing.cert.web.domain.DnsProviders;
import cn.lycodeing.cert.web.service.DnsProvidersService;
import cn.lycodeing.cert.web.mapper.DnsProvidersMapper;
import org.springframework.stereotype.Service;

/**
* @author lycodeing
* @description 针对表【cert_dns_providers(DNS提供商表)】的数据库操作Service实现
* @createDate 2024-11-12 22:56:07
*/
@Service
public class DnsProvidersServiceImpl extends ServiceImpl<DnsProvidersMapper, DnsProviders>
    implements DnsProvidersService{

}




