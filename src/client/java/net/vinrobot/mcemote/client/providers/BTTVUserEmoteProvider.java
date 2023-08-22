package net.vinrobot.mcemote.client.providers;

import net.vinrobot.mcemote.api.bttv.BetterTTVService;
import net.vinrobot.mcemote.api.bttv.Provider;
import net.vinrobot.mcemote.api.bttv.UserResponse;
import net.vinrobot.mcemote.config.Configuration;

import java.util.Arrays;
import java.util.stream.Stream;

public class BTTVUserEmoteProvider implements IEmoteProvider {
	@Override
	public void registerEmotes(final Configuration config, final IEmoteRegistry registry) throws Exception {
		final String twitchId = config.twitchId().get();
		if (twitchId.isEmpty()) {
			return;
		}

		final BetterTTVService service = new BetterTTVService();

		final UserResponse emoteSets = service.fetchUserEmoteSet(Provider.TWITCH, twitchId);

		Stream.concat(Arrays.stream(emoteSets.channelEmotes()), Arrays.stream(emoteSets.sharedEmotes()))
			.map(BTTVEmote::new)
			.forEach(registry::registerEmote);
	}
}
