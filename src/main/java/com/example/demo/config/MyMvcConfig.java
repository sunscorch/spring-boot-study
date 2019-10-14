package com.example.demo.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.componnet.LoginHandlerInterceptor;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
	@Resource
	LoginHandlerInterceptor loginHandlerInterceptor;
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/welcome").setViewName("index");
		registry.addViewController("/").setViewName("login");
		registry.addViewController("/index.html").setViewName("login");
		registry.addViewController("/main.html").setViewName("dashboard");

	}
	
	@Override
	 public void addInterceptors(InterceptorRegistry registry) {
		
		  registry.addInterceptor(loginHandlerInterceptor).addPathPatterns("/**")
		  .excludePathPatterns("/index.html","/","/user/login")
		  .excludePathPatterns("/webjars/**","/asserts/**");
		 

	}

}
