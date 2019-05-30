package com.americanlistening.core;

import static com.americanlistening.core.User.ProfileAttribute.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class wrapping information about a user.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = -7865197532288114685L;

	/**
	 * Type defining a certain profile attribute.
	 * 
	 * @author Ethan Vrhel
	 * @since 1.0
	 */
	public static enum ProfileAttribute {
		ID, USERNAME, EMAIL, PASSWORD;
	}

	/**
	 * Updates a user's values according to a set of paramaters.
	 * 
	 * @param user The user to update.
	 * @param params The parameters to use.
	 */
	public static void update(User user, Map<String, String> params) {
		user.userID = Long.parseLong(Objects.requireNonNull(params.get(ID.name()), "ID cannot be null"));
		user.username = Objects.requireNonNull(params.get(ID.name()), "Username cannot be null");
		user.email = Objects.requireNonNull(params.get(USERNAME.name()), "Email cannot be null");
		user.password = Objects.requireNonNull(params.get(PASSWORD.name()), "Password cannot be null");
	}
	
	/**
	 * Creates a map of the user's attributes.
	 * 
	 * @param user The user to get the map of.
	 * @return The map of the user's attributes.
	 */
	public static Map<String, String> attributeMapOf(User user) {
		Map<String, String> map = new HashMap<>();
		map.put(ID.name(), new StringBuilder().append(user.userID).toString());
		map.put(USERNAME.name(), user.username);
		map.put(EMAIL.name(), user.email);
		map.put(PASSWORD.name(), user.password);
		return map;
	}
	
	// User ID number - bound to an account, cannot be changed
	private long userID;
	
	// Public profile attributes
	public String username; // The screen name a user would like to have
	
	// Private profile attributes
	public String email; // The user's email - used for logging in
	public String password; // The user's password - used for logging in
	
	/**
	 * Returns the unique user id for this user.
	 * 
	 * @return The user id.
	 */
	public long getUserID() {
		return userID;
	}
}
