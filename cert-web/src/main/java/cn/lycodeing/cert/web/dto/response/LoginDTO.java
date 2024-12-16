package cn.lycodeing.cert.web.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 *  登录返回
 * @author lycodeing
 */
@Data
@Builder
public class LoginDTO {
    private String token;
}
