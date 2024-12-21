package cn.lycodeing.cert.web.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * 用户信息
 *
 * @author lycodeing
 */
@Builder
@Data
public class UserDTO {
    private Integer id;

    private String username;

    private String email;

    private String avatar;
}
