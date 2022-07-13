# tweet-app
Tweet App is java console application for registered users to post new tweets, view tweets by other users.

Guest users cannot see any tweets.
The core modules of tweet app are:
1.	User registration and login
2.	Post tweet
3.	View all tweets (all from logged in users account)
4.	View users and their respective tweets
5.	Forgot password option to reset password.

Application Language -> Java
Tooling ->	Maven
Data structures and Algorithms -> Data structures and Algorithms
Database Access ->	SQL

Write a program for Tweet App with below guidelines:

PRESENTATION:
1.	A user can register and login.
2.	Use below details while registration:
	a.	first_name (required)
	b.	last_name (optional)
	c.	gender (required)
	d.	dob (optional, dd-MM-yyyy)
	e.	email (required, email will must be used as login-id so must be unique)
	f.	password (required)
3.	A logged-in user can change his password (old password is required).
4.	A logged-in user:
	a.	Can post new tweet.
	b.	Can view all his tweets.
	c.	Can view all registered users and their respective tweets.
5.	A user can reset old password.

DATABASE, STORAGE:
1.	As the twitter application I should –
	a.	Create user when successful registration is done
	b.	Create new tweet when user tweets
	c.	Find all users and their tweets
	d.	Update user’s password

DEBUGGING & TROUBLESHOOTING:
1.	As the programmer I should:
	a.	Debug & Troubleshoot the code which has bugs with breakpoints / pause 
	b.	Ensure optimal resource utilization & close unneeded connections
	c.	Ensure all unhandled exceptions are addressed 
	d.	Query the database only for the necessary information (user info/tweets etc) with proper indexes & order.

RUBRICS/EXPECTED DELIVERABLES:

•	Implement java 8 features – lambda, stream, map, etc.
•	Java Project must be a Maven Project
•	Project must be initiated using Maven CLI
•	Email id should be used as username/userid
•	Use db.properties to store database parameters.
•	Package Structure for project will be like com.tweetapp.* with proper naming conventions for package and beans.
•	Use dao layer, service layer.
•	Use proper layering – database interaction code should be only in DAO classes. DAO classes should not have UI/Business logic. Service classes will have all the business logic.
•	Input validations must be handled
•	Use custom exception and show a valid error message to user if something goes wrong.
•	Use java data structure algorithm to reset password. 
•	Build the project.

SUPPORT GUIDELINES:
1.	Application shall be a java console application
2.	Application must be menu driven
3.	Application will comprise of two menu
	a.	Introduction menu for Non-logged in user
		i.	Register
		ii.	Login
		iii.	Forgot Password
	b.	Menu for logged in user
		i.	Post a tweet
		ii.	View my tweets
		iii.	View all tweets
		iv.	View All Users
		v.	Reset Password
		vi.	Logout 
4.	Menu must show iteratively after every operation
5.	Maintain the status of currently logged in user in DB
6.	Update the status when user logs out
