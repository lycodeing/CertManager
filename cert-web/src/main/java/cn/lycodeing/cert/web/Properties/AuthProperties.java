package cn.lycodeing.cert.web.Properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private String authHeader;

    private String tokenPrefix;

    private List<String> ignoreUrls;
}
