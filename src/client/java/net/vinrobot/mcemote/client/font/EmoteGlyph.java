package net.vinrobot.mcemote.client.font;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.BuiltinEmptyGlyph;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.font.RenderableGlyph;
import net.vinrobot.mcemote.MinecraftEmoteMod;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class EmoteGlyph implements Glyph {
	private final Emote emote;

	public EmoteGlyph(Emote emote) {
		this.emote = emote;
	}

	@Override
	public float getAdvance() {
		final int width = this.emote.getWidth();
		final int height = this.emote.getHeight();
		return width * 9f / height;
	}

	@Override
	public GlyphRenderer bake(Function<RenderableGlyph, GlyphRenderer> glyphRendererGetter) {
		final RenderableGlyph glyphRenderer;
		try {
			final BufferedImage image = this.emote.loadImage();
			glyphRenderer = new EmoteRenderableGlyph(image);
		} catch (IOException e) {
			MinecraftEmoteMod.LOGGER.debug("Unable to load emote", e);
			return BuiltinEmptyGlyph.MISSING.bake(glyphRendererGetter);
		}
		return glyphRendererGetter.apply(glyphRenderer);
	}
}
