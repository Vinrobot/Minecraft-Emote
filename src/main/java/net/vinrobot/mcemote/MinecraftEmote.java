package net.vinrobot.mcemote;

import net.fabricmc.loader.api.FabricLoader;
import net.vinrobot.mcemote.config.ConfigurationManager;
import net.vinrobot.mcemote.config.ConfigurationService;
import net.vinrobot.mcemote.config.file.FileConfigurationService;

import java.nio.file.Path;

public class MinecraftEmote {
	public static MinecraftEmote getInstance() {
		return MinecraftEmoteMod.getMinecraftEmote();
	}

	private final ConfigurationManager configManager;

	protected MinecraftEmote() {
		final Path configDir = FabricLoader.getInstance().getConfigDir();
		final Path configFile = configDir.resolve("mcemote.json");
		final ConfigurationService configService = new FileConfigurationService(configFile);
		this.configManager = new ConfigurationManager(configService);
	}

	public ConfigurationManager getConfigManager() {
		return this.configManager;
	}
}
