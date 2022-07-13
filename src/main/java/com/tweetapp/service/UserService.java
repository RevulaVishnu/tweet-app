package com.tweetapp.service;

import java.util.List;
import java.util.Optional;

import com.tweetapp.dao.impl.UserDaoImpl;
import com.tweetapp.model.User;

public class UserService {

	UserDaoImpl userDao = new UserDaoImpl();

	public User checkUserCredentials(String email, String password) {
		Optional<User> userOptional = userDao.getByEmail(email);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (password.equals(user.getPassword())) {
				return user;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public boolean registerUser(User user) {
		return userDao.save(user);
	}

	public boolean emailAlreadyExist(String email) {
		Optional<User> userOptional = userDao.getByEmail(email);
		return userOptional.isPresent();
	}

	public boolean updateLoginStatus(User user, boolean status) {
		user.setOnline(status);
		return userDao.update(user);
	}

	public User getUserByEmail(String email) {
		Optional<User> userOptional = userDao.getByEmail(email);
		User user = null;
		if (userOptional.isPresent()) {
			user = userOptional.get();
		}
		return user;
	}

	public boolean updateUserPassword(User user, String password) {
		user.setPassword(password);
		return userDao.update(user);
	}

	public List<User> getAllUsers() {
		return userDao.getAll();
	}

}
