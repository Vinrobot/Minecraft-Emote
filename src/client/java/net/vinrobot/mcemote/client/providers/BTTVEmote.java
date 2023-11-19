package net.vinrobot.mcemote.client.providers;

import net.vinrobot.mcemote.api.bttv.Emote;
import net.vinrobot.mcemote.client.imageio.NativeFrame;
import net.vinrobot.mcemote.client.imageio.NativeImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

public class BTTVEmote implements net.vinrobot.mcemote.client.font.Emote {
	private static final Logger LOGGER = LoggerFactory.getLogger(BTTVEmote.class);
	private static final int DEFAULT_SIZE = 28;

	private final Emote emote;

	public BTTVEmote(Emote emote) {
		this.emote = emote;
	}

	@Override
	public String getName() {
		return this.emote.code();
	}

	@Override
	public int getWidth() {
		final int width = this.emote.width();
		return width > 0 ? width : DEFAULT_SIZE;
	}

	@Override
	public int getHeight() {
		final int height = this.emote.height();
		return height > 0 ? height : DEFAULT_SIZE;
	}

	@Override
	public NativeFrame[] loadFrames() throws IOException {
		return NativeImageIO.readAll(URI.create("https://cdn.betterttv.net/emote/" + this.emote.id() + "/1x"));
	}
}
