![img](https://asset.felord.cn/blog/20200309171604.jpg)

![img](https://asset.felord.cn/blog/20200309170054.png)

![Spring Security Servlet过滤器链组织关系](https://segmentfault.com/img/bVbI6eS)

1. 什么都不配置，走那个默认得主过滤器链？
DelegatingFilterProxy#doFilter#invokeDelegate
默认去走 FilterChainProxy 每一条链

   2.怎么配置 多个 过滤器链条，怎么通过不同得uri  访问不同得过滤器链条
答案就在 https://www.felord.cn/spring-security-muti-httpsecurity.html

DelegatingFilterProxy  主要代理类有成员变量FilterChainProxy

拥有List<SecurityFilterChain>  每一条过滤器链  ，会匹配不同得请求，走不同得过滤器链 的实现是 DefaultSecurityFilterChain

