package net.vinrobot.mcemote.client.widget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;

public class BaseScreen extends Screen {
	private final Screen parent;

	private static final int GRADIENT_START_COLOR = 0xFF000000;
	private static final int GRADIENT_END_COLOR = 0x00000000;

	public BaseScreen(final Screen parent, final Text title) {
		super(title);
		this.parent = parent;
	}

	@Override
	public void close() {
		this.client.setScreen(this.parent);
	}

	public void renderBackground(final DrawContext context, final int marginTop, final int marginBottom) {
		final int bottom = this.height - marginBottom;

		// Background (Middle)
		context.setShaderColor(0.125F, 0.125F, 0.125F, 1.0F);
		context.drawTexture(Screen.OPTIONS_BACKGROUND_TEXTURE, 0, marginTop, this.width, bottom, this.width, bottom - marginTop, 32, 32);
		context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

		// Background (Top & Bottom), based on Screen.renderBackgroundTexture(DrawContext)
		context.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
		context.drawTexture(Screen.OPTIONS_BACKGROUND_TEXTURE, 0, 0, 0.0F, 0.0F, this.width, marginTop, 32, 32);
		context.drawTexture(Screen.OPTIONS_BACKGROUND_TEXTURE, 0, bottom, 0.0F, bottom, this.width, this.height - bottom, 32, 32);
		context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

		// Gradients
		final RenderLayer guiOverlay = RenderLayer.getGuiOverlay();
		context.fillGradient(guiOverlay, 0, marginTop, this.width, marginTop + 4, GRADIENT_START_COLOR, GRADIENT_END_COLOR, 0);
		context.fillGradient(guiOverlay, 0, bottom - 4, this.width, bottom, GRADIENT_END_COLOR, GRADIENT_START_COLOR, 0);
	}
}
