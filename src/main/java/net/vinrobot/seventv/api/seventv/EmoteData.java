package net.vinrobot.seventv.api.seventv;

import java.util.List;

public record EmoteData(
	String id,
	String name,
	int flags,
	int lifecycle,
	List<String> state,
	boolean listed,
	boolean animated,
	Owner owner,
	EmoteHost host,
	List<String> tags
) {
}
