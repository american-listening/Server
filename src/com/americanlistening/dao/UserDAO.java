package com.americanlistening.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.americanlistening.core.User;

/**
 * DAO for users.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class UserDAO implements DAO<User> {

	private List<User> users;
	
	/**
	 * Creates a new User DAO.
	 */
	public UserDAO() {
		users = new ArrayList<>();
	}
	
	@Override
	public Optional<User> get(long id) {
		return Optional.ofNullable(users.get((int) id));
	}

	@Override
	public List<User> getAll() {
		return users;
	}

	@Override
	public void save(User user) {
		users.add(user);
	}

	@Override
	public void update(User user, Map<String, String> params) {
		User.update(user, params);
	}

	@Override
	public void delete(User user) {
		users.remove(user);
	}

}
