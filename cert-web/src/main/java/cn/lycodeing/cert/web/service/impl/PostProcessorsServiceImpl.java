package cn.lycodeing.cert.web.service.impl;

import cn.lycodeing.cert.web.domain.PostProcessors;
import cn.lycodeing.cert.web.dto.request.PostProcessorsDTO;
import cn.lycodeing.cert.web.mapper.PostProcessorsMapper;
import cn.lycodeing.cert.web.service.PostProcessorsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author lycodeing
* @description 针对表【cert_post_processors(后置处理器表)】的数据库操作Service实现
* @createDate 2024-11-12 22:56:07
*/
@Service
public class PostProcessorsServiceImpl extends ServiceImpl<PostProcessorsMapper, PostProcessors>
    implements PostProcessorsService{

    @Override
    public void add(PostProcessorsDTO processorsDTO) {
        PostProcessors postProcessors = new PostProcessors();
        postProcessors.setProcessorName(processorsDTO.getProcessorName());
        postProcessors.setProcessorType(processorsDTO.getProcessorType());
        postProcessors.setDescription(processorsDTO.getDescription());
        postProcessors.setParametersJson(processorsDTO.getParametersJson());
        int insert = this.baseMapper.insert(postProcessors);
        if (insert != 1) {
            throw new RuntimeException("添加失败");
        }
    }
}




