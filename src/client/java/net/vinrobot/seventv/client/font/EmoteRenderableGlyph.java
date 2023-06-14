package net.vinrobot.seventv.client.font;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.RenderableGlyph;
import net.minecraft.client.texture.NativeImage;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

@Environment(EnvType.CLIENT)
public class EmoteRenderableGlyph implements RenderableGlyph {
	private final BufferedImage bufferedImage;

	public EmoteRenderableGlyph(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	@Override
	public int getWidth() {
		return this.bufferedImage.getWidth();
	}

	@Override
	public int getHeight() {
		return this.bufferedImage.getHeight();
	}

	@Override
	public void upload(int x, int y) {
		final Raster raster = this.bufferedImage.getTile(0, 0);
		assert raster.getNumBands() == 4;

		final int height = raster.getHeight();
		final int width = raster.getWidth();

		try (final NativeImage nativeImage = new NativeImage(NativeImage.Format.RGBA, width, height, false)) {
			// PERF: find a way to transfer directly
			int[] pixel = new int[4];
			for (int u = 0; u < height; ++u) {
				for (int v = 0; v < width; ++v) {
					pixel = raster.getPixel(v, u, pixel);
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
}
