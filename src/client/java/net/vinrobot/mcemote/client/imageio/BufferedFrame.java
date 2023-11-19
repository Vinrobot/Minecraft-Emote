package net.vinrobot.mcemote.client.imageio;

import java.awt.image.BufferedImage;
import java.time.Duration;
import java.util.Objects;

public record BufferedFrame(
	BufferedImage image,
	Duration duration
) {
	public BufferedFrame {
		Objects.requireNonNull(image);
		Objects.requireNonNull(duration);
	}

	public NativeFrame toNativeFrame() {
		return new NativeFrame(NativeImageHelper.fromBufferedImage(image), duration);
	}
}
