package com.tweetapp.dao;

import java.util.List;
import java.util.Optional;

import com.tweetapp.model.User;

public interface UserDao {

	Optional<User> getByEmail(String email);

	List<User> getAll();

	boolean save(User user);

	boolean update(User user);
}
