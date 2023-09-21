package net.vinrobot.mcemote.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static net.vinrobot.mcemote.MinecraftEmoteMod.LOGGER;

public class ConfigurationManager {
	private final Queue<ChangeCallback> changeCallbacks = new ConcurrentLinkedQueue<>();
	private final ConfigurationService configService;
	private Configuration configuration;

	public ConfigurationManager(final ConfigurationService configService) {
		this.configService = Objects.requireNonNull(configService);
	}

	public Configuration getConfig() {
		final Configuration config = this.configuration;
		return config != null ? config : this.load();
	}

	public Configuration load() {
		final Configuration config = new Configuration();

		try {
			LOGGER.info("Loading config");
			this.configService.load(config);
		} catch (final NoSuchFileException e) {
			LOGGER.warn("Config file not found: {}", e.getFile());
		} catch (final FileNotFoundException e) {
			LOGGER.warn("Config file not found");
		} catch (final IOException e) {
			LOGGER.error("Failed to load config", e);
		} finally {
			this.triggerOnChange(config);
		}

		return this.configuration = config;
	}

	public void save() {
		final Configuration config = this.configuration;
		if (config != null) {
			this.triggerOnChange(config);

			try {
				this.configService.save(config);
			} catch (final IOException e) {
				LOGGER.error("Failed to save config", e);
			}
		}
	}

	public void reset() {
		this.configuration = null;
	}

	public void onChange(final ChangeCallback callback) {
		this.changeCallbacks.add(callback);
	}

	private void triggerOnChange(final Configuration config) {
		for (final ChangeCallback callback : this.changeCallbacks) {
			callback.onChange(config);
		}
	}

	public interface ChangeCallback {
		void onChange(Configuration config);
	}
}
