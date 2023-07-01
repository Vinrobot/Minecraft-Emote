package net.vinrobot.mcemote.client;

import net.fabricmc.api.ClientModInitializer;
import net.vinrobot.mcemote.MinecraftEmoteMod;
import net.vinrobot.mcemote.api.FrankerFaceZService;
import net.vinrobot.mcemote.api.SevenTVService;
import net.vinrobot.mcemote.api.ffz.RoomResponse;
import net.vinrobot.mcemote.api.seventv.EmoteSet;
import net.vinrobot.mcemote.api.seventv.Platform;
import net.vinrobot.mcemote.api.seventv.UserResponse;
import net.vinrobot.mcemote.client.font.Emote;
import net.vinrobot.mcemote.client.font.impl.FFZEmote;
import net.vinrobot.mcemote.client.font.impl.SevenTVEmote;
import net.vinrobot.mcemote.client.text.EmotesManager;
import webpdecoderjn.WebPDecoder;

import java.io.IOException;

public class MinecraftEmoteModClient implements ClientModInitializer {
	public static final EmotesManager EMOTES_MANAGER = new EmotesManager();
	public static final String SCRAPIE_TWITCH_ID = "40646018";

	private static int addEmoteSet(EmotesManager manager, int baseCodePoint, EmoteSet emoteSet) {
		int codePoint = baseCodePoint;
		for (final Emote emote : emoteSet.emotes().stream().map(SevenTVEmote::new).toList()) {
			manager.addEmote(codePoint++, emote);
		}
		return codePoint;
	}

	private static int addEmoteSet(EmotesManager manager, int baseCodePoint, net.vinrobot.mcemote.api.ffz.EmoteSet emoteSet) {
		int codePoint = baseCodePoint;
		for (final Emote emote : emoteSet.emoticons().stream().map(FFZEmote::new).toList()) {
			manager.addEmote(codePoint++, emote);
		}
		return codePoint;
	}

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		try {
			WebPDecoder.init();
		} catch (IOException e) {
			MinecraftEmoteMod.LOGGER.error("Failed to initialize WebPDecoder", e);
		}

		try {
			{
				final SevenTVService api = new SevenTVService();

				final EmoteSet globalEmoteSet = api.fetchGlobalEmoteSet();
				addEmoteSet(EMOTES_MANAGER, 1, globalEmoteSet);
				MinecraftEmoteMod.LOGGER.info("Loaded " + globalEmoteSet.emotes().size() + " global emotes");

				final UserResponse user = api.fetchUser(Platform.TWITCH, SCRAPIE_TWITCH_ID);
				final EmoteSet userEmoteSet = user.emote_set();
				addEmoteSet(EMOTES_MANAGER, 100001, userEmoteSet);
				MinecraftEmoteMod.LOGGER.info("Loaded " + userEmoteSet.emotes().size() + " emotes from user " + user.user().username());
			}
			{
				final FrankerFaceZService api = new FrankerFaceZService();

				final RoomResponse room = api.fetchRoom(net.vinrobot.mcemote.api.ffz.Platform.TWITCH, SCRAPIE_TWITCH_ID);
				int codePoint = 200001;
				for (final net.vinrobot.mcemote.api.ffz.EmoteSet emoteSet : room.sets().values()) {
					codePoint = addEmoteSet(EMOTES_MANAGER, codePoint, emoteSet);
				}
				int count = room.sets().values().stream().map(e -> e.emoticons().size()).reduce(Integer::sum).orElse(0);
				MinecraftEmoteMod.LOGGER.info("Loaded " + count + " emotes from room " + room.room().id());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
