package net.vinrobot.mcemote.client.imageio.plugins.gif;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public record LogicalScreenDescriptor(
	int logicalScreenWidth,
	int logicalScreenHeight
) {
	public static LogicalScreenDescriptor parseNode(final Node node) {
		final NamedNodeMap attr = node.getAttributes();
		final int logicalScreenWidth = NodeHelper.getIntValue(attr.getNamedItem("logicalScreenWidth"));
		final int logicalScreenHeight = NodeHelper.getIntValue(attr.getNamedItem("logicalScreenHeight"));
		return new LogicalScreenDescriptor(logicalScreenWidth, logicalScreenHeight);
	}
}
