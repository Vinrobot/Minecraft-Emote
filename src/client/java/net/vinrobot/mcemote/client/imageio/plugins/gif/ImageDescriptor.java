package net.vinrobot.mcemote.client.imageio.plugins.gif;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public record ImageDescriptor(
	int imageLeftPosition,
	int imageTopPosition
) {
	public static ImageDescriptor parseNode(final Node node) {
		final NamedNodeMap attr = node.getAttributes();
		final int imageLeftPosition = NodeHelper.getIntValue(attr.getNamedItem("imageLeftPosition"));
		final int imageTopPosition = NodeHelper.getIntValue(attr.getNamedItem("imageTopPosition"));
		return new ImageDescriptor(imageLeftPosition, imageTopPosition);
	}
}
