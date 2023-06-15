package net.vinrobot.seventv.client.font;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.vinrobot.seventv.client.text.EmotesManager;

@Environment(EnvType.CLIENT)
public class EmoteFontStorage extends FontStorage {
	public static final Identifier IDENTIFIER = new Identifier("seventv.fonts", "emotes");

	private final EmotesManager emotesManager;

	public EmoteFontStorage(TextureManager textureManager, EmotesManager emotesManager) {
		super(textureManager, IDENTIFIER);
		this.emotesManager = emotesManager;
	}

	@Override
	protected FontStorage.GlyphPair findGlyph(int codePoint) {
		return this.emotesManager.getByCodePoint(codePoint)
			.map(EmoteGlyph::new)
			.map(e -> new FontStorage.GlyphPair(e, e))
			.orElseGet(() -> super.findGlyph(codePoint));
	}

	@Override
	protected GlyphRenderer findGlyphRenderer(int codePoint) {
		final Glyph glyph = this.getGlyph(codePoint, false);
		return glyph.bake(this::getGlyphRenderer);
	}
}
