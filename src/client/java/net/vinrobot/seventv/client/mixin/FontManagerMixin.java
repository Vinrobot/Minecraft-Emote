package net.vinrobot.seventv.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.FontManager;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.vinrobot.seventv.SevenTVMod;
import net.vinrobot.seventv.client.font.EmoteFontStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(FontManager.class)
@Environment(EnvType.CLIENT)
public abstract class FontManagerMixin {
	@Accessor
	@Final
	abstract Map<Identifier, FontStorage> getFontStorages();

	@Accessor
	@Final
	abstract TextureManager getTextureManager();

	@Inject(at = @At("TAIL"), method = "reload(Lnet/minecraft/client/font/FontManager$ProviderIndex;Lnet/minecraft/util/profiler/Profiler;)V")
	public void injectReload(FontManager.ProviderIndex index, Profiler profiler, CallbackInfo ci) {
		TextureManager textureManager = getTextureManager();
		Map<Identifier, FontStorage> fontStorages = getFontStorages();

		fontStorages.put(EmoteFontStorage.IDENTIFIER, new EmoteFontStorage(textureManager));

		SevenTVMod.LOGGER.info("Loaded EmoteFontStorage");
	}
}
