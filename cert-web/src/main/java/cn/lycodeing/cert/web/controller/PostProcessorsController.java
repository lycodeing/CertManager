package cn.lycodeing.cert.web.controller;

import cn.lycodeing.cert.web.common.R;
import cn.lycodeing.cert.web.domain.PostProcessors;
import cn.lycodeing.cert.web.dto.request.PostProcessorsDTO;
import cn.lycodeing.cert.web.dto.response.PostProcessorsOptionsDTO;
import cn.lycodeing.cert.web.security.SecurityContext;
import cn.lycodeing.cert.web.service.PostProcessorsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后置处理器接口
 * @author lycodeing
 */
@RestController
@RequestMapping("/api/post")
public class PostProcessorsController {

    private final PostProcessorsService postProcessorsService;

    public PostProcessorsController(PostProcessorsService postProcessorsService) {
        this.postProcessorsService = postProcessorsService;
    }

    @PostMapping
    public R<Void> add(@RequestBody @Validated PostProcessorsDTO postProcessorsDTO) {
        postProcessorsService.add(postProcessorsDTO);
        return R.ok();
    }


    /**
     * 查询当前登陆用户的后置处理器列表
     *
     * @return List<PostProcessorsOptionsDTO>
     */
    @GetMapping("/options")
    public R<List<PostProcessorsOptionsDTO>> options() {
        Integer userId = SecurityContext.getUserId();
        LambdaQueryWrapper<PostProcessors> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(PostProcessors::getUserId, userId);
        List<PostProcessorsOptionsDTO> dtoList = postProcessorsService.list(queryWrapper).stream()
                .map(post -> PostProcessorsOptionsDTO.builder()
                        .postId(post.getId())
                        .postName(post.getProcessorName())
                        .postType(post.getProcessorType())
                        .build()
                ).toList();
        return R.ok(dtoList);
    }



    @PostMapping("/relevance/{requestId}/{postProcessorId}")
    public R<Void> relevance(@PathVariable Long requestId, @PathVariable Long postProcessorId) {
        return R.ok();
    }
}
