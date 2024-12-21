package cn.lycodeing.cert.task;

import cn.lycodeing.cert.common.context.Context;
import cn.lycodeing.cert.common.enums.TaskTypeEnum;
import cn.lycodeing.cert.task.constant.SystemConstant;
import cn.lycodeing.cert.task.task.Task;
import cn.lycodeing.cert.task.task.TaskFactoryUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

public class CertTaskMain {
    public static void main(String[] args) {
        Context context = buildContext();
        if (StringUtils.isEmpty(context.getTaskId())) {
            throw new RuntimeException("taskId is empty");
        }
        if (StringUtils.isEmpty(context.getData())) {
            throw new RuntimeException("data is empty");
        }
        if (StringUtils.isEmpty(context.getTaskType().name())) {
            throw new RuntimeException("taskType is empty");
        }
        Task task = TaskFactoryUtils.getTask(context.getTaskType());
        int execute = task.execute(context);
        System.exit(execute);
    }


    public static Context buildContext() {
        Context context = new Context();
        context.setTaskId(System.getenv(SystemConstant.TASK_ID_KEY));
        context.setData(System.getenv(SystemConstant.TASK_TYPE_KEY));
        context.setTaskType(TaskTypeEnum.valueOf(System.getenv(SystemConstant.TASK_TYPE_KEY)));
        context.setOutput(new HashMap<>());
        return context;
    }
}
