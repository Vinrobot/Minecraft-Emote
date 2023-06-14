package net.vinrobot.seventv.api.seventv;

import java.util.List;

public record EmoteSet(
	String id,
	String name,
	int flags,
	List<String> tags,
	boolean immutable,
	boolean privileged,
	List<Emote> emotes,
	int emote_count,
	int capacity,
	Owner owner
) {
}
