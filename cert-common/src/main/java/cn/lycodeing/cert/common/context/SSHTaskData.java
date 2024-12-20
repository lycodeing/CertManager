package cn.lycodeing.cert.common.context;

import lombok.Data;

@Data
public class SSHTaskData {

    /**
     * 服务器地址
     */
    private String host;
    /**
     * 服务器端口
     */
    private Integer port;
    /**
     * 用户名
     */
    private String user;
    /**
     * 密码
     */
    private String password;

    /**
     * 命令
     */
    private String command;
}
