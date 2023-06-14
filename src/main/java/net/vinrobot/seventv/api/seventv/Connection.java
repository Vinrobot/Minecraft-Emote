package net.vinrobot.seventv.api.seventv;

public record Connection(
	String id,
	String platform,
	String username,
	String display_name,
	long linked_at,
	int emote_capacity,
	String emote_set_id,
	EmoteSet emote_set
) {
}
