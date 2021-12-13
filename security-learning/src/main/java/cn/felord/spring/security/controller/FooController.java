package cn.felord.spring.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FooController
 *
 * @author Felordcn
 * @since 9 :36 2019/10/12
 */
@RestController
@RequestMapping("/foo")
@Slf4j
public class FooController {


    /**
     * Test string.
     *
     * @return the string
     */
    @GetMapping("/test")
    public String test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("current authentication: 【 {} 】", authentication);
        return "success";
    }


}
