package net.vinrobot.mcemote.api.seventv;

public record EmoteFile(
	String name,
	String static_name,
	int width,
	int height,
	int frame_count,
	int size,
	String format
) {
}
