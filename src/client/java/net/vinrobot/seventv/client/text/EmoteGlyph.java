package net.vinrobot.seventv.client.text;

import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.font.RenderableGlyph;
import net.minecraft.client.texture.NativeImage;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.function.Function;

public class EmoteGlyph implements RenderableGlyph, Glyph {
	private final EmoteCharacter emoteCharacter;
	private BufferedImage image;

	public EmoteGlyph(EmoteCharacter emoteCharacter) {
		this.emoteCharacter = emoteCharacter;
	}

	@Override
	public int getWidth() {
		return this.emoteCharacter.width();
	}

	@Override
	public int getHeight() {
		return this.emoteCharacter.height();
	}

	public BufferedImage getImage() {
		if (image != null) {
			return image;
		} else {
			return image = this.emoteCharacter.loadImage();
		}
	}

	@Override
	public void upload(int x, int y) {
		final int height = getHeight();
		final int width = getWidth();

		try (final NativeImage nativeImage = new NativeImage(NativeImage.Format.RGBA, width, height, false)) {
			final BufferedImage fontImage = this.getImage();
			final Raster rast = fontImage.getTile(0, 0);

			// PERF: find a way to transfer directly
			int[] pixel = new int[4];
			for (int u = 0; u < height; ++u) {
				for (int v = 0; v < width; ++v) {
					pixel = rast.getPixel(v, u, pixel);
					nativeImage.setColor(v, u, pixel[0] | (pixel[1] << 8) | (pixel[2] << 16) | (pixel[3] << 24));
				}
			}

			nativeImage.upload(0, x, y, 0, 0, width, height, true, true, true, false);
		}
	}

	@Override
	public boolean hasColor() {
		return true;
	}

	@Override
	public float getOversample() {
		return getHeight() / 9f;
	}

	@Override
	public float getAdvance() {
		return getWidth() / getOversample();
	}

	@Override
	public GlyphRenderer bake(Function<RenderableGlyph, GlyphRenderer> glyphRendererGetter) {
		return glyphRendererGetter.apply(this);
	}
}
