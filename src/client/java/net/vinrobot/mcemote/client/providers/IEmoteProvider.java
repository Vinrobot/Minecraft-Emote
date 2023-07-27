package net.vinrobot.mcemote.client.providers;

import net.vinrobot.mcemote.config.Configuration;

public interface IEmoteProvider {
	void registerEmotes(Configuration config, IEmoteRegistry registry) throws Exception;
}
