package cn.lycodeing.cert.web.controller;

import cn.lycodeing.cert.web.common.R;
import cn.lycodeing.cert.web.dto.request.LoginDTO;
import cn.lycodeing.cert.web.dto.response.LoginResponse;
import cn.lycodeing.cert.web.service.UsersService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public R<LoginResponse> login(@RequestBody @Validated LoginDTO loginRequest) {
        String token = usersService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return R.ok(LoginResponse.builder().token(token).build());
    }

}
