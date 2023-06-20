package net.vinrobot.mcemote.client.providers;

import net.vinrobot.mcemote.api.FrankerFaceZService;
import net.vinrobot.mcemote.api.ffz.Platform;
import net.vinrobot.mcemote.client.font.impl.FFZEmote;

import java.io.IOException;

public class FFZRoomEmoteProvider implements IEmoteProvider {
	private final String userId;

	public FFZRoomEmoteProvider(String userId) {
		this.userId = userId;
	}

	@Override
	public void registerEmotes(IEmoteRegistry registry) throws IOException, InterruptedException {
		final FrankerFaceZService service = new FrankerFaceZService();

		service.fetchRoom(Platform.TWITCH, this.userId)
			.sets().values().stream()
			.flatMap(emoteSet -> emoteSet.emoticons().stream())
			.map(FFZEmote::new)
			.forEach(registry::registerEmote);
	}
}
