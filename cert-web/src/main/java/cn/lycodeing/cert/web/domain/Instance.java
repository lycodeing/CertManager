package cn.lycodeing.cert.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 证书请求操作日志
 *
 * @author lycodeing
 * @TableName cert_requests_log
 */
@TableName(value = "cert_instance")
@Data
public class Instance implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 关联的证书任务ID
     */
    private Integer taskId;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务状态
     */
    private Integer status;

    /**
     * 操作相关的地址信息 (如IP地址，URL等)
     */
    private String address;

    /**
     * 操作时间
     */
    private LocalDateTime actionTime;

    /**
     * 操作的描述信息
     */
    private String message;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 请求参数
     */
    private String jsonData;

    /**
     * 输出数据
     */
    private Map<String, String> outputJson;

    /**
     * 父实例id
     */
    private Integer parentInstanceId;


    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}