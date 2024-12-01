package cn.lycodeing.cert.web.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;


@Data
public class DnsProvidersRequest {
    private Integer id;

    /**
     * DNS提供商名称
     */
    @NotEmpty(message = "提供商名称不能为空")
    private String providerName;

    /**
     * DNS提供商类型
     */
    @NotEmpty(message = "提供商类型不能为空")
    private String providerType;

    /**
     * 提供商描述
     */
    @NotEmpty(message = "提供商描述不能为空")
    private String description;

    /**
     * 访问密钥 (AK)
     */
    @NotEmpty(message = "AK不能为空")
    private String accessKey;

    /**
     * 安全密钥 (SK)
     */
    @NotEmpty(message = "SK不能为空")
    private String secretKey;
}
