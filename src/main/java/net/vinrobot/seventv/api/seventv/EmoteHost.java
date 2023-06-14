package net.vinrobot.seventv.api.seventv;

import java.util.List;

public record EmoteHost(
	String url,
	List<EmoteFile> files
) {
}
