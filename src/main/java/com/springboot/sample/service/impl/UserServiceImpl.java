package com.springboot.sample.service.impl;

import org.springframework.stereotype.Service;
import com.springboot.sample.entity.User;
import com.springboot.sample.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	/**
	 * 用户名密码的身份验证
	 */
	@Override
	public boolean checkUser(String loginName, String password) {
		return true;
	}

	@Override
	public User getUserByLoginName(String loginName) {
		User user = new User();
        user.setId("2019");
        user.setName("hyj");
        user.setAge(18);
        return user;
	}

}
