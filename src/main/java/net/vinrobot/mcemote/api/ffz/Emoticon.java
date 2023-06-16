package net.vinrobot.mcemote.api.ffz;

import java.util.Date;
import java.util.Map;

public record Emoticon(
	int id,
	String name,
	int height,
	int width,
	Owner owner,
	Map<String, String> urls,
	int status,
	int usage_count,
	Date created_at,
	Date last_updated
) {
}
