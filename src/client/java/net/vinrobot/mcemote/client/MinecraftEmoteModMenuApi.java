package net.vinrobot.mcemote.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.vinrobot.mcemote.MinecraftEmote;
import net.vinrobot.mcemote.client.widget.ConfigurationScreen;
import net.vinrobot.mcemote.config.ConfigurationManager;

public class MinecraftEmoteModMenuApi implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		final ConfigurationManager configManager = MinecraftEmote.getInstance().getConfigManager();
		return parent -> new ConfigurationScreen(parent, configManager);
	}
}
