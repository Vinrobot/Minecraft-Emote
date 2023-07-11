package net.vinrobot.mcemote.client;

import net.fabricmc.api.ClientModInitializer;
import net.vinrobot.mcemote.MinecraftEmoteMod;
import net.vinrobot.mcemote.client.font.Emote;
import net.vinrobot.mcemote.client.providers.FFZRoomEmoteProvider;
import net.vinrobot.mcemote.client.providers.IEmoteProvider;
import net.vinrobot.mcemote.client.providers.STVGlobalEmoteProvider;
import net.vinrobot.mcemote.client.providers.STVUserEmoteProvider;
import net.vinrobot.mcemote.client.text.EmotesManager;
import webpdecoderjn.WebPLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MinecraftEmoteModClient implements ClientModInitializer {
	public static final EmotesManager EMOTES_MANAGER = new EmotesManager();
	public static final String SCRAPIE_TWITCH_ID = "40646018";

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		try {
			WebPLoader.init();
		} catch (IOException e) {
			MinecraftEmoteMod.LOGGER.error("Failed to initialize WebPDecoder", e);
		}

		final IEmoteProvider[] providers = new IEmoteProvider[]{
			new STVGlobalEmoteProvider(),
			new STVUserEmoteProvider(SCRAPIE_TWITCH_ID),
			new FFZRoomEmoteProvider(SCRAPIE_TWITCH_ID),
		};

		int codePoint = 100;

		for (final IEmoteProvider provider : providers) {
			final String providerName = provider.getClass().getName();

			try {
				// "Register" emotes in a temporary list
				// If an exception is thrown, the emotes will not be added to the manager
				final List<Emote> emotes = new ArrayList<>();
				provider.registerEmotes(emotes::add);

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
