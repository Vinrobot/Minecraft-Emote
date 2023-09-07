package net.vinrobot.mcemote.client.imageio.plugins.gif;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.Color;

public final class GlobalColorTable {
	public static Color parseBackgroundColor(Node node, Color defaultColor) {
		final NamedNodeMap attr = node.getAttributes();
		final int backgroundColorIndex = NodeHelper.getIntValue(attr.getNamedItem("backgroundColorIndex"));

		final NodeList colorNodes = node.getChildNodes();
		for (int i = 0, l = colorNodes.getLength(); i < l; ++i) {
			final NamedNodeMap colorAttr = colorNodes.item(i).getAttributes();
			final int index = NodeHelper.getIntValue(colorAttr.getNamedItem("index"));
			if (index == backgroundColorIndex) {
				final int red = NodeHelper.getIntValue(colorAttr.getNamedItem("red"));
				final int green = NodeHelper.getIntValue(colorAttr.getNamedItem("green"));
				final int blue = NodeHelper.getIntValue(colorAttr.getNamedItem("blue"));
				return new Color(red, green, blue);
			}
		}
		return defaultColor;
	}
}
