package cn.felord.spring.security.entity;

import lombok.Data;

@Data
public class SysUser {
    private Integer userId;
    private String username;
    private String encodePassword;
    private Integer age;
}
