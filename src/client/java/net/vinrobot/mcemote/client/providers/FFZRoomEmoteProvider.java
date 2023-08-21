package net.vinrobot.mcemote.client.providers;

import net.vinrobot.mcemote.api.FrankerFaceZService;
import net.vinrobot.mcemote.api.ffz.Platform;
import net.vinrobot.mcemote.config.Configuration;

import java.io.IOException;

public class FFZRoomEmoteProvider implements IEmoteProvider {
	@Override
	public void registerEmotes(Configuration config, IEmoteRegistry registry) throws IOException, InterruptedException {
		final String twitchId = config.twitchId().get();
		if (twitchId.isEmpty()) {
			return;
		}

		final FrankerFaceZService service = new FrankerFaceZService();

		service.fetchRoom(Platform.TWITCH, twitchId)
			.sets().values().stream()
			.flatMap(emoteSet -> emoteSet.emoticons().stream())
			.map(FFZEmote::new)
			.forEach(registry::registerEmote);
	}
}
