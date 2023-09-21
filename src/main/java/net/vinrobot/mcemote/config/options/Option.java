package net.vinrobot.mcemote.config.options;

import java.util.Objects;
import java.util.Optional;

public class Option<T> {
	static <T> Option<T> of(final T defaultValue) {
		return new Option<>(defaultValue);
	}

	private final T defaultValue;
	private Optional<T> value = Optional.empty();

	protected Option(final T defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Set the value of this option.
	 *
	 * @param value The optional value to set.
	 * @return This option.
	 */
	public Option<T> set(final Optional<T> value) {
		value.ifPresent(this::validate);
		this.value = Objects.requireNonNull(value);
		return this;
	}

	/**
	 * Set the value of this option.
	 *
	 * @param value The value to set. Must not be null.
	 * @return This option.
	 */
	public Option<T> set(final T value) {
		return this.set(Optional.of(value));
	}

	/**
	 * Reset the value of this option.
	 *
	 * @return This option.
	 */
	public Option<T> reset() {
		return this.set(Optional.empty());
	}

	/**
	 * Get the value of this option. Returns the default value if the value is not set.
	 *
	 * @return The value of this option.
	 */
	public T get() {
		return this.getRaw().orElseGet(this::getDefault);
	}

	/**
	 * Get the raw value of this option.
	 *
	 * @return The raw value of this option.
	 */
	public Optional<T> getRaw() {
		return this.value;
	}

	/**
	 * Get the default value of this option.
	 *
	 * @return The default value of this option.
	 */
	public T getDefault() {
		return this.defaultValue;
	}

	/**
	 * Validate the value of this option.
	 *
	 * @param value The value to validate.
	 * @throws ValidationFailedException If the validation fails.
	 */
	public void validate(final T value) throws ValidationFailedException {
		Objects.requireNonNull(value);
	}

	/**
	 * Check if the value of this option is valid.
	 *
	 * @param value The value to check.
	 * @return True if the value is valid, false otherwise.
	 * @see #validate(T)
	 */
	public boolean isValid(final T value) {
		try {
			this.validate(value);
			return true;
		} catch (ValidationFailedException e) {
			return false;
		}
	}
}
