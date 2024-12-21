package cn.lycodeing.cert.web.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 后置处理器表
 *
 * @author lycodeing
 * @TableName cert_post_processors
 */
@TableName(value = "cert_post_processors")
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
     * {@link cn.lycodeing.cert.common.enums.TaskTypeEnum}
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
     * {@link cn.lycodeing.cert.common.context.SFtpTaskData}
     * {@link cn.lycodeing.cert.common.context.SSHTaskData}
     * {@link cn.lycodeing.cert.common.context.CertTaskData}
     */
    private String jsonData;


    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}