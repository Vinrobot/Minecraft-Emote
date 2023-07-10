package net.vinrobot.mcemote.client.font;

import net.minecraft.client.texture.NativeImage;

import java.io.IOException;
import java.time.Duration;

public interface Emote {
	String getName();

	int getWidth();

	int getHeight();

	Frame[] loadFrames() throws IOException;

	record Frame(NativeImage image, Duration duration) {
		public Frame(NativeImage image) {
			this(image, Duration.ofDays(1));
		}
	}
}
