package cn.lycodeing.cert.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DNS提供商表
 *
 * @author lycodeing
 * @TableName cert_dns_providers
 */
@TableName(value = "cert_dns_providers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DnsProvider implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private Integer userId;

    /**
     * DNS提供商名称
     */
    private String providerName;

    /**
     * DNS提供商类型
     */
    private String providerType;

    /**
     * 提供商描述
     */
    private String description;

    /**
     * 访问密钥 (AK)
     */
    private String accessKey;

    /**
     * 安全密钥 (SK)
     */
    private String secretKey;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}