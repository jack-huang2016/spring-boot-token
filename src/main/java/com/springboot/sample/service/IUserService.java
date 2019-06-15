package com.springboot.sample.service;

import com.springboot.sample.entity.User;

public interface IUserService {
	 
	boolean checkUser(String loginName, String password);

	User getUserByLoginName(String loginName);
}
