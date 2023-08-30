package net.vinrobot.mcemote.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.LinkedHashSet;
import java.util.Set;

import static net.vinrobot.mcemote.MinecraftEmoteMod.LOGGER;

public class ConfigurationManager {
	private final Set<ChangeCallback> changeCallbacks = new LinkedHashSet<>();
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
		} catch (final NoSuchFileException e) {
			LOGGER.warn("Config file not found: {}", e.getFile());
			return this.configuration = service.create();
		} catch (final FileNotFoundException e) {
			LOGGER.warn("Config file not found");
			return this.configuration = service.create();
		} catch (final IOException e) {
			LOGGER.error("Failed to load config", e);
			return this.configuration = service.create();
		} finally {
			this.triggerOnChange();
		}
	}

	public void save() {
		final Configuration config = this.configuration;
		if (config != null) {
			this.triggerOnChange();

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

	public void onChange(final ChangeCallback callback) {
		this.changeCallbacks.add(callback);
	}

	public void triggerOnChange() {
		for (final ChangeCallback callback : this.changeCallbacks) {
			callback.onChange(this.configuration);
		}
	}

	public interface ChangeCallback {
		void onChange(Configuration config);
	}
}
