package de.danielxs01.stellwand.content.gui.stellwand;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

public class GuiBlockName extends GuiScreen {

	int x = 5;
	int y = 5;

	public GuiBlockName(Minecraft mc, String[] lines) {
		GL11.glPushMatrix();

		int width = 0;

		for (String line : lines) {
			int stringWidth = mc.fontRenderer.getStringWidth(line) + 15;
			if (stringWidth > width)
				width = stringWidth;
		}

		int height = (int) (4 + lines.length * mc.fontRenderer.FONT_HEIGHT * 1.5);
		Gui.drawRect(x, y, width, height, 0xCC57875D);

		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			this.drawString(mc.fontRenderer, line, x + 4, y + 4 + (i * 10), 0xFFFFFFFF);
		}

		GL11.glPopMatrix();

	}

}
