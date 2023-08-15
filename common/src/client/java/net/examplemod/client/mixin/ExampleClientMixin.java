package net.examplemod.client.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.examplemod.ExampleMod.LOGGER;

@Mixin(MinecraftClient.class)
public class ExampleClientMixin {
	@Inject(at = @At("HEAD"), method = "run")
	private void run(CallbackInfo info) {
		LOGGER.info(this.getClass().getName() + "#run");
	}
}
