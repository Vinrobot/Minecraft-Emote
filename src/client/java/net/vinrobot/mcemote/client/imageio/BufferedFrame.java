package net.vinrobot.mcemote.client.imageio;

import java.awt.image.BufferedImage;
import java.time.Duration;

public record BufferedFrame(
	BufferedImage image,
	Duration duration
) {
	NativeFrame toNativeFrame() {
		return new NativeFrame(NativeImageHelper.fromBufferedImage(image), duration);
	}
}
