package net.vinrobot.mcemote.client.font.impl;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.NativeImage;
import net.vinrobot.mcemote.api.seventv.Emote;
import net.vinrobot.mcemote.api.seventv.EmoteData;
import net.vinrobot.mcemote.api.seventv.EmoteFile;
import net.vinrobot.mcemote.api.seventv.EmoteHost;
import net.vinrobot.mcemote.client.helpers.NativeImageHelper;
import webpdecoderjn.WebPDecoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Comparator;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class SevenTVEmote implements net.vinrobot.mcemote.client.font.Emote {
	private final Emote emote;

	public SevenTVEmote(Emote emote) {
		this.emote = emote;
	}

	private EmoteFile getFile() {
		return this.emote.data().host().files().stream()
			.filter(f -> "WEBP".equalsIgnoreCase(f.format()))
			.sorted(Comparator.comparingInt(EmoteFile::size))
			.findFirst().get();
	}

	@Override
	public String getName() {
		return this.emote.name();
	}

	@Override
	public int getWidth() {
		final EmoteFile file = getFile();
		return file.width();
	}

	@Override
	public int getHeight() {
		final EmoteFile file = getFile();
		return file.height();
	}

	@Override
	public Frame[] loadFrames() throws IOException {
		final EmoteData data = this.emote.data();
		final EmoteHost host = data.host();
		final EmoteFile file = getFile();
		final String url = "https:" + host.url() + "/" + file.name();
		if (data.animated()) {
			final byte[] bytes = WebPDecoder.getBytesFromURL(new URL(url));
			final WebPDecoder.WebPImage image = WebPDecoder.decode(bytes);

			final int frameCount = image.frames.size();
			final Frame[] frames = new Frame[frameCount];
			for (int i = 0; i < frameCount; ++i) {
				final WebPDecoder.WebPImageFrame frame = image.frames.get(i);
				final NativeImage nativeImage = NativeImageHelper.fromBufferedImage(frame.img);
				frames[i] = new Frame(nativeImage, Duration.ofMillis(frame.delay));
			}
			return frames;
		} else {
			final BufferedImage image = Objects.requireNonNull(ImageIO.read(new URL(url)));
			final NativeImage nativeImage = NativeImageHelper.fromBufferedImage(image);
			return new Frame[]{new Frame(nativeImage)};
		}
	}
}
