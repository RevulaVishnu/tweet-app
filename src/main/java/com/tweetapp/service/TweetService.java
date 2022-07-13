package com.tweetapp.service;

import java.util.List;

import com.tweetapp.dao.impl.TweetDaoImpl;
import com.tweetapp.model.Tweet;

public class TweetService {

	TweetDaoImpl tweetDao = new TweetDaoImpl();

	public boolean postTweet(Tweet tweet) {
		return tweetDao.save(tweet);
	}

	public List<Tweet> getTweetsByEmail(String email) {
		return tweetDao.getByEmail(email);
	}

	public List<Tweet> getAllTweets() {
		return tweetDao.getAll();
	}

}
