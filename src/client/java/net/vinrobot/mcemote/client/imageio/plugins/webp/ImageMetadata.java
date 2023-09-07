package net.vinrobot.mcemote.client.imageio.plugins.webp;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public record ImageMetadata(
	int delay
) {
	public static ImageMetadata parseNode(final Node node) {
		final NamedNodeMap attr = node.getAttributes();
		final int duration = Integer.parseInt(attr.getNamedItem("Delay").getNodeValue());
		return new ImageMetadata(duration);
	}
}
