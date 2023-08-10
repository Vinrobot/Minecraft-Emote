package net.vinrobot.mcemote.config;

import net.vinrobot.mcemote.config.impl.OptionImpl;

import java.util.Objects;
import java.util.Optional;

public interface Option<T> {
	static <T> Option<T> of(final T defaultValue) {
		return new OptionImpl<>(defaultValue);
	}

	static <T> Option<T> of(final T defaultValue, Optional<T> value) {
		final Option<T> option = of(defaultValue);
		option.set(value);
		return option;
	}

	static <T> Option<T> of(final T defaultValue, T value) {
		final Option<T> option = of(defaultValue);
		option.set(value);
		return option;
	}

	/**
	 * Set the value of this option.
	 *
	 * @param value The optional value to set.
	 * @return This option.
	 */
	Option<T> set(Optional<T> value);

	/**
	 * Set the value of this option.
	 *
	 * @param value The value to set. Must not be null.
	 * @return This option.
	 */
	default Option<T> set(T value) {
		return this.set(Optional.of(value));
	}

	/**
	 * Reset the value of this option.
	 *
	 * @return This option.
	 */
	default Option<T> reset() {
		return this.set(Optional.empty());
	}

	/**
	 * Get the value of this option.
	 * Returns the default value if the value is not set.
	 *
	 * @return The value of this option.
	 */
	default T get() {
		return this.getRaw().orElseGet(this::getDefault);
	}

	/**
	 * Get the raw value of this option.
	 *
	 * @return The raw value of this option.
	 */
	Optional<T> getRaw();

	/**
	 * Get the default value of this option.
	 *
	 * @return The default value of this option.
	 */
	T getDefault();

	/**
	 * Validate the value of this option.
	 *
	 * @param value The value to validate.
	 * @throws ValidationFailedException If the validation fails.
	 */
	default void validate(final T value) throws ValidationFailedException {
		Objects.requireNonNull(value);
	}

	/**
	 * Check if the value of this option is valid.
	 *
	 * @see #validate(T)
	 * @param value The value to check.
	 * @return True if the value is valid, false otherwise.
	 */
	default boolean isValid(final T value) {
		try {
			this.validate(value);
			return true;
		} catch (ValidationFailedException e) {
			return false;
		}
	}
}
