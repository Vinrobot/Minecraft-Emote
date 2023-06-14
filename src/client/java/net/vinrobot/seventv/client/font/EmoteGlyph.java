package net.vinrobot.seventv.client.font;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.font.RenderableGlyph;
import net.vinrobot.seventv.SevenTVMod;
import net.vinrobot.seventv.client.text.EmoteCharacter;

import java.awt.image.BufferedImage;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class EmoteGlyph implements Glyph {
	private final EmoteCharacter emoteCharacter;

	public EmoteGlyph(EmoteCharacter emoteCharacter) {
		this.emoteCharacter = emoteCharacter;
	}

	@Override
	public float getAdvance() {
		final int width = this.emoteCharacter.width();
		final int height = this.emoteCharacter.height();
		return width * 9f / height;
	}

	@Override
	public GlyphRenderer bake(Function<RenderableGlyph, GlyphRenderer> glyphRendererGetter) {
		final BufferedImage image = this.emoteCharacter.loadImage();
		return glyphRendererGetter.apply(new EmoteRenderableGlyph(image));
	}
}
