package net.landofrails.stellwand.content.gui;

import org.apache.commons.lang3.math.NumberUtils;

import net.landofrails.stellwand.LandOfRails;
import net.landofrails.stellwand.content.blocks.stellwand.tileentity.TEBlockSignal;
import net.landofrails.stellwand.content.network.PacketDispatcher;
import net.landofrails.stellwand.content.network.server.RequestChange;
import net.landofrails.stellwand.content.util.BlockPos;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class GuiBlockTrackSignal extends GuiScreen {

	// Gui ID
	public static final int GUIID = 2;

	// Gui Elements
	private GuiTextField text;
	private GuiButton button;

	// Gui Variables
	private EntityPlayer player;
	private BlockPos blockPos;
	private int frequency;

	public GuiBlockTrackSignal() {

	}

	public GuiBlockTrackSignal(EntityPlayer player, BlockPos blockPos) {
		this.player = player;
		this.blockPos = blockPos;

		TileEntity te = player.worldObj.getTileEntity(blockPos.getX(), blockPos.getY(), blockPos.getZ());
		if (te instanceof TEBlockSignal) {
			TEBlockSignal sender = (TEBlockSignal) te;
			this.frequency = sender.getFrequency();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {

		final int textWidth = 160;
		final int difference = textWidth / 2 + 5;
		this.text = new GuiTextField(this.fontRendererObj, this.width / 2 - difference, this.height / 2 - 46, textWidth,
				20);
		text.setMaxStringLength(24);
		text.setText("" + frequency);
		this.text.setFocused(true);

		// ID, x, y, width, height, text
		this.button = new GuiButton(0, this.width / 2 + difference, this.height / 2 - 46, 60, 20, "Save");
		this.button.enabled = true;
		buttonList.clear();
		buttonList.add(button);

	}

	@Override
	protected void keyTyped(char par1, int par2) {
		super.keyTyped(par1, par2);
		this.text.textboxKeyTyped(par1, par2);
		if ((par1 == 'E' || par1 == 'e') && !this.text.isFocused())
			this.player.closeScreen();

	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		this.text.updateCursorCounter();
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();

		text.drawTextBox();
		button.drawButton(mc, 0, 0);
	}

	@Override
	protected void mouseClicked(int x, int y, int btn) {
		super.mouseClicked(x, y, btn);
		this.text.mouseClicked(x, y, btn);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (isInt(text.getText())) {
			LandOfRails.logger.info("Sending RequestChange");
			int newFrequency = Integer.parseInt(text.getText());
			PacketDispatcher.sendToServer(new RequestChange(blockPos, newFrequency));
			this.player.closeScreen();
		}
	}

	private boolean isInt(String text) {
		return NumberUtils.isNumber(text);
	}

}
