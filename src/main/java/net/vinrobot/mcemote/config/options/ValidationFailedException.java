package net.vinrobot.mcemote.config.options;

public class ValidationFailedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ValidationFailedException(final String message) {
		super(message);
	}

	public ValidationFailedException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ValidationFailedException(final Throwable cause) {
		super(cause);
	}
}
