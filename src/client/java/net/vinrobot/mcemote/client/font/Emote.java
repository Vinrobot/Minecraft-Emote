package net.vinrobot.mcemote.client.font;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Duration;

public interface Emote {
	String getName();

	int getWidth();

	int getHeight();

	Frame[] loadFrames() throws IOException;

	record Frame(BufferedImage image, Duration duration) {
		public Frame(BufferedImage image) {
			this(image, Duration.ofDays(1));
		}
	}
}
