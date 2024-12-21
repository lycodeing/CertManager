package cn.lycodeing.cert.web.command;

import cn.lycodeing.cert.web.exec.TaskExecutor;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务执行管理类
 * @author lycodeing
 */
public class TaskExecutorManager {
    private static final Map<String, TaskExecutor> TASK_EXECUTORS = new HashMap<>(16);


    public static TaskExecutor getTaskExecutor(String taskId) {
        return TASK_EXECUTORS.get(taskId);
    }

    public static void addTaskExecutor(String taskId, TaskExecutor taskExecutor) {
        TASK_EXECUTORS.put(taskId, taskExecutor);
    }

    public static void removeTaskExecutor(String taskId) {
        TASK_EXECUTORS.remove(taskId);
    }


    public static void killTaskExecutor(String taskId) {
        TaskExecutor taskExecutor = TASK_EXECUTORS.get(taskId);
        if (taskExecutor != null) {
            taskExecutor.kill();
        }
    }

    public static void killAllTaskExecutor() {
        TASK_EXECUTORS.values().forEach(TaskExecutor::kill);
    }

}
