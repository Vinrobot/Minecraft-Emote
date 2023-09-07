package net.vinrobot.mcemote.client.imageio;

import net.minecraft.client.texture.NativeImage;

import java.time.Duration;

public record NativeFrame(
	NativeImage image,
	Duration duration
) {
}
