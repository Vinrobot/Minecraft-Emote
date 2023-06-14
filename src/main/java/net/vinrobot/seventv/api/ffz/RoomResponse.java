package net.vinrobot.seventv.api.ffz;

import java.util.Map;

public record RoomResponse(
	Room room,
	Map<String, EmoteSet> sets
) {
}
