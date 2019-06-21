package com.americanlistening.core;

/**
 * Abstract class representing requirements for creating users.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public abstract class CreationParameters {
	
	/**
	 * Constants representing types of invalid attributes.
	 * 
	 * @author Ethan Vrhel
	 * @since 1.0
	 */
	public static enum InvalidityType {
		NULL(-1), NONE(0), USERNAME(1), EMAIL(2), PASSWORD(3);
		
		private int code;
		
		private InvalidityType(int code) {
			this.code = code;
		}
		
		/**
		 * Returns the code of this type.
		 * 
		 * @return The code.
		 */
		public int code() {
			return code;
		}
	}

	/**
	 * Checks the validity of a creation info.
	 * 
	 * @param info The info to check.
	 * @return The invalidity type.
	 */
	public abstract CreationParameters.InvalidityType check(UserCreateInfo info);
}
