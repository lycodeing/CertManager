package cn.lycodeing.cert.web.service.impl;

import cn.lycodeing.cert.web.dto.request.RequestRequest;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.lycodeing.cert.web.domain.Requests;
import cn.lycodeing.cert.web.service.RequestsService;
import cn.lycodeing.cert.web.mapper.RequestsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author lycodeing
 * @description 针对表【cert_requests(用于保存证书申请信息的表)】的数据库操作Service实现
 * @createDate 2024-11-12 22:56:07
 */
@Service
public class RequestsServiceImpl extends ServiceImpl<RequestsMapper, Requests>
        implements RequestsService {

    @Override
    public void createOrUpdate(RequestRequest request) {
        Requests requests = builderRequest(request);
        if (requests.getId() == null) {
            this.baseMapper.insert(requests);
        } else {
            this.baseMapper.updateById(requests);
        }
    }

    private Requests builderRequest(RequestRequest request) {
        Requests requests = new Requests();
        requests.setId(request.getId());
        requests.setDomain(request.getDomain());
        requests.setEmail(request.getEmail());
        requests.setDnsType(request.getDnsType());
        requests.setAccessKey(request.getAccessKey());
        requests.setAccessSecret(request.getAccessSecret());
        requests.setCertProvider(request.getCertProvider());
        requests.setApiKey(request.getApiKey());
        requests.setDomainsList(request.getDomainsList());
        return requests;
    }
}




