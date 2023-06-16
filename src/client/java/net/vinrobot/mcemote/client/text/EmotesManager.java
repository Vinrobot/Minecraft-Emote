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

	public EmotePair addEmote(int codePoint, Emote emote) {
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

	public Optional<EmotePair> getByName(String name) {
		return Optional.ofNullable(this.emoteByNames.get(name));
	}

	public Optional<Emote> getByCodePoint(int codePoint) {
		return Optional.ofNullable(this.emoteByCodePoints.get(codePoint)).map(EmotePair::emote);
	}

	public record EmotePair(int codePoint, Emote emote) {
	}
}
