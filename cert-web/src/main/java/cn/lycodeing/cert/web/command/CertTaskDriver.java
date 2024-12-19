package cn.lycodeing.cert.web.command;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 任务执行驱动器
 *
 * @author lycodeing
 */
@Component
public class CertTaskDriver {
    private AtomicBoolean isStart = new AtomicBoolean(false);

    TaskWatchEventExecutor taskWatchEventExecutor;


    public void start() {
        taskWatchEventExecutor = new TaskWatchEventExecutor();
        taskWatchEventExecutor.start();
    }


    public void stop() {
        taskWatchEventExecutor.stop();
    }
}
