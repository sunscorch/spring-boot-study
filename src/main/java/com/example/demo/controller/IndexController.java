package com.example.demo.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {
	
	@Autowired
	UserMapper userMapper;
	@RequestMapping("/666")
	String index(HttpServletResponse response, HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length != 0) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("token")) {
					String token = cookie.getValue();
					User user = userMapper.findByToken(token);
					log.info("start to check user exist!");
					if(user != null) {
						request.getSession().setAttribute("user", user);
					}
					break;
				}
			}
		}
		return "index";
	}
}
