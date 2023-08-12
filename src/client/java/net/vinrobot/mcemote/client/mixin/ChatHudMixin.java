package net.vinrobot.mcemote.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import net.vinrobot.mcemote.client.MinecraftEmoteModClient;
import net.vinrobot.mcemote.client.text.EmoteParser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChatHud.class)
@Environment(EnvType.CLIENT)
public class ChatHudMixin {
	@ModifyVariable(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", ordinal = 0)
	private Text addMessage(Text message) {
		return EmoteParser.wrapText(message, MinecraftEmoteModClient.EMOTES_MANAGER);
	}
}
