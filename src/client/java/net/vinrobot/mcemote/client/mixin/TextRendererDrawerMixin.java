package net.vinrobot.mcemote.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.VertexConsumer;
import net.vinrobot.mcemote.client.font.NativeImageGlyph;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.vinrobot.mcemote.client.font.CustomFontStorage.GLYPH_HEIGHT;

@Mixin(TextRenderer.Drawer.class)
public abstract class TextRendererDrawerMixin {
	@Accessor("x")
	abstract void setX(float x);

	@Unique
	private volatile Glyph localGlyph = null; // Used to catch the local variable in TextRenderer.Drawer#accept()

	@Redirect(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/FontStorage;getGlyph(IZ)Lnet/minecraft/client/font/Glyph;"))
	private Glyph acceptGetGlyph(final FontStorage instance, final int codePoint, final boolean validateAdvance) {
		return this.localGlyph = instance.getGlyph(codePoint, validateAdvance);
	}

	@Redirect(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawGlyph(Lnet/minecraft/client/font/GlyphRenderer;ZZFFFLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumer;FFFFI)V"))
	private void acceptDrawGlyph(final TextRenderer instance, final GlyphRenderer glyphRenderer, final boolean bold, final boolean italic, final float weight, final float x, final float y, final Matrix4f matrix, final VertexConsumer vertexConsumer, final float red, final float green, final float blue, final float alpha, final int light) {
		if (this.localGlyph instanceof NativeImageGlyph glyph) {
			final GameOptions options = MinecraftClient.getInstance().options;
			final float lineSpacing = options.getChatLineSpacing().getValue().floatValue();
			final float scale = (lineSpacing + 1);

			final float newX = x / scale;
			// Align the glyph to the top of the line
			final float newY = (y - (lineSpacing * GLYPH_HEIGHT) / 2) / scale;

			matrix.scale(scale);
			instance.drawGlyph(glyphRenderer, bold, italic, weight, newX, newY, matrix, vertexConsumer, red, green, blue, alpha, light);
			matrix.scale(1 / scale);

			// Fix the spacing between glyphs
			this.setX(x + glyph.getAdvance(bold) * lineSpacing);
		} else {
			instance.drawGlyph(glyphRenderer, bold, italic, weight, x, y, matrix, vertexConsumer, red, green, blue, alpha, light);
		}
	}
}
