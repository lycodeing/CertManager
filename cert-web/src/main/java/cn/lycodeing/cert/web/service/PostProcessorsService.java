package cn.lycodeing.cert.web.service;

import cn.lycodeing.cert.web.domain.PostProcessors;
import cn.lycodeing.cert.web.dto.request.PostProcessorsDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author lycodeing
* @description 针对表【cert_post_processors(后置处理器表)】的数据库操作Service
* @createDate 2024-11-12 22:56:07
*/
public interface PostProcessorsService extends IService<PostProcessors> {

    void add(PostProcessorsDTO processorsDTO);
}
