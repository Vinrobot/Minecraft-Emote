package net.vinrobot.mcemote.config;

import java.io.IOException;

public interface ConfigurationService {
	void load(Configuration configuration) throws IOException;

	void save(Configuration config) throws IOException;
}
