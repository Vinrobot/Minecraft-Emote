package net.vinrobot.mcemote.config.impl;

import net.vinrobot.mcemote.config.ValidationFailedException;

public class TwitchIdOptionImpl extends OptionImpl<String> {
	public TwitchIdOptionImpl(final String defaultValue) {
		super(defaultValue);
	}

	@Override
	public void validate(final String value) {
		super.validate(value);

		final int length = value.length();
		if (length == 0) {
			throw new ValidationFailedException("Twitch ID cannot be empty");
		}

		try {
			Long.parseLong(value);
		} catch (final NumberFormatException e) {
			throw new ValidationFailedException("Twitch ID must be a number");
		}
	}
}
