package de.danielxs01.stellwand.content.gui.stellwand;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class GuiBlockName extends GuiScreen {

	private static BufferedImage image = getImage();

	int xSize = 100;
	int ySize = 50;
	int x = 5;
	int y = 5;

	public GuiBlockName(Minecraft mc, String text) {
		ScaledResolution scaled = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		GL11.glPushMatrix();

//		ResourceLocation rl = new ResourceLocation(
//				Constants.MODID + ":" + "textures/blocks/trackStraight/block_track_empty_dark.png");
//		mc.getTextureManager().bindTexture(rl);
//
//		this.drawTexturedModalRect(x, y, 0, 0, 64, 64);

		for (int a = 0; a < 63; a++) {
			for (int b = 0; b < 63; b++) {
				Gui.drawRect(x + a, y + b, 1, 1, image.getRGB(x, y));
			}
		}

		GL11.glPopMatrix();

	}

	public static BufferedImage getImage() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(ClassLoader.getSystemResourceAsStream(
					"assets/stellwand/textures/blocks/trackStraight/block_track_empty_dark.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return image;
	}

	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}

}
