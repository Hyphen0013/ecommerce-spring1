package com.hyphen.service;

import com.hyphen.exception.UserException;
import com.hyphen.model.User;

public interface UserService {

	public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
}
