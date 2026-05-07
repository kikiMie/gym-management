package com.gym.gymmanagementsystem.config;


import com.gym.gymmanagementsystem.security.SessionAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Spring 提供这个接口就是为了让你在一个地方统一管理所有 Web 相关配置 跨域配置、拦截器等等
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
//拦截器
    private final SessionAuthInterceptor sessionAuthInterceptor;


    public WebMvcConfig(SessionAuthInterceptor sessionAuthInterceptor) {
        this.sessionAuthInterceptor = sessionAuthInterceptor;
    }

//配置跨域访问
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // registry 是 Spring 传给你的"注册表"对象
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true); //携带一些cookie的权限
    }
//注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Spring 传给你的"拦截器注册表"
        registry.addInterceptor(sessionAuthInterceptor)     // 用这个注册表添加拦截器
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/adminLogin",
                        "/api/userLogin",
                        "/api/logout"
                );
    }
}
/*配置层的作用：
✅ 决定拦截器作用于哪些路径（/api/**）
✅ 决定哪些路径排除（登录接口）
✅ 可以添加多个拦截器，设置执行顺序
业务层的作用：
✅ 实现具体的验证逻辑
✅ 判断是否是公开接口
✅ 检查 session
✅ 返回错误信息*/