package net.vinrobot.mcemote.client.font;

import net.minecraft.client.font.BuiltinEmptyGlyph;
import net.minecraft.client.font.Font;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;

import java.util.List;

public abstract class CustomFontStorage extends FontStorage {
	public static final float GLYPH_HEIGHT = TextRenderer.ARABIC_SHAPING_LETTERS_SHAPE;

	private final Identifier identifier;
	private final TextureManager textureManager;
	private final GlyphRenderer blankGlyphRenderer;

	public CustomFontStorage(final TextureManager textureManager, final Identifier id) {
		super(textureManager, id);
		this.identifier = id;
		this.textureManager = textureManager;

		// blankGlyphRenderer is private in FontStorage, but we can get it from the getObfuscatedGlyphRenderer
		// because if it doesn't find the glyph, it returns the blankGlyphRenderer.
		this.blankGlyphRenderer = super.getObfuscatedGlyphRenderer(BuiltinEmptyGlyph.MISSING);
	}

	public final Identifier getIdentifier() {
		return this.identifier;
	}

	protected TextureManager getTextureManager() {
		return this.textureManager;
	}

	@Override
	public void setFonts(final List<Font> fonts) {
		throw new UnsupportedOperationException();
	}

	@Override
	public abstract Glyph getGlyph(int codePoint, boolean validateAdvance);

	@Override
	public abstract GlyphRenderer getGlyphRenderer(int codePoint);

	@Override
	public GlyphRenderer getObfuscatedGlyphRenderer(final Glyph glyph) {
		// Don't obfuscate the glyph by default, children can override this.
		return this.blankGlyphRenderer;
	}

	public GlyphRenderer getBlankGlyphRenderer() {
		return this.blankGlyphRenderer;
	}
}
