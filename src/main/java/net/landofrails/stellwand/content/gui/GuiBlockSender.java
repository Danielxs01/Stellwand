package net.landofrails.stellwand.content.gui;

import org.apache.commons.lang3.math.NumberUtils;

import net.landofrails.stellwand.LandOfRails;
import net.landofrails.stellwand.content.blocks.stellwand.tileentity.TEBlockSender;
import net.landofrails.stellwand.content.blocks.stellwand.utils.EStellwandSignal;
import net.landofrails.stellwand.content.network.PacketDispatcher;
import net.landofrails.stellwand.content.network.server.RequestChange;
import net.landofrails.stellwand.content.util.BlockPos;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class GuiBlockSender extends GuiScreen {

	// Gui ID
	public static final int GUIID = 1;

	// Gui Elements
	private GuiTextField frequencyText;
	private GuiButton signalButton;
	private GuiButton saveButton;

	// Gui Variables
	private EntityPlayer player;
	private BlockPos blockPos;
	private int frequency;
	private EStellwandSignal signal;

	public GuiBlockSender() {

	}

	public GuiBlockSender(EntityPlayer player, BlockPos blockPos) {
		this.player = player;
		this.blockPos = blockPos;

		TileEntity te = player.worldObj.getTileEntity(blockPos.getX(), blockPos.getY(), blockPos.getZ());
		if (te instanceof TEBlockSender) {
			TEBlockSender sender = (TEBlockSender) te;
			this.frequency = sender.getFrequency();
			this.signal = sender.getSignal();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {

		final int textWidth = 160;
		final int difference = textWidth / 2 + 5;

		this.frequencyText = new GuiTextField(this.fontRendererObj, this.width / 2 - difference, this.height / 2 - 40,
				textWidth, 20);
		frequencyText.setMaxStringLength(24);
		frequencyText.setText("" + frequency);
		this.frequencyText.setFocused(true);

		this.signalButton = new GuiButton(1, this.width / 2 - difference, this.height / 2 - 10, textWidth, 20,
				signal.name());
		this.signalButton.enabled = true;

		// ID, x, y, width, height, text
		this.saveButton = new GuiButton(0, this.width / 2 + difference, this.height / 2 - 10, 60, 20, "Save");
		this.saveButton.enabled = true;

		buttonList.clear();
		buttonList.add(signalButton);
		buttonList.add(saveButton);

	}

	@Override
	protected void keyTyped(char par1, int par2) {
		super.keyTyped(par1, par2);
		if (this.frequencyText.isFocused())
			this.frequencyText.textboxKeyTyped(par1, par2);
		else if ((par1 == 'E' || par1 == 'e'))
			this.player.closeScreen();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		this.frequencyText.updateCursorCounter();
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();

		frequencyText.drawTextBox();
		signalButton.drawButton(mc, 0, 0);
		saveButton.drawButton(mc, 0, 0);
	}

	@Override
	protected void mouseClicked(int x, int y, int btn) {
		super.mouseClicked(x, y, btn);
		this.frequencyText.mouseClicked(x, y, btn);
	}

	@Override
	public void actionPerformed(GuiButton button) {

		if (button.id == saveButton.id) {
			if (isInt(frequencyText.getText())) {
				LandOfRails.logger.info("Sending RequestChange");
				int newFrequency = Integer.parseInt(frequencyText.getText());
				EStellwandSignal newSignal = EStellwandSignal.valueOf(signalButton.displayString);
				PacketDispatcher.sendToServer(new RequestChange(blockPos, newFrequency, newSignal));
				this.player.closeScreen();
			}
		} else if (button.id == signalButton.id) {
			String currentSignal = signalButton.displayString;
			EStellwandSignal s = EStellwandSignal.valueOf(currentSignal);
			signalButton.displayString = getNextSignal(s).name();
		}

	}

	public EStellwandSignal getNextSignal(EStellwandSignal currentSignal) {
		EStellwandSignal[] signale = EStellwandSignal.values();
		boolean next = false;

		for (EStellwandSignal s : signale) {
			if (s.equals(currentSignal))
				next = true;
			else if (next)
				return s;
		}

		return signale[0];
	}

	private boolean isInt(String text) {
		return NumberUtils.isNumber(text);
	}

}
