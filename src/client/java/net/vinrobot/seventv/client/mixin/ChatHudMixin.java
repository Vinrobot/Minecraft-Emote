package net.vinrobot.seventv.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.OrderedText;
import net.vinrobot.seventv.client.text.EmoteParser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChatHud.class)
@Environment(EnvType.CLIENT)
public class ChatHudMixin {
	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;III)I"))
	private int drawTextWithShadow(DrawContext drawContext, TextRenderer textRenderer, OrderedText orderedText, int x, int y, int color, Operation<Integer> original) {
		orderedText = EmoteParser.wrapOrderedText(orderedText);
		return original.call(drawContext, textRenderer, orderedText, x, y, color);
	}
}
