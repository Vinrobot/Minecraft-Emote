package net.vinrobot.seventv.client;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import net.fabricmc.api.ClientModInitializer;
import net.vinrobot.seventv.client.text.EmotesManager;

public class SevenTVModClient implements ClientModInitializer {
	public static final EmotesManager EMOTES_MANAGER = new EmotesManager();

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		MixinExtrasBootstrap.init();
	}
}
