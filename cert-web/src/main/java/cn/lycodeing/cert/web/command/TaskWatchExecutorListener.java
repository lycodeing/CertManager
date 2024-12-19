package cn.lycodeing.cert.web.command;


import cn.lycodeing.cert.web.utils.SpringUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * 监听初始化和销毁
 *
 * @author lycodeing
 */
@Slf4j
@Component
@DependsOn("springUtil")
public class TaskWatchExecutorListener implements ServletContextListener {

    private CertTaskDriver certTaskDriver;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            if(certTaskDriver == null){
                certTaskDriver = SpringUtil.getBean(CertTaskDriver.class);
            }
            certTaskDriver.start();
        }catch (Exception e){
            log.error("CertTaskDriver start error", e);
            throw new RuntimeException(e);
        }
        log.info("CertTaskDriver start success");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        certTaskDriver.stop();
    }
}
