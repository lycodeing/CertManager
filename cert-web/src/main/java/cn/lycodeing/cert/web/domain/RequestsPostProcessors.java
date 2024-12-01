package cn.lycodeing.cert.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

/**
 * 证书请求与后置处理器关联表
 * @TableName cert_requests_post_processors
 */
@TableName(value ="cert_requests_post_processors")
@Data
public class RequestsPostProcessors implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 证书请求ID
     */
    private Integer requestId;

    /**
     * 后置处理器ID
     */
    private Integer processorId;

    /**
     * 后置处理器参数（JSON格式）
     */
    private String parameters;

    /**
     * 处理状态
     */
    private Object status;

    /**
     * 处理输出信息
     */
    private String output;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}