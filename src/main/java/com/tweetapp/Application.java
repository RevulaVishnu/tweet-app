package com.tweetapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.tweetapp.exception.DatabaseException;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;
import com.tweetapp.service.TweetService;
import com.tweetapp.service.UserService;
import com.tweetapp.validation.TweetValidator;
import com.tweetapp.validation.UserValidator;

public class Application {
	static BufferedReader br;
	static UserService userService = new UserService();
	static TweetService tweetService = new TweetService();
	static UserValidator userValidator = new UserValidator();
	static TweetValidator tweetValidator = new TweetValidator();

	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		int choice;
		do {
			showDisplayMessage("Welcome to Tweet App");
			showloggedOutOptions();
			try {
				choice = Integer.parseInt(br.readLine());
			} catch (NumberFormatException ex) {
				showMessage("ERROR! Please Enter Correct Choice");
				break;
			}
			switch (choice) {
			case 1:
				System.out.println();
				register_user();
				break;
			case 2:
				System.out.println();
				login_user();
				break;
			case 3:
				System.out.println();
				reset_password();
				break;
			case 4:
				System.out.println();
				showMessage("Thank You for Using Tweet App.");
				break;
			default:
				showMessage("ERROR! Please Enter Correct Choice");
				break;
			}
		} while (choice != 4);
		br.close();
	}

	private static void register_user() throws IOException {
		showDisplayMessage("User Registration Form:");
		String firstName, lastName, gender, dob, email, password, confirm_password;
		System.out.print("\t\tEnter First Name(Required): ");
		firstName = br.readLine().trim();
		System.out.print("\t\tEnter Last Name(Optional): ");
		lastName = br.readLine().trim();
		System.out.print("\t\tEnter Gender(Required) [male/female]: ");
		gender = br.readLine().toLowerCase().trim();
		System.out.print("\t\tEnter Date of Birth(Optional) [dd-MM-yyyy]: ");
		dob = br.readLine().trim();
		System.out.print("\t\tEnter Email(Required): ");
		email = br.readLine().trim();
		System.out.print("\t\tEnter Password(Required): ");
		password = br.readLine().trim();
		System.out.print("\t\tConfirm Password(Required): ");
		confirm_password = br.readLine().trim();

		List<String> validationErrors = userValidator.validate(firstName, lastName, gender, dob, email, password,
				confirm_password);
		if (validationErrors.isEmpty()) {
			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date dateOfBirth = null;
			try {
				dateOfBirth = formatter.parse(dob);
			} catch (ParseException e) {
				System.out.println("Invalid Date Format");
			}
			User user = new User(0, firstName, lastName, gender, dateOfBirth, email, confirm_password, false, null);
			if (userService.registerUser(user)) {
				showMessage("User Registration Successful.");
			} else {
				showMessage("User Registration Failed!");
				throw new DatabaseException("Database Error: Unable to save to Database");
			}
		} else {
			showMessage("Validation Failed!");
			validationErrors.forEach(error -> System.out.println("\t\tERROR -> " + error));
		}
	}

	private static void login_user() throws IOException {
		String email, password;
		showDisplayMessage("User Login Form:");
		System.out.print("\t\tEnter Email: ");
		email = br.readLine().trim();
		System.out.print("\t\tEnter Password: ");
		password = br.readLine().trim();

		User user = userService.checkUserCredentials(email, password);
		if (Objects.isNull(user)) {
			showMessage("Incorrect Username or Password");
		} else {
			showMessage("User Logged in Successfully.");
			userService.updateLoginStatus(user, true);
			processLoggedInUser(user);
		}
	}

	private static void reset_password() throws IOException {
		showDisplayMessage("Reset Password Form:");
		System.out.print("\t\tEnter your Email to Reset Password: ");
		String email = br.readLine().trim();
		if (userService.emailAlreadyExist(email)) {
			System.out.print("\t\tEnter New Password: ");
			String password = br.readLine().trim();
			System.out.print("\t\tConfirm Password: ");
			String confirmPassword = br.readLine().trim();

			List<String> validationErrors = userValidator.validate(password, confirmPassword);
			if (validationErrors.isEmpty()) {
				User user = userService.getUserByEmail(email);
				if (userService.updateUserPassword(user, password)) {
					showMessage("Password Updated Successfully.");
				} else {
					showMessage("Unable to Reset Password.");
				}
			} else {
				showMessage("Validation Failed!");
				validationErrors.forEach(error -> System.out.println("\t\tERROR -> " + error));
			}
		} else {
			showMessage("Email doesn't exist in Database.");
		}
	}

	private static void processLoggedInUser(User user) throws IOException {
		int choice;
		do {
			showDisplayMessage("Logged In as " + user.getEmail());
			showloggedInOptions();
			try {
				choice = Integer.parseInt(br.readLine());
			} catch (NumberFormatException ex) {
				showMessage("ERROR! Please Enter Correct Choice");
				break;
			}
			switch (choice) {
			case 1:
				System.out.println();
				post_tweet(user);
				break;
			case 2:
				System.out.println();
				viewMyTweets(user);
				break;
			case 3:
				System.out.println();
				viewAllTweets(user);
				break;
			case 4:
				System.out.println();
				viewTweetsOfUsers(user);
				break;
			case 5:
				System.out.println();
				changePassword(user);
				break;
			case 6:
				System.out.println();
				userService.updateLoginStatus(user, false);
				showMessage("Logged out successfully.");
				break;
			default:
				showMessage("ERROR! Please Enter Correct Choice");
				break;
			}
		} while (choice != 6);
	}

	private static void post_tweet(User user) throws IOException {
		showDisplayMessage("Post a Tweet");
		System.out.println("\t\tEnter Your Tweet(Max 300 chars): [PRESS ENTER TO POST YOUR TWEET]");
		String tweetInput = br.readLine().trim().replace("'", "'");

		List<String> validationErrors = tweetValidator.validate(tweetInput);
		if (validationErrors.isEmpty()) {
			Tweet tweet = new Tweet(0, tweetInput, user.getEmail(), null);
			if (tweetService.postTweet(tweet)) {
				showMessage("Tweet Posted Successfully");
			} else {
				showMessage("Unable to Post the Tweet");
				throw new DatabaseException("Database Error: Unable to save to Database");
			}
		} else {
			showMessage("Validation Failed!");
			validationErrors.forEach(error -> System.out.println("\t\tERROR -> " + error));
		}
	}

	private static void viewMyTweets(User user) {
		showDisplayMessage("Your Tweets");
		List<Tweet> myTweets = tweetService.getTweetsByEmail(user.getEmail());
		System.out.println();
		if (myTweets.isEmpty()) {
			showMessage("No Tweets to show.");
		} else {
			myTweets.forEach(Application::displayTweet);
		}
	}

	private static void displayTweet(Tweet tweet) {
		System.out.println("Tweet -> " + tweet.getValue());
		System.out.println("by: " + tweet.getTweetedBy() + "\tat: " + tweet.getCreatedAt().toLocalDateTime());
		System.out.println();
	}

	private static void viewAllTweets(User user) {
		showDisplayMessage("All Tweets");
		List<Tweet> allTweets = tweetService.getAllTweets();
		System.out.println();
		if (allTweets.isEmpty()) {
			showMessage("No Tweets to show.");
		} else {
			allTweets.forEach(Application::displayTweet);
		}
	}

	private static void viewTweetsOfUsers(User user) throws NumberFormatException, IOException {
		showDisplayMessage("Tweets Of User");
		List<User> allUsers = userService.getAllUsers();
		int ch = 1;
		for (User u : allUsers) {
			System.out.println(ch + ") " + u.getEmail());
			ch++;
		}
		System.out.print("Enter Your Choice: ");
		int userChoice = 0;
		try {
			userChoice = Integer.parseInt(br.readLine());
		} catch (NumberFormatException ex) {
			showMessage("ERROR! Please Enter Correct Choice");
		}
		if (userChoice > 0 && userChoice <= allUsers.size()) {
			userChoice--;
			List<Tweet> userTweets = tweetService.getTweetsByEmail(allUsers.get(userChoice).getEmail());
			System.out.println();
			if (userTweets.isEmpty()) {
				showMessage("No Tweets to show.");
			} else {
				showMessage("Tweets of " + allUsers.get(userChoice).getEmail());
				userTweets.forEach(Application::displayTweet);
			}
		} else {
			showMessage("Please Enter Correct Choice");
		}
	}

	private static void changePassword(User user) throws IOException {
		showDisplayMessage("Change Your Password");
		String oldPassword, newPassword, confirmPassword;
		System.out.print("\t\tEnter Your Old Password: ");
		oldPassword = br.readLine().trim();
		if (oldPassword.equals(user.getPassword())) {
			System.out.print("\t\tEnter New Password: ");
			newPassword = br.readLine().trim();
			System.out.print("\t\tConfirm Password: ");
			confirmPassword = br.readLine().trim();

			List<String> validationErrors = userValidator.validate(newPassword, confirmPassword);
			if (validationErrors.isEmpty()) {
				if (userService.updateUserPassword(user, newPassword)) {
					showMessage("Password Updated Successfully.");
				} else {
					showMessage("Unable to Change Password.");
				}
			} else {
				showMessage("Validation Failed!");
				validationErrors.forEach(error -> System.out.println("\t\tERROR -> " + error));
			}
		} else {
			showMessage("Wrong Password.");
		}
	}

	private static void showDisplayMessage(String string) {
		System.out.println("****************************************************************************************");
		System.out.println("\t\t\t\t" + string + "");
		System.out.println("****************************************************************************************");
	}

	private static void showMessage(String string) {
		System.out.println();
		System.out.println("\t\t\t" + string + "");
		System.out.println();
	}

	private static void showloggedOutOptions() {
		System.out.println("1) Register");
		System.out.println("2) Login");
		System.out.println("3) Forgot Password");
		System.out.println("4) Exit");
		System.out.print("Enter Your Choice: ");
	}

	private static void showloggedInOptions() {
		System.out.println("1) Post a Tweet");
		System.out.println("2) View my Tweets");
		System.out.println("3) View all Tweets");
		System.out.println("4) View Tweets of users");
		System.out.println("5) Change Password");
		System.out.println("6) Logout");
		System.out.print("Enter Your Choice: ");
	}

}
