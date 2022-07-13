package com.tweetapp.dao;

import java.util.List;

import com.tweetapp.model.Tweet;

public interface TweetDao {

	List<Tweet> getByEmail(String email);

	List<Tweet> getAll();

	boolean save(Tweet tweet);
}
