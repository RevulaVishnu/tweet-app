package com.tweetapp.model;

import java.sql.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

	private int id;
	private String firstName;
	private String lastName;
	private String gender;
	private Date dob;
	private String email;
	private String password;
	private boolean online;
	private Timestamp createdAt;
}
