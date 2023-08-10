package net.vinrobot.mcemote.config;

import java.io.IOException;

import static net.vinrobot.mcemote.MinecraftEmoteMod.LOGGER;

public class ConfigurationManager {
	private final ConfigurationService configService;
	private Configuration configuration;

	public ConfigurationManager(final ConfigurationService configService) {
		this.configService = configService;
	}

	public Configuration getConfig() {
		final Configuration config = this.configuration;
		return config != null ? config : this.load();
	}

	public Configuration load() {
		final ConfigurationService service = this.configService;
		try {
			LOGGER.info("Loading config");
			return this.configuration = service.load();
		} catch (final IOException e) {
			LOGGER.error("Failed to load config", e);
			return this.configuration = service.create();
		}
	}

	public void save() {
		final Configuration config = this.configuration;
		if (config != null) {
			try {
				this.configService.save(config);
			} catch (final IOException e) {
				LOGGER.error("Failed to save config", e);
			}
		}
	}

	public void reset() {
		this.configuration = this.configService.create();
	}
}
