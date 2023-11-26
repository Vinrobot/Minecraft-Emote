package net.vinrobot.mcemote.client.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.vinrobot.mcemote.config.Configuration;
import net.vinrobot.mcemote.config.ConfigurationManager;

public class ConfigurationScreen extends BaseScreen {
	private static final Text MENU_TITLE = Text.translatableWithFallback("mcemote.options.title", "Minecraft Emote Options");
	private static final Text TWITCH_ID_LABEL = Text.translatableWithFallback("mcemote.options.twitchId", "Twitch ID");
	private static final Text EMOTE_SCALING_LABEL = Text.translatableWithFallback("mcemote.options.emoteScaling", "Emote Scaling");
	private static final Text EMOTE_SCALING_INFO = Text.translatableWithFallback("mcemote.options.emoteScalingInfo", "Chat Settings → Line Spacing");

	private static final int TITLE_COLOR = 0xFFFFFF;
	private static final int LABEL_COLOR = 0xA0A0A0;
	private static final int TEXTFIELD_VALID_COLOR = 0xE0E0E0;
	private static final int TEXTFIELD_INVALID_COLOR = 0xFF0000;

	private final ConfigurationManager configManager;

	private ButtonWidget doneButton;
	private TextFieldWidget twitchIdField;
	private SliderFieldWidget emoteScalingField;

	public ConfigurationScreen(final Screen parent, final ConfigurationManager configManager) {
		super(parent, MENU_TITLE);
		this.configManager = configManager;
	}

	@Override
	public void tick() {
		super.tick();
		this.twitchIdField.tick();
	}

	@Override
	protected void init() {
		final int widgetWidth = 200;
		final int widgetHeight = 20;
		final int widgetX = (this.width - widgetWidth) / 2;
		final Configuration config = this.configManager.getConfig();

		this.twitchIdField = new TextFieldWidget(this.textRenderer, widgetX, 75, widgetWidth, widgetHeight, TWITCH_ID_LABEL);
		this.twitchIdField.setMaxLength(32);
		this.twitchIdField.setText(config.twitchId().get());
		this.twitchIdField.setChangedListener((value) -> this.validateInputs());
		this.addDrawableChild(this.twitchIdField);

		this.emoteScalingField = new SliderFieldWidget(widgetX, 118, widgetWidth, widgetHeight, EMOTE_SCALING_INFO, 0);
		this.addDrawable(this.emoteScalingField);

		final int buttonSpacing = widgetHeight + 6;
		int buttonY = this.height - 5;

		buttonY -= buttonSpacing;
		this.doneButton = this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, this::saveAndClose)
			.dimensions(widgetX, buttonY, widgetWidth, widgetHeight).build());

		buttonY -= buttonSpacing;
		this.addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL, this::discardAndClose)
			.dimensions(widgetX, buttonY, widgetWidth, widgetHeight).build());

		this.validateInputs();
	}

	@Override
	public void resize(MinecraftClient client, int width, int height) {
		final String twitchIdValue = this.twitchIdField.getText();
		super.resize(client, width, height);
		this.twitchIdField.setText(twitchIdValue);
	}

	private void saveAndClose(final ButtonWidget ignored) {
		final Configuration config = this.configManager.getConfig();

		final String twitchIdValue = this.twitchIdField.getText();
		config.twitchId().set(twitchIdValue);

		this.configManager.save();
		this.close();
	}

	private void discardAndClose(final ButtonWidget ignored) {
		this.close();
	}

	private void validateInputs() {
		final Configuration config = this.configManager.getConfig();

		final String twitchIdValue = this.twitchIdField.getText();
		final boolean isTwitchIdValid = twitchIdValue != null && config.twitchId().isValid(twitchIdValue);

		this.twitchIdField.setEditableColor(isTwitchIdValid ? TEXTFIELD_VALID_COLOR : TEXTFIELD_INVALID_COLOR);

		this.doneButton.active = isTwitchIdValid;
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		this.renderBackground(context, 41, 67);

		context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 16, TITLE_COLOR);
		context.drawTextWithShadow(this.textRenderer, TWITCH_ID_LABEL, this.width / 2 - 100, 60, LABEL_COLOR);
		context.drawTextWithShadow(this.textRenderer, EMOTE_SCALING_LABEL, this.width / 2 - 100, 106, LABEL_COLOR);

		super.render(context, mouseX, mouseY, delta);
	}
}
