package net.vinrobot.mcemote.client;

import net.fabricmc.api.ClientModInitializer;
import net.vinrobot.mcemote.MinecraftEmote;
import net.vinrobot.mcemote.MinecraftEmoteMod;
import net.vinrobot.mcemote.client.font.Emote;
import net.vinrobot.mcemote.client.helpers.ListHelper;
import net.vinrobot.mcemote.client.providers.IEmoteProvider;
import net.vinrobot.mcemote.client.text.EmotesManager;
import net.vinrobot.mcemote.config.Configuration;
import net.vinrobot.mcemote.config.ConfigurationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class MinecraftEmoteModClient implements ClientModInitializer {
	public static final EmotesManager EMOTES_MANAGER = new EmotesManager();

	@Override
	public void onInitializeClient() {
		final ConfigurationManager configManager = MinecraftEmote.getInstance().getConfigManager();

		configManager.onChange((config) -> {
			EMOTES_MANAGER.clearEmotes();
			this.registerEmotes(EMOTES_MANAGER, config);
		});

		// Load config at startup
		configManager.getConfig();
	}

	private void registerEmotes(final EmotesManager manager, final Configuration config) {
		final ServiceLoader<IEmoteProvider> serviceLoader = ServiceLoader.load(IEmoteProvider.class);
		final List<IEmoteProvider> providers = ListHelper.sort(serviceLoader);

		for (final IEmoteProvider provider : providers) {
			final String providerName = provider.getClass().getName();

			try {
				// "Register" emotes in a temporary list
				// If an exception is thrown, the emotes will not be added to the manager
				final List<Emote> emotes = new ArrayList<>();
				provider.registerEmotes(config, emotes::add);

				for (final Emote emote : emotes) {
					manager.addEmote(emote);
				}

				MinecraftEmoteMod.LOGGER.info("Registered " + emotes.size() + " emotes from provider " + providerName);
			} catch (Exception e) {
				MinecraftEmoteMod.LOGGER.error("Failed to register emotes from provider " + providerName, e);
			}
		}
	}
}
