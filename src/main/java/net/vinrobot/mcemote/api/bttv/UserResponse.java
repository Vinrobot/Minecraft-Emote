package net.vinrobot.mcemote.api.bttv;

public record UserResponse(
	String id,
	String[] bots,
	String avatar,
	Emote[] channelEmotes,
	Emote[] sharedEmotes
) {
}
