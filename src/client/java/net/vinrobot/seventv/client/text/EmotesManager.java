package net.vinrobot.seventv.client.text;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class EmotesManager {
	private final Map<String, EmoteCharacter> byNames = new HashMap<>();
	private final Map<Integer, EmoteCharacter> byCodePoints = new HashMap<>();

	public EmotesManager() {
		int codePoint = 0;

		this.addEmote(new EmoteCharacter("Chatting", ++codePoint, "https://cdn.7tv.app/emote/60ef410f48cde2fcc3eb5caa/1x_static.webp", 32, 32));
	}

	public void addEmote(EmoteCharacter emoteCharacter) {
		this.byNames.putIfAbsent(emoteCharacter.name(), emoteCharacter);
		this.byCodePoints.putIfAbsent(emoteCharacter.codePoint(), emoteCharacter);
	}

	public Optional<EmoteCharacter> getByName(String name) {
		return Optional.ofNullable(this.byNames.get(name));
	}

	public Optional<EmoteCharacter> getByCodepoint(int codePoint) {
		return Optional.ofNullable(this.byCodePoints.get(codePoint));
	}
}
