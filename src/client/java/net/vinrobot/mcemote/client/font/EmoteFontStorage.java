package net.vinrobot.mcemote.client.font;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.BuiltinEmptyGlyph;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.vinrobot.mcemote.MinecraftEmoteMod;
import net.vinrobot.mcemote.client.helpers.NativeImageHelper;
import net.vinrobot.mcemote.client.text.EmotesManager;

import java.awt.image.BufferedImage;

@Environment(EnvType.CLIENT)
public class EmoteFontStorage extends FontStorage {
	public static final Identifier IDENTIFIER = new Identifier("mcemote.fonts", "emotes");
	public static final float GLYPH_HEIGHT = 9;

	private final EmotesManager emotesManager;

	public EmoteFontStorage(TextureManager textureManager, EmotesManager emotesManager) {
		super(textureManager, IDENTIFIER);
		this.emotesManager = emotesManager;
	}

	@Override
	protected FontStorage.GlyphPair findGlyph(int codePoint) {
		return this.emotesManager.getByCodePoint(codePoint)
			.map(this::loadGlyph)
			.map(e -> new FontStorage.GlyphPair(e, e))
			.orElseGet(() -> super.findGlyph(codePoint));
	}

	protected Glyph loadGlyph(Emote emote) {
		try {
			final int width = emote.getWidth();
			final int height = emote.getHeight();
			final float advance = width * GLYPH_HEIGHT / height;
			final float oversample = height / GLYPH_HEIGHT;
			final BufferedImage bufferedImage = emote.loadImage();
			final NativeImage nativeImage = NativeImageHelper.fromBufferedImage(bufferedImage);
			return new NativeImageGlyph(nativeImage, advance, oversample);
		} catch (Exception ex) {
			MinecraftEmoteMod.LOGGER.error("Unable to load emote", ex);
			return BuiltinEmptyGlyph.MISSING;
		}
	}

	@Override
	protected GlyphRenderer findGlyphRenderer(int codePoint) {
		final Glyph glyph = this.getGlyph(codePoint, false);
		return glyph.bake(this::getGlyphRenderer);
	}
}
