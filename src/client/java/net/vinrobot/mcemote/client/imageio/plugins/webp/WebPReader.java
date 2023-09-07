package net.vinrobot.mcemote.client.imageio.plugins.webp;

import net.vinrobot.mcemote.client.imageio.BufferedFrame;
import org.w3c.dom.Node;

import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public final class WebPReader {
	public static final String IMAGEIO_WEBP_IMAGE_METADATA_FORMAT = "net_vinrobot_imageio_webp_image_1.0";

	public static List<BufferedFrame> readFrames(ImageReader reader) throws IOException {
		final List<BufferedFrame> frames = new ArrayList<>();
		for (int frameIndex = 0; ; ++frameIndex) {
			final BufferedImage frame;
			try {
				frame = reader.read(frameIndex);
			} catch (IndexOutOfBoundsException e) {
				break;
			}

			final IIOMetadata imageMetadata = reader.getImageMetadata(frameIndex);
			final Duration delay = getDuration(imageMetadata);

			frames.add(new BufferedFrame(frame, delay));
		}

		return frames;
	}

	private static Duration getDuration(final IIOMetadata metadata) {
		final Node metadataNode = metadata.getAsTree(IMAGEIO_WEBP_IMAGE_METADATA_FORMAT);
		final ImageMetadata imageMetadata = ImageMetadata.parseNode(metadataNode);
		return Duration.ofMillis(imageMetadata.delay());
	}
}
