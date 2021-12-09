package cn.felord.spring.security.service.impl;

import cn.felord.spring.security.entity.SysUser;
import cn.felord.spring.security.mapper.SysUserMapper;
import cn.felord.spring.security.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author dax
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser queryByUsername(String username) {
        return sysUserMapper.queryByUsername(username);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer addUser(SysUser sysUser) {
        return sysUserMapper.addUser(sysUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateUser(SysUser sysUser) {
        return sysUserMapper.updateUser(sysUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer removeUser(SysUser sysUser) {
        return sysUserMapper.removeUser(sysUser);
    }
}
