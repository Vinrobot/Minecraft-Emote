package net.vinrobot.mcemote.client.imageio.plugins.gif;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.time.Duration;

public record GraphicControlExtension(
	DisposalMethod disposalMethod,
	Duration delayTime
) {
	public static GraphicControlExtension parseNode(Node node) {
		final NamedNodeMap attr = node.getAttributes();
		final DisposalMethod disposalMethod = DisposalMethod.getByIdentifier(NodeHelper.getNodeValue(attr.getNamedItem("disposalMethod")));
		final Duration delayTime = Duration.ofMillis(NodeHelper.getIntValue(attr.getNamedItem("delayTime")) * 10L);
		return new GraphicControlExtension(disposalMethod, delayTime);
	}
}
