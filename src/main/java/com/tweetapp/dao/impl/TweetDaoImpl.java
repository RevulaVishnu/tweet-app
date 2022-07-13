package com.tweetapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tweetapp.dao.TweetDao;
import com.tweetapp.model.Tweet;
import com.tweetapp.util.ConnectionUtil;

public class TweetDaoImpl implements TweetDao {

	static Connection con ;

	static {
		con = ConnectionUtil.getConnection();
	}

	public List<Tweet> getByEmail(String email) {
		try {
			String sql = "SELECT * FROM tweets WHERE tweeted_by='" + email + "' ORDER BY created_at DESC";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			List<Tweet> tweets = new ArrayList<>();
			while (rs.next()) {
				Tweet tweet = new Tweet(rs.getInt("id"), rs.getString("value"), rs.getString("tweeted_by"),
						rs.getTimestamp("created_at"));
				tweets.add(tweet);
			}

			return tweets;
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	public List<Tweet> getAll() {
		try {
			String sql = "SELECT * FROM tweets ORDER BY created_at DESC";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			List<Tweet> tweets = new ArrayList<>();
			while (rs.next()) {
				Tweet tweet = new Tweet(rs.getInt("id"), rs.getString("value"), rs.getString("tweeted_by"),
						rs.getTimestamp("created_at"));
				tweets.add(tweet);
			}

			return tweets;
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	public boolean save(Tweet tweet) {
		try {
			String sql = "INSERT INTO tweets(value,tweeted_by) VALUES(?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, tweet.getValue());
			ps.setString(2, tweet.getTweetedBy());
			int result = ps.executeUpdate();

			return result > 0;
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return false;
		}
	}

}
