package net.vinrobot.mcemote.api.bttv;

public enum Provider {
	TWITCH("twitch"),
	YOUTUBE("youtube");

	public final String path;

	Provider(String path) {
		this.path = path;
	}
}
