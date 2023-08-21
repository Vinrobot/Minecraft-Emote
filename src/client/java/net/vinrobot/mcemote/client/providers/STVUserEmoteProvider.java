package net.vinrobot.mcemote.client.providers;

import net.vinrobot.mcemote.api.SevenTVService;
import net.vinrobot.mcemote.api.seventv.Platform;
import net.vinrobot.mcemote.config.Configuration;

import java.io.IOException;

public class STVUserEmoteProvider implements IEmoteProvider {
	@Override
	public void registerEmotes(Configuration config, IEmoteRegistry registry) throws IOException, InterruptedException {
		final String twitchId = config.twitchId().get();
		if (twitchId.isEmpty()) {
			return;
		}

		final SevenTVService service = new SevenTVService();

		service.fetchUser(Platform.TWITCH, twitchId)
			.emote_set().emotes().stream()
			.map(SevenTVEmote::new)
			.forEach(registry::registerEmote);
	}
}
