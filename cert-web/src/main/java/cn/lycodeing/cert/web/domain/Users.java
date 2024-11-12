package cn.lycodeing.cert.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

/**
 * 用户表
 * @TableName cert_users
 */
@TableName(value ="cert_users")
@Data
public class Users implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 密码哈希
     */
    private String password;

    /**
     * 用户全名
     */
    private String fullName;

    /**
     * 用户角色
     */
    private Object role;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}