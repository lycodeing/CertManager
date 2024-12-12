package cn.lycodeing.cert.web.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 后置处理器表
 * @TableName cert_post_processors
 */
@TableName(value ="cert_post_processors")
@Data
public class PostProcessors implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 后置处理器名称
     */
    private String processorName;

    /**
     * 类型
     */
    private String processorType;

    /**
     * 处理器描述
     */
    private String description;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 参数json字符串
     */
    private String parametersJson;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}