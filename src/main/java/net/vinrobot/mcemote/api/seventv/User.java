package net.vinrobot.mcemote.api.seventv;

import java.util.List;

public record User(
	String id,
	String username,
	String display_name,
	long created_at,
	String avatar_url,
	String biography,
	Style style,
	List<Editor> editors,
	List<String> roles,
	List<Connection> connections
) {
}
