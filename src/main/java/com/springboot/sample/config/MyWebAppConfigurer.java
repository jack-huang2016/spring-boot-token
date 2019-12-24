package com.springboot.sample.config;

import com.springboot.sample.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
	
	/**
	 * 加入新增的拦截器到拦截器链上
	 */
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		// 用户排除拦截的路径集合
		List<String> excludePathList = new ArrayList<>();
		String excludePath1 = "/login";
		//String excludePath2 = "/common/send";
		//String excludePath3 = "/common/validationCode";
		excludePathList.add(excludePath1);
		//excludePathList.add(excludePath2);
		//excludePathList.add(excludePath3);

        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截，该集合里的路径不需要拦截
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePathList);
		WebMvcConfigurer.super.addInterceptors(registry);
    }
}
