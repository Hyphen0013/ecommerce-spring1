package com.hyphen.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyphen.config.JwtProvider;
import com.hyphen.exception.UserException;
import com.hyphen.model.User;
import com.hyphen.repository.UserRepository;

@Service
public class UserServiceImplementaion implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	public UserServiceImplementaion(
			UserRepository userRepository,
			JwtProvider jwtProvider
	) {
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public User findUserById(Long userId) throws UserException {
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			return user.get();
		}
		throw new UserException("User not found with this id: " + userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new UserException("User not found this email: " + email);
		}
		return user;
	}

}
