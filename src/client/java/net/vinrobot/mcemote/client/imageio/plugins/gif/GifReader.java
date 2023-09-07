package net.vinrobot.mcemote.client.imageio.plugins.gif;

import net.vinrobot.mcemote.client.imageio.BufferedFrame;
import org.w3c.dom.Node;

import javax.imageio.ImageReader;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads GIF frames.
 *
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/imageio/metadata/doc-files/gif_metadata.html">GIF Metadata Format Specification</a>
 */
public final class GifReader {
	public static final String IMAGEIO_GIF_STREAM_METADATA_FORMAT = "javax_imageio_gif_stream_1.0";
	public static final String IMAGEIO_GIF_IMAGE_METADATA_FORMAT = "javax_imageio_gif_image_1.0";
	public static final Color DEFAULT_BACKGROUND_COLOR = new Color(0, 0, 0, 0); // Transparent

	public static List<BufferedFrame> readFrames(final ImageReader reader) throws IOException {
		final List<BufferedFrame> frames = new ArrayList<>();

		int width = -1, height = -1;
		Color backgroundColor = DEFAULT_BACKGROUND_COLOR;

		final Node streamMetadata = reader.getStreamMetadata().getAsTree(IMAGEIO_GIF_STREAM_METADATA_FORMAT);
		for (final Node nodeItem : NodeHelper.getChildren(streamMetadata)) {
			switch (nodeItem.getNodeName()) {
				case "LogicalScreenDescriptor":
					LogicalScreenDescriptor logicalScreenDescriptor = LogicalScreenDescriptor.parseNode(nodeItem);
					width = logicalScreenDescriptor.logicalScreenWidth();
					height = logicalScreenDescriptor.logicalScreenHeight();
					break;
				case "GlobalColorTable":
					backgroundColor = GlobalColorTable.parseBackgroundColor(nodeItem, DEFAULT_BACKGROUND_COLOR);
					break;
			}
		}

		BufferedImage master = null;
		Graphics2D masterGraphics = null;

		for (int frameIndex = 0; ; ++frameIndex) {
			final BufferedImage image;
			try {
				image = reader.read(frameIndex);
			} catch (final IndexOutOfBoundsException io) {
				break;
			}

			if (master == null) {
				if (width == -1 || height == -1) {
					width = image.getWidth();
					height = image.getHeight();
				}

				master = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				masterGraphics = master.createGraphics();
				masterGraphics.setBackground(backgroundColor);
			}

			int x = 0, y = 0;
			GraphicControlExtension graphicControlExtension = null;

			final Node imageMetadata = reader.getImageMetadata(frameIndex).getAsTree(IMAGEIO_GIF_IMAGE_METADATA_FORMAT);
			for (final Node nodeItem : NodeHelper.getChildren(imageMetadata)) {
				switch (nodeItem.getNodeName()) {
					case "ImageDescriptor":
						ImageDescriptor imageDescriptor = ImageDescriptor.parseNode(nodeItem);
						x = imageDescriptor.imageLeftPosition();
						y = imageDescriptor.imageTopPosition();
						break;
					case "GraphicControlExtension":
						graphicControlExtension = GraphicControlExtension.parseNode(nodeItem);
						break;
				}
			}

			masterGraphics.drawImage(image, x, y, null);
			frames.add(new BufferedFrame(copy(master), graphicControlExtension.delayTime()));

			switch (graphicControlExtension.disposalMethod()) {
				case RESTORE_TO_BACKGROUND:
					masterGraphics.clearRect(x, y, image.getWidth(), image.getHeight());
					break;
				case RESTORE_TO_PREVIOUS:
					master = copy(frames.get(frameIndex - 1).image());
					masterGraphics = master.createGraphics();
					masterGraphics.setBackground(backgroundColor);
					break;
			}
		}

		return frames;
	}

	private static BufferedImage copy(final BufferedImage source) {
		final ColorModel model = source.getColorModel();
		final WritableRaster raster = source.copyData(null);
		final boolean alpha = source.isAlphaPremultiplied();
		return new BufferedImage(model, raster, alpha, null);
	}
}
