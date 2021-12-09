package cn.felord.spring.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dax
 */
@MapperScan({"cn.felord.spring.security.mapper*"})
@SpringBootApplication
public class SecurityLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityLearningApplication.class, args);
    }

}
