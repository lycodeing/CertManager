package cn.lycodeing.cert.web.controller;

import cn.lycodeing.cert.web.common.R;
import cn.lycodeing.cert.web.dto.request.RequestDTO;
import cn.lycodeing.cert.web.exec.TaskExecutor;
import cn.lycodeing.cert.web.service.RequestsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
public class RequestController {


    private final RequestsService requestsService;

    public RequestController(RequestsService requestsService) {
        this.requestsService = requestsService;
    }


    @PostMapping("/create")
    public R<Void> create(@RequestBody RequestDTO request) {
        requestsService.createOrUpdate(request);
        return R.ok();
    }


    @PostMapping("/execute")
    public void execute() {
        // 执行相关操作
        TaskExecutor executor = new TaskExecutor(null);

    }


}
