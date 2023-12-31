package net.vinrobot.mcemote.client.text;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.vinrobot.mcemote.client.font.Emote;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class EmotesManager {
	private final Map<String, EmotePair> emoteByNames = new HashMap<>();
	private final Map<Integer, EmotePair> emoteByCodePoints = new HashMap<>();

	private int nextCodePoint = 1;

	public EmotePair addEmote(Emote emote) {
		final int codePoint = this.nextCodePoint++;
		final String name = emote.getName();
		final EmotePair emotePair = new EmotePair(codePoint, emote);

		this.emoteByNames.put(name, emotePair);
		this.emoteByCodePoints.put(codePoint, emotePair);
		return emotePair;
	}

	public Optional<EmotePair> removeEmote(Emote emote) {
		return this.removeEmote(emote.getName());
	}

	public Optional<EmotePair> removeEmote(String name) {
		return Optional.ofNullable(this.emoteByNames.remove(name))
			.map(EmotePair::codePoint)
			.map(this.emoteByCodePoints::remove);
	}

	public Optional<EmotePair> removeEmote(int codePoint) {
		return Optional.ofNullable(this.emoteByCodePoints.remove(codePoint))
			.map(EmotePair::emote)
			.map(Emote::getName)
			.map(this.emoteByNames::remove);
	}

	public void clearEmotes() {
		// Don't reset nextCodePoint, so that new emotes will have
		// unique code points during the entire life of this object.
		this.emoteByNames.clear();
		this.emoteByCodePoints.clear();
	}

	public Optional<EmotePair> getByName(String name) {
		return Optional.ofNullable(this.emoteByNames.get(name));
	}

	public Optional<Emote> getByCodePoint(int codePoint) {
		return Optional.ofNullable(this.emoteByCodePoints.get(codePoint)).map(EmotePair::emote);
	}

	public record EmotePair(int codePoint, Emote emote) {
	}
}
