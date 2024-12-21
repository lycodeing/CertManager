package cn.lycodeing.cert.web.service.impl;

import cn.lycodeing.cert.web.domain.User;
import cn.lycodeing.cert.web.mapper.UsersMapper;
import cn.lycodeing.cert.web.service.UsersService;
import cn.lycodeing.cert.web.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

/**
 * @author lycodeing
 * @description 针对表【cert_users(用户表)】的数据库操作Service实现
 * @createDate 2024-11-12 22:56:07
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, User>
        implements UsersService {

    private final JwtUtil jwtUtil;

    public UsersServiceImpl(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String login(String username, String password) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class);
        wrapper.eq(User::getUsername, username);
        User user = this.baseMapper.selectOne(wrapper);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        String md5Pwd = DigestUtils.md5Hex(password.getBytes());
        if (md5Pwd.equals(user.getPassword())) {
            return jwtUtil.createJwt(user.getUserId());
        }
        throw new RuntimeException("用户名或密码错误");
    }
}




