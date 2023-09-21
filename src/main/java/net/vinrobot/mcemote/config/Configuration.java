package net.vinrobot.mcemote.config;

import net.vinrobot.mcemote.config.options.Option;
import net.vinrobot.mcemote.config.options.TwitchIdOption;

public class Configuration {
	private static final String SCRAPIE_TWITCH_ID = "40646018";

	private final Option<String> twitchId = new TwitchIdOption(SCRAPIE_TWITCH_ID);

	public Option<String> twitchId() {
		return this.twitchId;
	}
}
