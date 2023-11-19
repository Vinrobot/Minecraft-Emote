package net.vinrobot.mcemote.client.providers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.vinrobot.mcemote.api.seventv.Emote;
import net.vinrobot.mcemote.api.seventv.EmoteData;
import net.vinrobot.mcemote.api.seventv.EmoteFile;
import net.vinrobot.mcemote.api.seventv.EmoteHost;
import net.vinrobot.mcemote.client.imageio.NativeFrame;
import net.vinrobot.mcemote.client.imageio.NativeImageIO;

import java.io.IOException;
import java.net.URI;
import java.util.Comparator;

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
			.findFirst().orElseThrow();
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
	public NativeFrame[] loadFrames() throws IOException {
		final EmoteData data = this.emote.data();
		final EmoteHost host = data.host();
		final EmoteFile file = getFile();
		final String url = "https:" + host.url() + "/" + file.name();
		return NativeImageIO.readAll(URI.create(url));
	}
}
