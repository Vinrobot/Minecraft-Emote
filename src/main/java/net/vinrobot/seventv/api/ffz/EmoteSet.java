package net.vinrobot.seventv.api.ffz;

import java.util.List;

public record EmoteSet(
	int id,
	String title,
	List<Emoticon> emoticons
) {
}
