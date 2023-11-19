package net.vinrobot.mcemote.client.providers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.vinrobot.mcemote.api.ffz.Emoticon;
import net.vinrobot.mcemote.client.font.Emote;
import net.vinrobot.mcemote.client.imageio.NativeFrame;
import net.vinrobot.mcemote.client.imageio.NativeImageIO;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class FFZEmote implements Emote {
	private final Emoticon emoticon;

	public FFZEmote(Emoticon emoticon) {
		this.emoticon = emoticon;
	}

	@Override
	public String getName() {
		return this.emoticon.name();
	}

	@Override
	public int getWidth() {
		return this.emoticon.width();
	}

	@Override
	public int getHeight() {
		return this.emoticon.height();
	}

	@Override
	public NativeFrame[] loadFrames() throws IOException {
		final Map<String, String> urls = this.emoticon.urls();
		final String url = urls.containsKey("1") ? urls.get("1") : urls.values().iterator().next();
		return NativeImageIO.readAll(URI.create(url));
	}
}
