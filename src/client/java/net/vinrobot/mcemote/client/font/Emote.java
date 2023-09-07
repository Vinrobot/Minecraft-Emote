package net.vinrobot.mcemote.client.font;

import net.vinrobot.mcemote.client.imageio.NativeFrame;

import java.io.IOException;

public interface Emote {
	String getName();

	int getWidth();

	int getHeight();

	NativeFrame[] loadFrames() throws IOException;
}
