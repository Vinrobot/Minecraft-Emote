package net.vinrobot.seventv.client.text;

import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.GlyphContainer;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.vinrobot.seventv.client.SevenTVModClient;

public class EmoteFontStorage extends FontStorage {
	public static final Identifier IDENTIFIER = new Identifier("seventv.fonts", "emotes");

	private final GlyphContainer<Glyph> glyphCache = new GlyphContainer(i -> new Glyph[i], (rowCount) -> new Glyph[rowCount][]);
	private final GlyphContainer<GlyphRenderer> glyphRendererCache = new GlyphContainer(i -> new GlyphRenderer[i], (rowCount) -> new GlyphRenderer[rowCount][]);

	public EmoteFontStorage(TextureManager textureManager) {
		super(textureManager, IDENTIFIER);
	}

	@Override
	public Glyph getGlyph(int codePoint, boolean validateAdvance) {
		return this.glyphCache.computeIfAbsent(codePoint, c -> this.loadGlyph(c, validateAdvance));
	}

	public Glyph loadGlyph(int codePoint, boolean validateAdvance) {
		return SevenTVModClient.EMOTES_MANAGER.getByCodepoint(codePoint)
			.<Glyph>map(e -> new EmoteGlyph(e))
			.orElseGet(() -> super.getGlyph(codePoint, validateAdvance));
	}

	@Override
	public GlyphRenderer getGlyphRenderer(int codePoint) {
		return this.glyphRendererCache.computeIfAbsent(codePoint, this::loadGlyphRenderer);
	}

	public GlyphRenderer loadGlyphRenderer(int codePoint) {
		final Glyph glyph = this.getGlyph(codePoint, false);
		return glyph.bake(this::getGlyphRenderer);
	}
}
