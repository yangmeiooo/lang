package cn.felord.spring.security.service;

import cn.felord.spring.security.entity.SysUser;

public interface SysUserService {
    /**
     * Query by username sys user.
     *
     * @param username the username
     * @return the sys user
     */
    SysUser queryByUsername(String username);

    /**
     * Add user integer.
     *
     * @param sysUser the sys user
     * @return the integer
     */
    Integer addUser(SysUser sysUser);

    /**
     * Update user integer.
     *
     * @param sysUser the sys user
     * @return the integer
     */
    Integer updateUser(SysUser sysUser);


    /**
     * Remove user integer.
     *
     * @param sysUser the sys user
     * @return the integer
     */
    Integer removeUser(SysUser sysUser);
}
