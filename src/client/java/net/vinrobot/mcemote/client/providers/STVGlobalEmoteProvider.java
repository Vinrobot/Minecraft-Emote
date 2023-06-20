package net.vinrobot.mcemote.client.providers;

import net.vinrobot.mcemote.api.SevenTVService;
import net.vinrobot.mcemote.client.font.impl.SevenTVEmote;

import java.io.IOException;

public class STVGlobalEmoteProvider implements IEmoteProvider {
	@Override
	public void registerEmotes(IEmoteRegistry registry) throws IOException, InterruptedException {
		final SevenTVService service = new SevenTVService();

		service.fetchGlobalEmoteSet()
			.emotes().stream()
			.map(SevenTVEmote::new)
			.forEach(registry::registerEmote);
	}
}
