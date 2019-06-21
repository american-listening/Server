package com.americanlistening.core;

import static com.americanlistening.core.DataRequest.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.americanlistening.dao.DAO;

/**
 * Represents an instance of the server.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class Instance {

	/**
	 * File path of the instance configuration.
	 */
	public static final String INSTANCE_CONFIGURATION = "D:\\Ethan\\jee-workspace\\AmericanListening\\instance.config";

	/**
	 * The current instance.
	 */
	public static final Instance currentInstance = new Instance();

	/**
	 * Interface for instance main.
	 * 
	 * @author Ethan Vrhel
	 * @since 1.0
	 */
	public static interface InstanceMain {

		/**
		 * Main method called when an instance is created.
		 * 
		 * @param inst The instance that was created.
		 */
		public void main(Instance inst);
	}

	private Map<String, String> config;

	private InstanceMain main;
	private List<DAO<?>> daos;

	private Map<Long, User> users;

	private CreationParameters params;

	private Instance() {
		this.daos = new ArrayList<>();
		this.config = new HashMap<>();
		this.users = new HashMap<>();
		//loadConfig();
		//this.main = locateInstanceMain();
		//this.main.main(this);
	}

	/**
	 * Returns the name of this instance.
	 * 
	 * @return The name, or a blank string if none exists.
	 */
	public String name() {
		String n = config.get("name");
		return n == null ? "" : n;
	}

	/**
	 * Adds a DAO to the current instance.
	 * 
	 * @param dao The DAO to add.
	 */
	public void addDAO(DAO<?> dao) {
		if (!daos.contains(dao))
			daos.add(dao);
		allDAOs();
	}

	/**
	 * Returns all DAOs bound to this instance.
	 * 
	 * @return All DAOs.
	 */
	public DAO<?>[] allDAOs() {
		return daos.toArray(new DAO<?>[daos.size()]);
	}

	@SuppressWarnings("unchecked")
	public <T extends DAO<?>> T getDAO(Class<T> t) {
		for (DAO<?> dao : daos) {
			if (dao.getClass() == t)
				return (T) dao;
		}
		return null;
	}

	/**
	 * Registers a new user on the server.
	 * 
	 * @param cinf The creation info for the user.
	 * @return The new user.
	 * @throws RegistrationException When an error occurs while registering the
	 *                               user.
	 */
	public User registerUser(UserCreateInfo cinf) throws RegistrationException {
		long id = -1L;
		try {
			id = generateID();
		} catch (RuntimeException e) {
			throw new RegistrationException("No valid user ID", e);
		}
		User user = new User(id);
		user.username = cinf.username;
		user.password = cinf.password;
		user.email = cinf.email;
		if (!userValid(user))
			throw new RegistrationException("User has values that match existing users.");
		users.put(user.getUserID(), user);
		return user;
	}

	/**
	 * Returns all users on the platform.
	 * 
	 * @return All users.
	 */
	public User[] allUsers() {
		return users.values().toArray(new User[users.size()]);
	}

	/**
	 * Returns the user by its id.
	 * 
	 * @param id The user id.
	 * @return The respective user, or <code>null</code> if it does not exist.
	 */
	public User getUser(long id) {
		return users.get(id);
	}

	/**
	 * Returns the user by its username.
	 * 
	 * @param username The username.
	 * @return The respective user, or <code>null</code> if it does not exist.F
	 */
	public User getUser(String username) {
		for (User user : users.values()) {
			if (user.username.equalsIgnoreCase(username))
				return user;
		}
		return null;
	}
	
	public String getData(DataRequest request) {
		System.out.println(request);
		switch (request.requestType) {
		case USER_PARAM_REQUEST:
			long userID = Long.parseLong(request.params[0]);
			User user = getUser(userID);
			System.out.println(user);
			if (user == null)
				break;
			Map<String, String> map = User.attributeMapOf(user);
			return map.get(request.params[1]);
		}
		return null;
	}

	/**
	 * Returns the creation parameters that will be used when registering a user.
	 * 
	 * @return The creation parameters.
	 */
	public CreationParameters getCreationParameters() {
		return params;
	}

	/**
	 * Sets the parameters that will be used when a user tries to register.
	 * 
	 * @param params The creation parameters.
	 */
	public void setCreationParameters(CreationParameters params) {
		this.params = params;
	}

	private void loadConfig() {
		File f = new File(INSTANCE_CONFIGURATION);
		if (!f.exists())
			throw new Error("No existing instance configuration.  Looking for: " + INSTANCE_CONFIGURATION);
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(f);
			byte[] b = new byte[(int) f.length()];
			fin.read(b, 0, b.length);
			String str = new String(b);
			String[] tokens0 = str.split("\n");
			for (String line : tokens0) {
				String[] tokens = line.split("=");
				if (tokens.length != 2)
					throw new Error("Config file has an invalid format.");
				config.put(tokens[0], tokens[1]);
			}
			if (!config.containsKey("main"))
				System.err.println("Warning: config does not specify an entry point");
		} catch (IOException e) {
			throw new Error("Failed to read instance config file.", e);
		} finally {
			if (fin != null)
				try {
					fin.close();
				} catch (IOException e) {
					throw new Error("Unexpected error closing a " + fin.getClass(), e);
				}
		}
		System.out.println("Config loaded " + config.size() + " elements!");
	}

	private InstanceMain locateInstanceMain() {
		String cpath = config.get("main");
		if (cpath == null)
			throw new Error("No valid keyvalue pair found for key \"main\"");
		try {
			Class<?> c = Class.forName(cpath, true, ClassLoader.getSystemClassLoader());
			Class<?>[] ints = c.getInterfaces();
			boolean foundint = false;
			for (Class<?> inter : ints) {
				if (inter == InstanceMain.class) {
					foundint = true;
					break;
				}
			}
			if (!foundint) {
				throw new Error("Cannot instantiate valid main class because class " + c + " does not implement "
						+ InstanceMain.class);
			}
			try {
				return (InstanceMain) c.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new Error("Failed to create new instance of " + c, e);
			}
		} catch (ClassNotFoundException e) {
			throw new Error("Class \"" + cpath + "\" does not exist in the current context.", e);
		}
	}

	private boolean userValid(User test) {
		for (User user : users.values()) {
			if (User.isConflicting(user, test))
				return false;
		}
		return true;
	}

	private Long generateID() throws RuntimeException {
		if (true)
			return 1L;
		// TODO: Optimize generation (currently will be very slow with many users)
		Collection<User> users = this.users.values();
		long currentIteration = 1L;
		boolean notfound = true;
		search0: while (notfound) {
			if (currentIteration == Long.MAX_VALUE)
				throw new RuntimeException("No values avaliable.");
			for (User user : users) {
				if (currentIteration == user.getUserID()) {
					currentIteration++;
					continue search0;
				}
			}
			notfound = true;
		}
		return currentIteration;
	}
}
