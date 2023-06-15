package net.vinrobot.seventv.client.font;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface Emote {
	String getName();

	int getWidth();

	int getHeight();

	BufferedImage loadImage() throws IOException;
}
