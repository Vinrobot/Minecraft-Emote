package net.examplemod.client;

import static net.examplemod.ExampleMod.LOGGER;

public class ExampleModClient {
	public void onInitializeClient() {
		LOGGER.info(this.getClass().getName() + "#onInitializeClient");
	}
}
