package com.springboot.sample.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import com.springboot.sample.interceptor.TokenInterceptor;

public class MyWebAppConfigurer extends WebMvcConfigurationSupport {
	
	/**
	 * 加入新增的拦截器到拦截器链上
	 */
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截，该处/login不需要拦截
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**").excludePathPatterns("/login");
        super.addInterceptors(registry);
    }
}
