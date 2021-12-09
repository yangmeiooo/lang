package cn.felord.spring.security.controller;

import cn.felord.spring.security.entity.Rest;
import cn.felord.spring.security.entity.RestBody;
import cn.felord.spring.security.entity.SysUser;
import cn.felord.spring.security.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * LoginController
 *
 * @author Felordcn
 * @since 10 :05 2019/10/17
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private SysUserService sysUserService;

    /**
     * 登录失败返回 401 以及提示信息.
     *
     * @return the rest
     */
    @PostMapping("/failure")
    public Rest loginFailure() {

        return RestBody.failure(HttpStatus.UNAUTHORIZED.value(), "登录失败了，老哥");
    }

    /**
     * 登录成功后拿到个人信息.
     *
     * @return the rest
     */
    @PostMapping("/success")
    public Rest loginSuccess() {
          // 登录成功后用户的认证信息 UserDetails会存在 安全上下文寄存器 SecurityContextHolder 中
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        SysUser sysUser = sysUserService.queryByUsername(username);
        // 脱敏
        sysUser.setEncodePassword("[PROTECT]");
        return RestBody.okData(sysUser,"登录成功");
    }
}
