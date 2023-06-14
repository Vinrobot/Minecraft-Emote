package net.vinrobot.seventv.client.text;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

@Environment(EnvType.CLIENT)
public record EmoteCharacter(String name, int codePoint, String url, int width, int height) {
	public BufferedImage loadImage() {
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
