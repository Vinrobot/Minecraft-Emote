package net.examplemod.fabric;

import net.examplemod.ExampleMod;
import net.fabricmc.api.ModInitializer;

public class ExampleModFabric implements ModInitializer {
	private final ExampleMod exampleMod = new ExampleMod();

	@Override
	public void onInitialize() {
		this.exampleMod.onInitialize();
	}
}
