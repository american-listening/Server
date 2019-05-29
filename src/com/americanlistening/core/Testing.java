package com.americanlistening.core;

import java.util.List;

import com.americanlistening.dao.DAO;
import com.americanlistening.dao.UserDAO;

public class Testing {

	public static DAO<User> userDAO = new UserDAO();
	
	static {
		User user = new User();
		user.email = "someone@domain.com";
		user.username = "username";
		user.password = "password";
		userDAO.save(user);
	}
	
	public static boolean testValidate(String username, String password) {
		List<User> users = userDAO.getAll();
		for (User user : users) {
			if (user.username.equals(username) && user.password.equals(password))
				return true;
		}
		return false;
	}
}
