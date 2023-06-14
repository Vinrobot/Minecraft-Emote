package net.vinrobot.seventv.api.seventv;

import java.util.List;

public record Owner(
	String id,
	String username,
	String display_name,
	String avatar_url,
	Style style,
	List<String> roles
) {
}
