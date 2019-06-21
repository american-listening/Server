package com.americanlistening.core;

/**
 * A registration exception is thrown when an error occurs while registering a
 * new user.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class RegistrationException extends Exception {

	private static final long serialVersionUID = -93669294963000348L;

	private CreationParameters.InvalidityType invalidity;

	public RegistrationException() {
		super();
	}

	public RegistrationException(String message) {
		super(message);
	}

	public RegistrationException(String message, Throwable cause) {
		super(message, cause);
	}

	public RegistrationException(Throwable cause) {
		super(cause);
	}

	public RegistrationException(CreationParameters.InvalidityType invalidity) {
		super();
		this.invalidity = invalidity;
	}

	public RegistrationException(String message, CreationParameters.InvalidityType invalidity) {
		super(message);
		this.invalidity = invalidity;
	}

	public RegistrationException(String message, Throwable cause, CreationParameters.InvalidityType invalidity) {
		super(message, cause);
		this.invalidity = invalidity;
	}

	public RegistrationException(Throwable cause, CreationParameters.InvalidityType invalidity) {
		super(cause);
		this.invalidity = invalidity;
	}

	/**
	 * Returns the invalidity that caused this exception.
	 * 
	 * @return The invalidity that caused the exception, or <code>null</code> if it
	 *         is unknown.
	 */
	public CreationParameters.InvalidityType invalidityType() {
		return invalidity;
	}
}
