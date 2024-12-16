package cn.lycodeing.cert.web.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步配置
 * @author lycodeing
 */
@Slf4j
@Configuration
public class ThreadPoolConfig {

    /** 核心线程数 */
    public static final Integer CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    /** 最大线程数 */
    public static final Integer MAX_POOL_SIZE = (int) (CORE_POOL_SIZE / (1 - 0.9));



    @Bean
    public ThreadPoolExecutor taskExecutor() {
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000),
                new BasicThreadFactory.Builder().namingPattern("task-thread-%d").build(),
                // 自定义拒绝策略
                ((r, executor) -> log.error("The async executor pool is full!"))
        );
    }
}
