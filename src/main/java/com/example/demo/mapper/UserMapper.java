package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.model.User;

@Mapper
public interface UserMapper {
	@Insert("insert into user ( account_id, name, token, gmt_create, gmt_modify) "
			+ "values( #{account_id}, #{name}, #{token}, #{gmt_create}, #{gmt_modify})")
	public void insert(User user);
	
	@Select("select * from user where token=#{token}")
	public User findByToken(@Param("token")String token);
}
