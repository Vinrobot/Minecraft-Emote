package net.vinrobot.mcemote.api.seventv;

public record UserResponse(
	String id,
	String platform,
	String username,
	String display_name,
	long linked_at,
	int emote_capacity,
	String emote_set_id,
	EmoteSet emote_set,
	User user
) {
}
