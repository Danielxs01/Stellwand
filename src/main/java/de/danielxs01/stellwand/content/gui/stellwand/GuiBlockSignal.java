package de.danielxs01.stellwand.content.gui.stellwand;

import org.apache.commons.lang3.math.NumberUtils;

import de.danielxs01.stellwand.Stellwand;
import de.danielxs01.stellwand.content.tileentities.TEBlockSignal;
import de.danielxs01.stellwand.network.PacketDispatcher;
import de.danielxs01.stellwand.network.server.RequestTEStorageChange;
import de.danielxs01.stellwand.utils.BlockPos;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class GuiBlockSignal extends GuiScreen {

	// Gui ID
	public static final int GUIID = 2;

	// Gui Elements
	private GuiTextField text;
	private GuiButton button;

	// Gui Variables
	private EntityPlayer player;
	private BlockPos blockPos;
	private int frequency;

	public GuiBlockSignal() {

	}

	public GuiBlockSignal(EntityPlayer player, BlockPos blockPos) {
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
			frequency = Integer.parseInt(text.getText());

			Stellwand.logger.info("GuiBlockSignal | Frequency: {}", frequency);

			// TODO: Update data for signal
			PacketDispatcher.sendToServer(new RequestTEStorageChange(blockPos, frequency, null));

			this.player.closeScreen();
		}
	}

	private boolean isInt(String text) {
		return NumberUtils.isNumber(text);
	}

}
