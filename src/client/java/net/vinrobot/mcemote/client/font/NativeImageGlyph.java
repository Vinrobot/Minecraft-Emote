package net.vinrobot.mcemote.client.font;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.font.RenderableGlyph;
import net.minecraft.client.texture.NativeImage;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public record NativeImageGlyph(
	NativeImage nativeImage,
	float advance,
	float oversample
) implements Glyph, RenderableGlyph {
	@Override
	public float getAdvance() {
		return this.advance;
	}

	@Override
	public GlyphRenderer bake(Function<RenderableGlyph, GlyphRenderer> glyphRendererGetter) {
		return glyphRendererGetter.apply(this);
	}

	@Override
	public int getWidth() {
		return this.nativeImage.getWidth();
	}

	@Override
	public int getHeight() {
		return this.nativeImage.getHeight();
	}

	@Override
	public void upload(int x, int y) {
		final int width = this.nativeImage.getWidth();
		final int height = this.nativeImage.getHeight();
		this.nativeImage.upload(0, x, y, 0, 0, width, height, true, true, true, false);
	}

	@Override
	public boolean hasColor() {
		return true;
	}

	@Override
	public float getOversample() {
		return this.oversample;
	}

	@Override
	public float getAdvance(boolean bold) {
		// Bold is not supported
		return this.getAdvance();
	}

	@Override
	public float getBoldOffset() {
		// Bold is not supported
		return 0;
	}

	@Override
	public float getShadowOffset() {
		// Shadow is not supported
		return 0;
	}
}
