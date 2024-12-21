package cn.lycodeing.cert.web.controller;

import cn.lycodeing.cert.web.common.R;
import cn.lycodeing.cert.web.dto.request.TaskDTO;
import cn.lycodeing.cert.web.service.TaskService;
import org.springframework.web.bind.annotation.*;

/**
 * 创建证书请求接口
 * @author lycodeing
 */
@RestController
@RequestMapping("/api/task")
public class TaskController {


    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @PostMapping("/create")
    public R<Void> create(@RequestBody TaskDTO request) {
        taskService.createOrUpdate(request);
        return R.ok();
    }


    @PostMapping("/execute")
    public void execute(@RequestParam String taskId) {
        taskService.execute(taskId);
    }


}
