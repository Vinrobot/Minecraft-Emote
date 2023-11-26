package net.vinrobot.mcemote.client.widget;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class SliderFieldWidget extends SliderWidget {
	public SliderFieldWidget(final int x, final int y, final int width, final int height, final Text text, final double value) {
		super(x, y, width, height, text, value);
	}

	@Override
	protected void updateMessage() {
	}

	@Override
	protected void applyValue() {
	}
}
