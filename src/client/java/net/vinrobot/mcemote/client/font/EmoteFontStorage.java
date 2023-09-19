package net.vinrobot.mcemote.client.font;

import net.minecraft.client.font.BuiltinEmptyGlyph;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.vinrobot.mcemote.client.helpers.FutureHelper;
import net.vinrobot.mcemote.client.imageio.NativeFrame;
import net.vinrobot.mcemote.client.text.EmotesManager;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EmoteFontStorage extends CustomFontStorage {
	public static final Identifier IDENTIFIER = new Identifier("mcemote.fonts", "emotes");
	public static final float GLYPH_HEIGHT = 8;

	private final EmotesManager emotesManager;
	private final Map<Integer, Future<AnimatedGlyph>> framesCache = new HashMap<>();
	private final Map<Glyph, GlyphRenderer> glyphRendererCache = new HashMap<>();
	private final ExecutorService executorService = new ThreadPoolExecutor(1, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

	public EmoteFontStorage(TextureManager textureManager, EmotesManager emotesManager) {
		super(textureManager, IDENTIFIER);
		this.emotesManager = emotesManager;
	}

	@Override
	public void close() {
		super.close();
		this.clearCache();
	}

	public void clearCache() {
		this.framesCache.clear();
		this.glyphRendererCache.clear();
	}

	@Override
	public Glyph getGlyph(final int codePoint, final boolean validateAdvance) {
		try {
			return FutureHelper.asOptional(this.framesCache.computeIfAbsent(codePoint, this::asyncLoadAnimatedGlyph))
				.map(m -> m.getGlyphAt(Instant.now())).orElse(BuiltinEmptyGlyph.MISSING);
		} catch (RuntimeException ex) {
			return BuiltinEmptyGlyph.MISSING;
		}
	}

	private Future<AnimatedGlyph> asyncLoadAnimatedGlyph(final int codePoint) {
		return this.executorService.submit(() -> this.loadAnimatedGlyph(codePoint));
	}

	private AnimatedGlyph loadAnimatedGlyph(final int codePoint) throws IOException {
		final Emote emote = this.emotesManager.getByCodePoint(codePoint).orElseThrow();

		final int width = emote.getWidth();
		final int height = emote.getHeight();
		final float advance = width * GLYPH_HEIGHT / height;
		final float oversample = height / GLYPH_HEIGHT;
		final NativeFrame[] frames = emote.loadFrames();

		final AnimatedGlyph.Frame[] animatedFrames = new AnimatedGlyph.Frame[frames.length];
		for (int i = 0; i < frames.length; ++i) {
			final NativeFrame frame = frames[i];
			final Glyph glyph = new NativeImageGlyph(frame.image(), advance, oversample);
			animatedFrames[i] = new AnimatedGlyph.Frame(glyph, frame.duration());
		}

		return new AnimatedGlyph(animatedFrames);
	}

	@Override
	public GlyphRenderer getGlyphRenderer(final int codePoint) {
		final Glyph glyph = this.getGlyph(codePoint, true);
		return this.glyphRendererCache.computeIfAbsent(glyph, g -> g.bake(this::getGlyphRenderer));
	}
}
