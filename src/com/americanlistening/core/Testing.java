package com.americanlistening.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.americanlistening.dao.DAO;
import com.americanlistening.dao.UserDAO;
import com.americanlistening.util.ClassIO;

public class Testing implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1563413787817297687L;
	public static DAO<User> userDAO = new UserDAO();
	
	static {
		User user = new User(0L);
		user.email = "someone@domain.com";
		user.username = "username";
		user.password = "password";
		userDAO.save(user);
		
		user = new User(1L);
		user.email = "someone2@domain.com";
		user.username = "abosh";
		user.password = "dumb1234";
		userDAO.save(user);
		
		try {
			String str = ClassIO.export(new Testing());
			Testing t = ClassIO.load(str, Testing.class);
			System.out.println(ClassIO.asString(t));
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
