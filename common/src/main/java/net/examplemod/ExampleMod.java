package net.examplemod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod {
	public static final String MOD_ID = "examplemod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public void onInitialize() {
		LOGGER.info(this.getClass().getName() + "#init");
		LOGGER.info(ExampleExpectPlatform.getConfigDirectory().toString());
	}
}
