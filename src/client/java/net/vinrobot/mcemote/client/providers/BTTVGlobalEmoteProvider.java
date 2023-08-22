package net.vinrobot.mcemote.client.providers;

import net.vinrobot.mcemote.api.bttv.BetterTTVService;
import net.vinrobot.mcemote.api.bttv.Emote;
import net.vinrobot.mcemote.config.Configuration;

import java.util.Arrays;

public class BTTVGlobalEmoteProvider implements IEmoteProvider {
	@Override
	public int priority() {
		return 10;
	}

	@Override
	public void registerEmotes(final Configuration config, final IEmoteRegistry registry) throws Exception {
		final BetterTTVService service = new BetterTTVService();

		final Emote[] emoteSets = service.fetchGlobalEmoteSet();

		Arrays.stream(emoteSets)
			.map(BTTVEmote::new)
			.forEach(registry::registerEmote);
	}
}
