package net.examplemod.client.fabric;

import net.examplemod.client.ExampleModClient;
import net.fabricmc.api.ClientModInitializer;

public class ExampleModClientFabric implements ClientModInitializer {
	private final ExampleModClient exampleModClient = new ExampleModClient();

	@Override
	public void onInitializeClient() {
		this.exampleModClient.onInitializeClient();
	}
}
