package net.vinrobot.mcemote.client.imageio.plugins.gif;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Iterator;
import java.util.NoSuchElementException;

class NodeHelper {
	public static String getNodeValue(Node node) {
		return node == null ? null : node.getNodeValue();
	}

	public static int getIntValue(Node node) {
		return node == null ? 0 : getIntValue(node.getNodeValue());
	}

	public static boolean getBooleanValue(Node node) {
		return node != null && getBooleanValue(node.getNodeValue());
	}

	public static int getIntValue(String value) {
		return value == null ? 0 : Integer.parseInt(value);
	}

	public static boolean getBooleanValue(String value) {
		return value != null && Boolean.parseBoolean(value);
	}

	public static Iterable<Node> getChildren(final Node node) {
		final NodeList nodeList = node.getChildNodes();
		return () -> new Iterator<>() {
			private int index = 0;

			@Override
			public boolean hasNext() {
				return index < nodeList.getLength();
			}

			@Override
			public Node next() {
				if (!hasNext()) throw new NoSuchElementException();
				return nodeList.item(index++);
			}
		};
	}
}
