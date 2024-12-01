package cn.lycodeing.cert.web.controller;

import cn.lycodeing.cert.web.service.PostProcessorsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post/processors")
public class PostProcessorsController {

    private final PostProcessorsService postProcessorsService;

    public PostProcessorsController(PostProcessorsService postProcessorsService) {
        this.postProcessorsService = postProcessorsService;
    }

    @PostMapping
    public void add() {

    }
}
