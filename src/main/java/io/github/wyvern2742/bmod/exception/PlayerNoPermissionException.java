package io.github.wyvern2742.bmod.exception;

/**
 * Thrown when a player tries to execute a function they do not have permission
 * for. This may also include trying to interact with objects.
 */
public class PlayerNoPermissionException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code PlayerNoPermissionException} with {@code null} as its
	 * error detail message.
	 */
	public PlayerNoPermissionException() {
		super();
	}

	/**
	 * Constructs an {@code PlayerNoPermissionException} with the specified detail
	 * message.
	 *
	 * @param message The detail message (which is saved for later retrieval by the
	 *                {@link #getMessage()} method)
	 */
	public PlayerNoPermissionException(String message) {
		super(message);
	}

	/**
	 * Constructs an {@code PlayerNoPermissionException} with the specified detail
	 * message and cause.
	 *
	 * <p>
	 * Note that the detail message associated with {@code cause} is <i>not</i>
	 * automatically incorporated into this exception's detail message.
	 *
	 * @param message The detail message (which is saved for later retrieval by the
	 *                {@link #getMessage()} method)
	 *
	 * @param cause   The cause (which is saved for later retrieval by the
	 *                {@link #getCause()} method). (A null value is permitted, and
	 *                indicates that the cause is nonexistent or unknown.)
	 *
	 */
	public PlayerNoPermissionException(String message, Throwable cause) {
		super(message, cause);
	}

}
