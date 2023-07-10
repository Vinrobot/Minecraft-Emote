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

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class EmoteFontStorage extends FontStorage {
	public static final Identifier IDENTIFIER = new Identifier("mcemote.fonts", "emotes");
	public static final float GLYPH_HEIGHT = 9;

	private final EmotesManager emotesManager;
	private final Map<Integer, Frames> framesCache = new HashMap<>();
	private final Map<Glyph, GlyphRenderer> glyphRendererCache = new HashMap<>();

	public EmoteFontStorage(TextureManager textureManager, EmotesManager emotesManager) {
		super(textureManager, IDENTIFIER);
		this.emotesManager = emotesManager;
	}

	@Override
	public Glyph getGlyph(int codePoint, boolean validateAdvance) {
		try {
			return this.framesCache.computeIfAbsent(codePoint, this::loadAnimationManager)
				.getFrameAt(Duration.ofMillis(System.currentTimeMillis()))
				.image();
		} catch (RuntimeException ex) {
			return BuiltinEmptyGlyph.MISSING;
		}
	}

	private Frames loadAnimationManager(Integer integer) {
		try {
			final Emote emote = this.emotesManager.getByCodePoint(integer).orElseThrow();

			final int width = emote.getWidth();
			final int height = emote.getHeight();
			final float advance = width * GLYPH_HEIGHT / height;
			final float oversample = height / GLYPH_HEIGHT;
			final Emote.Frame[] frames = emote.loadFrames();

			final Frames.Frame[] animatedFrames = new Frames.Frame[frames.length];
			for (int i = 0; i < frames.length; i++) {
				final Emote.Frame frame = frames[i];
				final Glyph glyph = new NativeImageGlyph(frame.image(), advance, oversample);
				animatedFrames[i] = new Frames.Frame(glyph, frame.duration());
			}

			return new Frames(animatedFrames);
		} catch (Exception ex) {
			MinecraftEmoteMod.LOGGER.error("Unable to load emote", ex);
			throw new RuntimeException(ex);
		}
	}

	@Override
	public GlyphRenderer getGlyphRenderer(int codePoint) {
		final Glyph glyph = this.getGlyph(codePoint, false);
		return this.glyphRendererCache.computeIfAbsent(glyph, (g) -> g.bake(this::getGlyphRenderer));
	}
}
