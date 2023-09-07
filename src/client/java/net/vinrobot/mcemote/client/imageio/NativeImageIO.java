package net.vinrobot.mcemote.client.imageio;

import net.vinrobot.mcemote.client.imageio.plugins.gif.GifReader;
import net.vinrobot.mcemote.client.imageio.plugins.webp.WebPReader;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class NativeImageIO {
	public static NativeFrame[] readAll(final URL url) throws IOException {
		try (final InputStream input = url.openStream()) {
			return readAll(input);
		}
	}

	public static NativeFrame[] readAll(final InputStream input) throws IOException {
		return readBufferedFrames(input).stream().map(BufferedFrame::toNativeFrame).toArray(NativeFrame[]::new);
	}

	private static List<BufferedFrame> readBufferedFrames(final InputStream input) throws IOException {
		try (final ImageInputStream stream = ImageIO.createImageInputStream(input)) {
			if (stream == null) {
				throw new IIOException("Can't create an ImageInputStream!");
			}

			final ImageReader reader = ImageIO.getImageReaders(stream).next();
			try {
				reader.setInput(stream, true, false);
				return switch (reader.getFormatName()) {
					case "gif" -> GifReader.readFrames(reader);
					case "webp" -> WebPReader.readFrames(reader);
					default -> List.of(new BufferedFrame(reader.read(0), Duration.ofDays(1)));
				};
			} finally {
				reader.dispose();
			}
		}
	}
}
