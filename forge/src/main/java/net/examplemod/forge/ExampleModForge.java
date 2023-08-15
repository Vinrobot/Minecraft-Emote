package net.examplemod.forge;

import dev.architectury.platform.forge.EventBuses;
import net.examplemod.ExampleMod;
import net.examplemod.client.ExampleModClient;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExampleMod.MOD_ID)
public class ExampleModForge {
	public ExampleModForge() {
		// Submit our event bus to let architectury register our content on the right time
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		EventBuses.registerModEventBus(ExampleMod.MOD_ID, modEventBus);
		modEventBus.register(this);
	}

	@SubscribeEvent
	public void onCommonSetup(FMLCommonSetupEvent event) {
		final ExampleMod exampleMod = new ExampleMod();
		exampleMod.onInitialize();
	}

	@SubscribeEvent
	public void onClientSetup(FMLClientSetupEvent event) {
		final ExampleModClient exampleModClient = new ExampleModClient();
		exampleModClient.onInitializeClient();
	}
}
