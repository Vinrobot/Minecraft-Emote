package net.vinrobot.mcemote.api.seventv;

public record Editor(
	String id,
	int permissions,
	boolean visible,
	Object added_at
) {
}
