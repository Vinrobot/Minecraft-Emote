package net.vinrobot.mcemote.api.ffz;

public record Room(
	int _id,
	int twitch_id,
	String id,
	boolean is_group,
	String display_name,
	int set
) {
}
