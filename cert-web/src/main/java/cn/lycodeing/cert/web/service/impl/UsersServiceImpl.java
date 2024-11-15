package cn.lycodeing.cert.web.service.impl;

import cn.lycodeing.cert.web.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.lycodeing.cert.web.domain.Users;
import cn.lycodeing.cert.web.service.UsersService;
import cn.lycodeing.cert.web.mapper.UsersMapper;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.stereotype.Service;

/**
 * @author lycodeing
 * @description 针对表【cert_users(用户表)】的数据库操作Service实现
 * @createDate 2024-11-12 22:56:07
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
        implements UsersService {

    private final JwtUtil jwtUtil;

    public UsersServiceImpl(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String login(String username, String password) {
        LambdaQueryWrapper<Users> wrapper = Wrappers.lambdaQuery(Users.class);
        wrapper.eq(Users::getUsername, username);
        Users users = this.baseMapper.selectOne(wrapper);
        String md5Pwd = Md5Crypt.md5Crypt(password.getBytes());
        if (md5Pwd.equals(users.getPassword())) {
            return jwtUtil.createJwt(users.getUserId());
        }
        throw new RuntimeException("用户名或密码错误");
    }
}




