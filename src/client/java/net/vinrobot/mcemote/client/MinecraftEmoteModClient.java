package net.vinrobot.mcemote.client;

import net.fabricmc.api.ClientModInitializer;
import net.vinrobot.mcemote.MinecraftEmoteMod;
import net.vinrobot.mcemote.client.font.Emote;
import net.vinrobot.mcemote.client.providers.FFZRoomEmoteProvider;
import net.vinrobot.mcemote.client.providers.IEmoteProvider;
import net.vinrobot.mcemote.client.providers.STVGlobalEmoteProvider;
import net.vinrobot.mcemote.client.providers.STVUserEmoteProvider;
import net.vinrobot.mcemote.client.text.EmotesManager;
import net.vinrobot.mcemote.config.Configuration;
import net.vinrobot.mcemote.config.impl.ConfigurationImpl;
import webpdecoderjn.WebPLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MinecraftEmoteModClient implements ClientModInitializer {
	public static final EmotesManager EMOTES_MANAGER = new EmotesManager();

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		try {
			WebPLoader.init();
		} catch (IOException e) {
			MinecraftEmoteMod.LOGGER.error("Failed to initialize WebPDecoder", e);
		}

		final Configuration config = new ConfigurationImpl();
		final IEmoteProvider[] providers = new IEmoteProvider[]{
			new STVGlobalEmoteProvider(),
			new STVUserEmoteProvider(),
			new FFZRoomEmoteProvider(),
		};

		int codePoint = 100;

		for (final IEmoteProvider provider : providers) {
			final String providerName = provider.getClass().getName();

			try {
				// "Register" emotes in a temporary list
				// If an exception is thrown, the emotes will not be added to the manager
				final List<Emote> emotes = new ArrayList<>();
				provider.registerEmotes(config, emotes::add);

				for (final Emote emote : emotes) {
					EMOTES_MANAGER.addEmote(codePoint++, emote);
				}

				MinecraftEmoteMod.LOGGER.info("Registered " + emotes.size() + " emotes from provider " + providerName);
			} catch (Exception e) {
				MinecraftEmoteMod.LOGGER.error("Failed to register emotes from provider " + providerName, e);
			}
		}
	}
}
