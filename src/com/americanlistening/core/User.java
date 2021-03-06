package com.americanlistening.core;

import static com.americanlistening.core.User.ProfileAttribute.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
	 * Returns the name of a profile attribute.
	 * 
	 * @param attrib The profile attribute.
	 * @return Its name.
	 */
	public static String nameOf(ProfileAttribute attrib) {
		return attrib.name();
	}

	/**
	 * Updates a user's values according to a set of parameters.
	 * 
	 * @param user   The user to update.
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

	public static void rebaseFollowers(User user, Instance inst) {

	}

	/**
	 * Tests whether two users have conflicting values.
	 * 
	 * @param user0 The first user to test.
	 * @param user1 The second user to test.
	 * @return <code>true</code> if the users conflict and <code>false</code>
	 *         otherwise.
	 */
	public static boolean isConflicting(User user0, User user1) {
		return user0.email.equalsIgnoreCase(user1.email) || user0.username.equalsIgnoreCase(user1.username)
				|| user0.userID == user1.userID;
	}

	// User ID number - bound to an account, cannot be changed
	private long userID;

	// Public profile attributes
	public String username; // The screen name a user would like to have
	public List<Long> following; // Who the user is following
	public List<Long> followers; // Who the user is followed by

	// Private profile attributes
	public String email; // The user's email - used for logging in
	public String password; // The user's password - used for logging in
	
	/**
	 * Creates a new user.
	 * 
	 * @param userid The user ID to usee.
	 */
	public User(long userid) {
		userID = userid;
	}

	/**
	 * Returns the unique user id for this user.
	 * 
	 * @return The user id.
	 */
	public long getUserID() {
		return userID;
	}
}
