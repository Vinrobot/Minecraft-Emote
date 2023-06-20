package net.vinrobot.mcemote.client.providers;

import net.vinrobot.mcemote.api.SevenTVService;
import net.vinrobot.mcemote.api.seventv.Platform;
import net.vinrobot.mcemote.client.font.impl.SevenTVEmote;

import java.io.IOException;

public class STVUserEmoteProvider implements IEmoteProvider {
	private final String userId;

	public STVUserEmoteProvider(String userId) {
		this.userId = userId;
	}

	@Override
	public void registerEmotes(IEmoteRegistry registry) throws IOException, InterruptedException {
		final SevenTVService service = new SevenTVService();

		service.fetchUser(Platform.TWITCH, this.userId)
			.emote_set().emotes().stream()
			.map(SevenTVEmote::new)
			.forEach(registry::registerEmote);
	}
}
