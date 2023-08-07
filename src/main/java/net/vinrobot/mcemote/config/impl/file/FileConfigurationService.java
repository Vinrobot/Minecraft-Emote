package net.vinrobot.mcemote.config.impl.file;

import net.vinrobot.mcemote.config.Configuration;
import net.vinrobot.mcemote.config.ConfigurationService;
import net.vinrobot.mcemote.config.TypedGson;
import net.vinrobot.mcemote.config.impl.ConfigurationImpl;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileConfigurationService implements ConfigurationService {
	private final TypedGson<FileConfiguration> gson = new TypedGson<>();
	private final Path configFile;

	public FileConfigurationService(final Path configFile) {
		this.configFile = configFile;
	}

	@Override
	public Configuration create() {
		return new ConfigurationImpl();
	}

	@Override
	public Configuration load() throws IOException {
		FileConfiguration fileConfiguration;
		try (final Reader reader = Files.newBufferedReader(this.configFile)) {
			fileConfiguration = this.gson.fromJson(reader, FileConfiguration.class);
		}

		final Configuration config = this.create();
		if (fileConfiguration != null) {
			fileConfiguration.copyTo(config);
		}
		return config;
	}

	@Override
	public void save(final Configuration configuration) throws IOException {
		final FileConfiguration fileConfiguration = new FileConfiguration();
		fileConfiguration.copyFrom(configuration);

		try (final Writer writer = Files.newBufferedWriter(this.configFile)) {
			this.gson.toJson(fileConfiguration, writer);
		}
	}
}
