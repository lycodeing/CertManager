package cn.lycodeing.cert.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 证书请求与后置处理器关联表
 *
 * @author lycodeing
 * @TableName cert_task_post_processors
 */
@TableName(value = "cert_task_post_processors")
@Data
public class TaskPostProcessor implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 证书请求ID
     */
    private Integer taskId;

    /**
     * 后置处理器ID
     */
    private Integer processorId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 排序
     */
    private Integer sort;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}