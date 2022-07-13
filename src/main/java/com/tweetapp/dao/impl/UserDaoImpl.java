package com.tweetapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import com.tweetapp.dao.UserDao;
import com.tweetapp.model.User;
import com.tweetapp.util.ConnectionUtil;

public class UserDaoImpl implements UserDao {

	static Connection con = null;

	static {
		con = ConnectionUtil.getConnection();
	}

	public Optional<User> getByEmail(String email) {
		try {
			String sql = "SELECT * FROM users WHERE email='" + email + "' ORDER BY created_at DESC";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("gender"), rs.getDate("dob", Calendar.getInstance()), rs.getString("email"),
						rs.getString("password"), rs.getBoolean("online"), rs.getTimestamp("created_at"));
				return Optional.of(user);
			} else {
				return Optional.empty();
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return Optional.empty();
		}
	}

	public List<User> getAll() {
		try {
			String sql = "SELECT * FROM users ORDER BY created_at DESC";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			List<User> users = new ArrayList<User>();
			while (rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("gender"), rs.getDate("dob"), rs.getString("email"), rs.getString("password"),
						rs.getBoolean("online"), rs.getTimestamp("created_at"));
				users.add(user);
			}

			return users;
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return new ArrayList<User>();
		}
	}

	public boolean save(User user) {
		try {
			String sql = "INSERT INTO users(first_name,last_name,gender,dob,email,password) VALUES(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getGender());
			ps.setObject(4, user.getDob().toInstant().atZone(ZoneId.of("Asia/Kolkata")).toLocalDate());
			ps.setString(5, user.getEmail());
			ps.setString(6, user.getPassword());
			int result = ps.executeUpdate();

			if (result > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return false;
		}
	}

	public boolean update(User user) {
		try {
			String sql = "UPDATE users SET password='" + user.getPassword() + "', online=" + user.isOnline()
					+ " WHERE email='" + user.getEmail() + "'";
			Statement stmt = con.createStatement();
			int result = stmt.executeUpdate(sql);

			if (result > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return false;
		}
	}

}
