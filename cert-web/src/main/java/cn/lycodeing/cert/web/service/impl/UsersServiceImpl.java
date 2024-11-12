package cn.lycodeing.cert.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.lycodeing.cert.web.domain.Users;
import cn.lycodeing.cert.web.service.UsersService;
import cn.lycodeing.cert.web.mapper.UsersMapper;
import org.springframework.stereotype.Service;

/**
* @author lycodeing
* @description 针对表【cert_users(用户表)】的数据库操作Service实现
* @createDate 2024-11-12 22:56:07
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

}




