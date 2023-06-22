package net.vinrobot.mcemote.client.helpers;

import net.minecraft.client.texture.NativeImage;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public final class NativeImageHelper {
	public static NativeImage fromBufferedImage(BufferedImage bufferedImage) throws UnsupportedOperationException {
		final Raster raster = bufferedImage.getTile(0, 0);

		final NativeImage.Format imageFormat = switch (raster.getNumBands()) {
			case 4, 3 -> NativeImage.Format.RGBA;
			default -> throw new UnsupportedOperationException("Unsupported number of bands");
		};

		final int height = raster.getHeight();
		final int width = raster.getWidth();

		final NativeImage nativeImage = new NativeImage(imageFormat, width, height, false);

		// PERF: find a way to transfer directly
		int[] pixel = new int[]{0, 0, 0, 255/*ALPHA*/};
		for (int u = 0; u < height; ++u) {
			for (int v = 0; v < width; ++v) {
				pixel = raster.getPixel(v, u, pixel);
				nativeImage.setColor(v, u, pixel[0] | (pixel[1] << 8) | (pixel[2] << 16) | (pixel[3] << 24));
			}
		}

		return nativeImage;
	}
}
