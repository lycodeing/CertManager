package cn.lycodeing.cert.web.exec;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

@Slf4j
public class TaskExecutor {

    private long pid;

    TaskExecutorThread executorThread;

    public TaskExecutor(Map<String, String> params) {
        executorThread = new TaskExecutorThread(params);
    }

    /**
     * 启动
     */
    public void start() {
        executorThread.start();
        this.pid = executorThread.pid;
    }

    /**
     * 需要执行的python文件的地址
     */
    private static final String PYTHON_COMMAND = "python /task/run.py";

    public static class TaskExecutorThread extends Thread {

        private final Map<String, String> params;

        private Long pid;


        public TaskExecutorThread(Map<String, String> params) {
            this.params = params;
        }

        @Override
        public void run() {
            long pid = 0;
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(PYTHON_COMMAND);
                Process process = processBuilder.start();
                pid = process.pid();
                // 设置环境变量
                processBuilder.environment().putAll(params);
                // 读取命令执行的结果
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    log.info(line);
                }
                // 等待命令执行完成并获取执行结果
                int exitCode = process.waitFor();
                log.info("执行python脚本完成，退出码为：{}", exitCode);
                // 获取pid

                reader.close();
            } catch (Exception e) {
                log.info("执行python脚本异常", e);
            }
            this.pid = pid;
        }


    }


    public void kill() {
        try {
            log.info("kill task process pid:{}", pid);
            Runtime.getRuntime().exec("kill -9 " + pid);
        } catch (Exception e) {
            log.info("kill python进程异常", e);
        }
    }
}
