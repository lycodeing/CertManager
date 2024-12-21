package cn.lycodeing.cert.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 认证配置
 * @author lycodeing
 */
@Data
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private String authHeader;

    private String tokenPrefix;

    private List<String> ignoreUrls;
}
