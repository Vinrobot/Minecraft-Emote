package net.vinrobot.mcemote.client.providers;

import net.vinrobot.mcemote.config.Configuration;

public interface IEmoteProvider extends Comparable<IEmoteProvider> {
	default int priority() {
		return 50;
	}

	void registerEmotes(Configuration config, IEmoteRegistry registry) throws Exception;

	@Override
	default int compareTo(IEmoteProvider other) {
		return Integer.compare(this.priority(), other.priority());
	}
}
