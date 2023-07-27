package net.vinrobot.mcemote.config.impl;

import net.vinrobot.mcemote.config.Option;

import java.util.Objects;
import java.util.Optional;

public class OptionImpl<T> implements Option<T> {
	private final T defaultValue;
	private Optional<T> value = Optional.empty();

	public OptionImpl(final T defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public Option<T> set(final Optional<T> value) {
		this.value = Objects.requireNonNull(value);
		return this;
	}

	@Override
	public Optional<T> getRaw() {
		return this.value;
	}

	@Override
	public T getDefault() {
		return this.defaultValue;
	}
}
