package cn.lycodeing.cert.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 用于保存证书申请信息的表
 * @TableName cert_requests
 */
@TableName(value ="cert_requests")
@Data
public class Requests implements Serializable {
    /**
     * 证书申请记录的唯一ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 申请证书的域名
     */
    private String domain;

    /**
     * 证书文件的存储路径
     */
    private String certPath;

    /**
     * 联系人邮箱地址
     */
    private String email;

    /**
     * 使用的DNS提供商类型
     */
    private String dnsType;

    /**
     * 访问密钥
     */
    private String accessKey;

    /**
     * 访问密钥的密钥
     */
    private String accessSecret;

    /**
     * 证书提供商类型
     */
    private String certProvider;

    /**
     * API密钥
     */
    private String apiKey;

    /**
     * 要申请证书的域名列表
     */
    private Object domainsList;

    /**
     * 申请时间
     */
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}