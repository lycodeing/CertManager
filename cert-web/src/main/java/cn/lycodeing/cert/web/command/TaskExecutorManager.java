package cn.lycodeing.cert.web.command;

import cn.lycodeing.cert.web.exec.TaskExecutor;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务执行管理类
 */
public class TaskExecutorManager {
    private static final Map<String, TaskExecutor> taskExecutors = new HashMap<>(16);


    public static TaskExecutor getTaskExecutor(String taskId) {
        return taskExecutors.get(taskId);
    }

    public static void addTaskExecutor(String taskId, TaskExecutor taskExecutor) {
        taskExecutors.put(taskId, taskExecutor);
    }

    public static void removeTaskExecutor(String taskId) {
        taskExecutors.remove(taskId);
    }


    public static void killTaskExecutor(String taskId) {
        TaskExecutor taskExecutor = taskExecutors.get(taskId);
        if (taskExecutor != null) {
            taskExecutor.kill();
        }
    }

    public static void killAllTaskExecutor() {
        taskExecutors.values().forEach(TaskExecutor::kill);
    }

}
