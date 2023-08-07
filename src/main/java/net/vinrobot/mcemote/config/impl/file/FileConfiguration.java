package net.vinrobot.mcemote.config.impl.file;

import net.vinrobot.mcemote.config.Configuration;

import java.util.Optional;

class FileConfiguration {
	public String twitchId;

	public void copyFrom(final Configuration domain) {
		this.twitchId = domain.twitchId().get();
	}

	public void copyTo(final Configuration configuration) {
		configuration.twitchId().set(Optional.ofNullable(this.twitchId));
	}
}
