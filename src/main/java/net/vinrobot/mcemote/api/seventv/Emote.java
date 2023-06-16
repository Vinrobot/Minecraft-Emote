package net.vinrobot.mcemote.api.seventv;

public record Emote(
	String id,
	String name,
	int flags,
	long timestamp,
	String actor_id,
	EmoteData data
) {
}
