package net.vinrobot.mcemote.client.providers;

import net.vinrobot.mcemote.api.FrankerFaceZService;
import net.vinrobot.mcemote.api.ffz.GlobalEmoteSets;
import net.vinrobot.mcemote.config.Configuration;

import java.util.Arrays;

public class FFZGlobalEmoteProvider implements IEmoteProvider {
	@Override
	public int priority() {
		return 10;
	}

	@Override
	public void registerEmotes(final Configuration config, final IEmoteRegistry registry) throws Exception {
		final FrankerFaceZService service = new FrankerFaceZService();

		final GlobalEmoteSets emoteSets = service.fetchGlobalEmoteSet();

		Arrays.stream(emoteSets.default_sets())
			.mapToObj(Integer::toString)
			.map(emoteSets.sets()::get)
			.flatMap((emoteSet) -> emoteSet.emoticons().stream())
			.map(FFZEmote::new)
			.forEach(registry::registerEmote);
	}
}
