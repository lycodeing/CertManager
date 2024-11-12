package cn.lycodeing.cert.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.lycodeing.cert.web.domain.PostProcessors;
import cn.lycodeing.cert.web.service.PostProcessorsService;
import cn.lycodeing.cert.web.mapper.PostProcessorsMapper;
import org.springframework.stereotype.Service;

/**
* @author lycodeing
* @description 针对表【cert_post_processors(后置处理器表)】的数据库操作Service实现
* @createDate 2024-11-12 22:56:07
*/
@Service
public class PostProcessorsServiceImpl extends ServiceImpl<PostProcessorsMapper, PostProcessors>
    implements PostProcessorsService{

}



