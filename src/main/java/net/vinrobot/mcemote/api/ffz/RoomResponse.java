package net.vinrobot.mcemote.api.ffz;

import java.util.Map;

public record RoomResponse(
	Room room,
	Map<String, EmoteSet> sets
) {
}
