package cn.lycodeing.cert.web.dto.request;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * @author lycodeing
 */
@Builder
@Data
public class PostProcessorsDTO {

    /**
     * 后置处理器名称
     */
    @NotEmpty(message = "后置处理器名称不能为空")
    private String processorName;

    /**
     * 类型
     */
    @NotEmpty(message = "处理器类型不能为空")
    private String processorType;

    /**
     * 处理器描述
     */
    private String description;

    /**
     * 参数json字符串
     */
    @NotEmpty(message = "参数不能为空")
    private String parametersJson;
}
