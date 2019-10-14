package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import com.example.demo.componnet.MyLocaleResolver;

@Configuration
public class MyLocaleConfig {
	 @Bean
	    public LocaleResolver localeResolver(){

	        return new MyLocaleResolver();
	    }
}
