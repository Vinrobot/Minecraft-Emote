package net.vinrobot.seventv.client.font.impl;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.vinrobot.seventv.api.seventv.Emote;
import net.vinrobot.seventv.api.seventv.EmoteData;
import net.vinrobot.seventv.api.seventv.EmoteFile;
import net.vinrobot.seventv.api.seventv.EmoteHost;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;

@Environment(EnvType.CLIENT)
public class SevenTVEmote implements net.vinrobot.seventv.client.font.Emote {
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
	public BufferedImage loadImage() throws IOException {
		final EmoteData data = this.emote.data();
		final EmoteHost host = data.host();
		final EmoteFile file = getFile();
		final String url = "https:" + host.url() + "/" + (data.animated() ? file.static_name() : file.name());
		return ImageIO.read(new URL(url));
	}
}
