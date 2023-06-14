package net.vinrobot.seventv.client.text;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.vinrobot.seventv.client.font.EmoteFontStorage;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public class EmoteParser implements CharacterVisitor {
	public static final Style EMOTE_STYLE = Style.EMPTY.withFont(EmoteFontStorage.IDENTIFIER);

	private final CharacterVisitor visitor;
	private final EmotesManager emotesManager;
	private final List<InternalCharacter> characters = new LinkedList<>();

	public EmoteParser(CharacterVisitor visitor, EmotesManager emotesManager) {
		this.visitor = visitor;
		this.emotesManager = emotesManager;
	}

	public static OrderedText wrapOrderedText(OrderedText orderedText, EmotesManager emotesManager) {
		return (CharacterVisitor visitor) -> {
			final EmoteParser parser = new EmoteParser(visitor, emotesManager);
			boolean rt = orderedText.accept(parser);
			return rt && parser.flush();
		};
	}

	@Override
	public boolean accept(int index, Style style, int codePoint) {
		if (Character.isWhitespace(codePoint)) {
			return this.flush() && this.visitor.accept(index, style, codePoint);
		} else {
			this.characters.add(new InternalCharacter(index, style, codePoint));
			return true;
		}
	}

	public boolean flush() {
		if (this.characters.isEmpty()) {
			return true;
		}

		String word = this.characters.stream().map(c -> c.content()).collect(Collectors.joining());
		final Optional<EmoteCharacter> emote = this.emotesManager.getByName(word);
		return emote.map(this::handleEmote).orElseGet(this::handleText);
	}

	private boolean handleEmote(EmoteCharacter emoteCharacter) {
		this.characters.clear();

		return this.visitor.accept(0, EMOTE_STYLE, emoteCharacter.codePoint());
	}

	private boolean handleText() {
		Iterator<InternalCharacter> iterator = this.characters.iterator();
		while (iterator.hasNext()) {
			InternalCharacter character = iterator.next();
			iterator.remove();

			if (!this.visitor.accept(character.index, character.style, character.codePoint)) {
				return false;
			}
		}

		return true;
	}

	private record InternalCharacter(int index, Style style, int codePoint) {
		public String content() {
			return String.valueOf(Character.toChars(codePoint));
		}
	}
}
