package net.vinrobot.mcemote;

import net.fabricmc.loader.api.FabricLoader;
import net.vinrobot.mcemote.config.ConfigurationManager;
import net.vinrobot.mcemote.config.ConfigurationService;
import net.vinrobot.mcemote.config.file.FileConfigurationService;
import net.vinrobot.mcemote.http.FileHttpCacheStorage;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClientBuilder;

import java.nio.file.Path;

public class MinecraftEmote {
	public static MinecraftEmote getInstance() {
		return MinecraftEmoteMod.getMinecraftEmote();
	}

	private final ConfigurationManager configManager;
	private final CloseableHttpClient httpClient;

	protected MinecraftEmote() {
		final Path configDir = FabricLoader.getInstance().getConfigDir();
		final Path configFile = configDir.resolve("mcemote.json");
		final ConfigurationService configService = new FileConfigurationService(configFile);
		this.configManager = new ConfigurationManager(configService);

		final Path cacheDir = FabricLoader.getInstance().getGameDir()
			.resolve("cache")
			.resolve("mcemote.httpcache");
		final CacheConfig cacheConfig = CacheConfig.custom()
			.setMaxObjectSize(1 << 26) // 64 MiB
			.build();
		this.httpClient = CachingHttpClientBuilder.create()
			.setCacheConfig(cacheConfig)
			.setHttpCacheStorage(new FileHttpCacheStorage(cacheDir))
			.build();
	}

	public ConfigurationManager getConfigManager() {
		return this.configManager;
	}

	public CloseableHttpClient getHttpClient() {
		return this.httpClient;
	}
}
