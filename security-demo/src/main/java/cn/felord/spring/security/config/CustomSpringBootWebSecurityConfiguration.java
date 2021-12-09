package cn.felord.spring.security.config;

import cn.felord.spring.security.filter.JsonLoginPostProcessor;
import cn.felord.spring.security.filter.LoginPostProcessor;
import cn.felord.spring.security.filter.PreLoginFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * CustomSpring
 *
 * @author Felordcn
 * @see org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration
 * @since 14 :58 2019/10/15
 */
@Configuration
@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class CustomSpringBootWebSecurityConfiguration {
    private static final String LOGIN_PROCESSING_URL = "/process";

    /**
     * Json login post processor json login post processor.
     *
     * @return the json login post processor
     */
    @Bean
    public JsonLoginPostProcessor jsonLoginPostProcessor(){
        return new JsonLoginPostProcessor();
    }

    /**
     * Pre login filter pre login filter.
     *
     * @param loginPostProcessors the login post processors
     * @return the pre login filter
     */
    @Bean
    public PreLoginFilter preLoginFilter(Collection<LoginPostProcessor> loginPostProcessors){
        return new PreLoginFilter(LOGIN_PROCESSING_URL,loginPostProcessors);
    }

    /**
     * The type Default configurer adapter.
     */
    @Configuration
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    static class DefaultConfigurerAdapter extends WebSecurityConfigurerAdapter {

      @Resource
      private PreLoginFilter preLoginFilter;



        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            super.configure(auth);
        }

        @Override
        public void configure(WebSecurity web)  {
            super.configure(web);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .cors()
                    .and()
                    .authorizeRequests().anyRequest().authenticated()
                    .and()
                    .addFilterBefore(preLoginFilter, UsernamePasswordAuthenticationFilter.class)
                    .formLogin().loginProcessingUrl(LOGIN_PROCESSING_URL).successForwardUrl("/login/success").failureForwardUrl("/login/failure");

        }
    }
}
