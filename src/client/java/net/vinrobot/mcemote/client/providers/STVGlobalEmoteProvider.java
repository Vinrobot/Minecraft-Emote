package net.vinrobot.mcemote.client.providers;

import net.vinrobot.mcemote.api.SevenTVService;
import net.vinrobot.mcemote.client.font.impl.SevenTVEmote;
import net.vinrobot.mcemote.config.Configuration;

import java.io.IOException;

public class STVGlobalEmoteProvider implements IEmoteProvider {
	@Override
	public int priority() {
		return 10;
	}

	@Override
	public void registerEmotes(Configuration config, IEmoteRegistry registry) throws IOException, InterruptedException {
		final SevenTVService service = new SevenTVService();

		service.fetchGlobalEmoteSet()
			.emotes().stream()
			.map(SevenTVEmote::new)
			.forEach(registry::registerEmote);
	}
}
