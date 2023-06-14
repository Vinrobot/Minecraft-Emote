package net.vinrobot.seventv.client.font;

import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.vinrobot.seventv.client.SevenTVModClient;

public class EmoteFontStorage extends FontStorage {
	public static final Identifier IDENTIFIER = new Identifier("seventv.fonts", "emotes");

	public EmoteFontStorage(TextureManager textureManager) {
		super(textureManager, IDENTIFIER);
	}

	@Override
	protected FontStorage.GlyphPair findGlyph(int codePoint) {
		return SevenTVModClient.EMOTES_MANAGER.getByCodepoint(codePoint)
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
