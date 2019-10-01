package com.example.demo.auth.provider;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.example.demo.auth.dto.AccessTokenDTO;
import com.example.demo.auth.dto.GithubUser;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class GitHubTokenProvider {
	public String getToken(AccessTokenDTO dto) {
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");

		OkHttpClient client = new OkHttpClient();

		String url = "https://github.com/login/oauth/access_token";

		RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(dto));
		Request request = new Request.Builder().url(url).post(body).build();

		try (Response response = client.newCall(request).execute()) {
			String result = response.body().string();
			System.out.println(result);
			result = result.split("=")[1].split("&")[0];
			System.out.println(result);
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	public GithubUser getGithubUserInfo(String accessToken) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url("https://api.github.com/user?access_token=" + accessToken).build();

		try (Response response = client.newCall(request).execute()) {
			String result = response.body().string();

			GithubUser githubUser = JSON.parseObject(result, GithubUser.class);
			System.out.println(result);
			return githubUser;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

}
