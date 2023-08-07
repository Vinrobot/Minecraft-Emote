package net.vinrobot.mcemote;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.vinrobot.mcemote.config.ConfigurationService;
import net.vinrobot.mcemote.config.impl.file.FileConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class MinecraftEmoteMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("MCEmote");

	private static ConfigurationService configService;

	public static ConfigurationService getConfigService() {
		ConfigurationService ret = configService;
		if (ret == null) {
			throw new RuntimeException("Configuration service not yet available!");
		}
		return ret;
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		final Path configDir = FabricLoader.getInstance().getConfigDir();
		final Path configFile = configDir.resolve("mcemote.json");
		configService = new FileConfigurationService(configFile);
	}
}
