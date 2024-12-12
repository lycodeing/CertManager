package cn.lycodeing.cert.web.controller;

import cn.lycodeing.cert.web.common.R;
import cn.lycodeing.cert.web.dto.request.PostProcessorsDTO;
import cn.lycodeing.cert.web.service.PostProcessorsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post/processors")
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


    @PostMapping("/relevance/{requestId}/{postProcessorId}")
    public R<Void> relevance(@PathVariable Long requestId, @PathVariable Long postProcessorId) {
        return R.ok();
    }
}
