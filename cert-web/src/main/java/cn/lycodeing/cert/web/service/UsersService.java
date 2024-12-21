package cn.lycodeing.cert.web.service;

import cn.lycodeing.cert.web.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author lycodeing
* @description 针对表【cert_users(用户表)】的数据库操作Service
* @createDate 2024-11-12 22:56:07
*/
public interface UsersService extends IService<User> {

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    String login(String username, String password);

}
