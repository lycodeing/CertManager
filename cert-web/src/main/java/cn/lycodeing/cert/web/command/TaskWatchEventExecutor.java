package cn.lycodeing.cert.web.command;

import cn.lycodeing.cert.web.domain.Instance;
import cn.lycodeing.cert.web.enums.InstanceStatusEnum;
import cn.lycodeing.cert.web.exec.TaskExecutor;
import cn.lycodeing.cert.web.service.InstanceService;
import cn.lycodeing.cert.web.utils.GsonUtil;
import cn.lycodeing.cert.web.utils.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static cn.lycodeing.cert.web.utils.ThreadUtil.sleep;

/**
 * 证书任务执行器
 *
 * @author lycodeing
 */
@Component
@Slf4j
@DependsOn("springUtil")
public class TaskWatchEventExecutor {

    private final AtomicBoolean running = new AtomicBoolean(false);

    /**
     * map 类型
     */
    private static final Type MAP_TYPE = new TypeToken<Map<String, String>>() {
    }.getType();

    private final InstanceService service = SpringUtil.getBean(InstanceService.class);

    /**
     * 证书任务线程
     */
    CertTaskThread certTaskThread;

    /**
     * 创建线程池
     */
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void start() {
        certTaskThread = new CertTaskThread();
        executorService.submit(certTaskThread);
    }


    public void stop() {
        running.set(false);
        executorService.shutdown();
    }

    class CertTaskThread implements Runnable {

        @Override
        public void run() {
            while (!running.get()) {
                try {
                    // 1.读取Instance表数据
                    LambdaQueryWrapper<Instance> queryWrapper = Wrappers.lambdaQuery();
                    queryWrapper.eq(Instance::getStatus, InstanceStatusEnum.WAITING.getCode());
                    List<Instance> list = service.list(queryWrapper);
                    for (Instance instance : list) {
                        // 2.更新Instance表状态为running
                        instance.setStatus(InstanceStatusEnum.RUNNER.getCode());
                        // 3.调用TaskExecutor
                        String paramsJson = instance.getJsonData();
                        TaskExecutor taskExecutor = new TaskExecutor(GsonUtil.fromJson(paramsJson, MAP_TYPE));
                        // 4.写入TaskExecutorManager中
                        TaskExecutorManager.addTaskExecutor(instance.getId().toString(), taskExecutor);
                    }
                    service.updateBatchById(list);
                } catch (Exception e) {
                    sleep(100);
                    log.info("the task executor is abnormal procedure", e);
                } finally {
                    sleep(1000);
                }
            }
        }
    }
}