package cn.lycodeing.cert.web.controller;

import cn.lycodeing.cert.web.common.R;
import cn.lycodeing.cert.web.domain.User;
import cn.lycodeing.cert.web.dto.response.LoginDTO;
import cn.lycodeing.cert.web.dto.response.UserDTO;
import cn.lycodeing.cert.web.security.SecurityContext;
import cn.lycodeing.cert.web.service.UsersService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关接口
 *
 * @author lycodeing
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }


    /**
     * 登陆
     */
    @PostMapping("/login")
    public R<LoginDTO> login(@RequestBody @Validated cn.lycodeing.cert.web.dto.request.LoginDTO loginRequest) {
        String token = usersService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return R.ok(LoginDTO.builder().token(token).build());
    }

    @GetMapping("/info")
    public R<UserDTO> info() {
        Integer userId = SecurityContext.getUserId();
        User user = usersService.getById(userId);
        return R.ok(UserDTO.builder().id(1).username(user.getUsername()).email("lycodeing@qq.com").avatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif").build());
    }
}
