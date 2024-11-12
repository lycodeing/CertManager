package cn.lycodeing.cert.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 证书请求操作日志
 * @TableName cert_requests_log
 */
@TableName(value ="cert_requests_log")
@Data
public class RequestsLog implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 关联的证书请求ID
     */
    private Integer requestId;

    /**
     * 操作类型
     */
    private Object action;

    /**
     * 操作相关的地址信息 (如IP地址，URL等)
     */
    private String address;

    /**
     * 操作时间
     */
    private Date actionTime;

    /**
     * 操作的描述信息
     */
    private String message;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}