package com.americanlistening.dao;

import com.americanlistening.core.Instance;
import com.americanlistening.core.RegistrationException;
import com.americanlistening.core.User;
import com.americanlistening.core.UserCreateInfo;

/**
 * DAO handling user registration.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class RegistrationDAO {

	/**
	 * Registers a user.
	 * 
	 * @param info The registration info.
	 * @throws RegistrationException When an error occurs.
	 */
	public User register(UserCreateInfo info) throws RegistrationException {
		return Instance.currentInstance.registerUser(info);
	}
}
