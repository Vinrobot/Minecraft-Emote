package net.vinrobot.mcemote.config;

import java.io.IOException;

public interface ConfigurationService {
	Configuration create();

	Configuration load() throws IOException;

	void save(Configuration config) throws IOException;
}
