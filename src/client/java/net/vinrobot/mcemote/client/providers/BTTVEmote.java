package net.vinrobot.mcemote.client.providers;

import net.minecraft.client.texture.NativeImage;
import net.vinrobot.mcemote.api.bttv.Emote;
import net.vinrobot.mcemote.client.helpers.NativeImageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

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
	public Frame[] loadFrames() throws IOException {
		if (this.emote.animated()) {
			LOGGER.error("Animated BTTV emotes are not supported yet.");
		}

		final URL url = new URL("https://cdn.betterttv.net/emote/" + this.emote.id() + "/1x");
		final BufferedImage image = Objects.requireNonNull(ImageIO.read(url));

		final int expectedWidth = getWidth(), expectedHeight = getHeight();
		final int actualWidth = image.getWidth(), actualHeight = image.getHeight();
		if (expectedWidth != actualWidth || expectedHeight != actualHeight) {
			final String expectedSize = expectedWidth + "x" + expectedHeight;
			final String actualSize = actualWidth + "x" + actualHeight;
			LOGGER.error("BTTV emote " + getName() + " has unexpected size " + actualSize + " (expected " + expectedSize + ").");
		}

		final NativeImage nativeImage = NativeImageHelper.fromBufferedImage(image);
		return new Frame[]{new Frame(nativeImage)};
	}
}
