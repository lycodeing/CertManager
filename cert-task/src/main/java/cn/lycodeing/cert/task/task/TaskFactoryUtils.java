package cn.lycodeing.cert.task.task;

import cn.lycodeing.cert.task.enums.TaskTypeEnum;
import cn.lycodeing.cert.task.task.cdn.CdnTask;
import cn.lycodeing.cert.task.task.sftp.SFtpTask;
import cn.lycodeing.cert.task.task.ssh.SSHTask;
import cn.lycodeing.cert.task.task.ssl.CreateCertTask;

/**
 * @author lycodeing
 */
public class TaskFactoryUtils {
    public static Task getTask(TaskTypeEnum taskTypeEnum) {
        return switch (taskTypeEnum) {
            case SSL -> new CreateCertTask();
            case CDN -> new CdnTask();
            case SSH -> new SSHTask();
            case SFTP -> new SFtpTask();
        };
    }
}
