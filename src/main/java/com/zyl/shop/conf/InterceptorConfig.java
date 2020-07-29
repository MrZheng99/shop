package com.zyl.shop.conf;

import com.zyl.shop.interceptor.MInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    MInterceptor mInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       // InterceptorRegistration registration = registry.addInterceptor(mInterceptor);
        //registration.addPathPatterns("/user/**","/checkout/**","/shopping/**","/order/**");
    }
}
