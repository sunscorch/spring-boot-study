package com.example.demo.controller;


import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.auth.dto.AccessTokenDTO;
import com.example.demo.auth.dto.GithubUser;
import com.example.demo.auth.provider.GitHubTokenProvider;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AuthController {

	@Autowired
	private GitHubTokenProvider provider;
	@Autowired
	private UserMapper userMapper;

	@RequestMapping("githubLogin")
	void login(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String gitUrl = "https://github.com/login/oauth/authorize";

		String url = UriComponentsBuilder.fromUriString(gitUrl).queryParam("client_id", "d2a86b7266f9cacb6f00")
				.queryParam("redirect_uri", "http://localhost:8081/callback").queryParam("scope", "user")
				.queryParam("state", "1").build().toString();

		response.sendRedirect(url);
	}

	@RequestMapping("callback")
	String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state,
			HttpServletResponse response)
			throws Exception {

		AccessTokenDTO dto = new AccessTokenDTO();
		dto.setClient_id("d2a86b7266f9cacb6f00");
		dto.setClient_secret("08635c8ff5844aa529b47e57d6cbf9293722d606");
		dto.setCode(code);;
		dto.setRedirect_uri("http://localhost:8081/callback");
		dto.setState(state);
		
		
		
		String accessToken = provider.getToken(dto);
		GithubUser githubUser = provider.getGithubUserInfo(accessToken);
		
		User user = new User();
		if(githubUser != null && githubUser.getId() != null) {
			
			String token = UUID.randomUUID().toString();
			user.setGmt_create(System.currentTimeMillis());
			user.setGmt_modify(user.getGmt_create());
			user.setToken(token);
			user.setName(githubUser.getLogin());
			user.setId(githubUser.getId());
			userMapper.insert(user);
			
			log.debug("start to write token into cookie");
			Cookie cookie = new Cookie("token", token);
			response.addCookie(cookie);
			
		}
		
		return "redirect:/";

	}
}
