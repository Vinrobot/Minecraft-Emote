package net.vinrobot.mcemote.api.bttv;

public record Emote(
	String id,
	String code,
	String imageType,
	boolean animated,
	int width,
	int height
) {
}
