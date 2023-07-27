package net.vinrobot.mcemote.config.impl;

import net.vinrobot.mcemote.config.Configuration;
import net.vinrobot.mcemote.config.Option;

public final class ConfigurationImpl implements Configuration {
	private static final String SCRAPIE_TWITCH_ID = "40646018";

	private final Option<String> twitchId = Option.of(SCRAPIE_TWITCH_ID);

	@Override
	public Option<String> twitchId() {
		return this.twitchId;
	}
}
