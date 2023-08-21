package net.vinrobot.mcemote.api.ffz;

import java.util.Map;

public record GlobalEmoteSets(
	int[] default_sets,
	Map<String, EmoteSet> sets
) {
}
