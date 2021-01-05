package de.danielxs01.stellwand.content.gui.stellwand;

import org.apache.commons.lang3.math.NumberUtils;

import de.danielxs01.stellwand.content.tileentities.TEBlockSender;
import de.danielxs01.stellwand.network.PacketDispatcher;
import de.danielxs01.stellwand.network.server.RequestTEStorageChange;
import de.danielxs01.stellwand.utils.BlockPos;
import de.danielxs01.stellwand.utils.EStellwandSignal;
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
	private GuiTextField nameText;
	private GuiButton signalButton;
	private GuiButton saveButton;

	// Gui Variables
	private EntityPlayer player;
	private BlockPos blockPos;
	private int frequency;
	private String name;
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
			this.name = sender.getName();
			this.signal = sender.getSignal();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {

		final int textWidth = 160;
		final int difference = textWidth / 2 + 5;

		// Frequency
		this.frequencyText = new GuiTextField(this.fontRendererObj, this.width / 2 - difference, this.height / 2 - 70,
				textWidth, 20);
		frequencyText.setMaxStringLength(24);
		frequencyText.setText("" + frequency);
		this.frequencyText.setFocused(true);

		// Name
		this.nameText = new GuiTextField(this.fontRendererObj, this.width / 2 - difference, this.height / 2 - 40,
				textWidth, 20);
		nameText.setMaxStringLength(24);
		nameText.setText("" + name);

		// Signal
		this.signalButton = new GuiButton(1, this.width / 2 - difference, this.height / 2 - 10, textWidth, 20,
				signal.name());
		this.signalButton.enabled = true;

		// Save
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
		else if (this.nameText.isFocused())
			this.nameText.textboxKeyTyped(par1, par2);
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
		this.nameText.updateCursorCounter();
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();

		final int textWidth = 160;
		final int difference = textWidth / 2 + 5;

		// Frequency
		frequencyText.drawTextBox();
		if (frequencyText.getText().length() == 0)
			this.drawCenteredString(mc.fontRenderer, "Frequency", (this.width / 2 - difference) + 31,
					this.height / 2 - 64, 0xFFAAAAAA);

		// Name
		nameText.drawTextBox();
		if (nameText.getText().length() == 0)
			this.drawCenteredString(mc.fontRenderer, "Name", (this.width / 2 - difference) + 16, this.height / 2 - 34,
					0xFFAAAAAA);

		// Signal
		signalButton.drawButton(mc, 0, 0);

		// Save
		saveButton.drawButton(mc, 0, 0);
	}

	@Override
	protected void mouseClicked(int x, int y, int btn) {
		super.mouseClicked(x, y, btn);
		this.frequencyText.mouseClicked(x, y, btn);
		this.nameText.mouseClicked(x, y, btn);
	}

	@Override
	public void actionPerformed(GuiButton button) {

		if (button.id == saveButton.id) {
			if (isInt(frequencyText.getText())) {
				frequency = Integer.parseInt(frequencyText.getText());
				signal = EStellwandSignal.valueOf(signalButton.displayString);
				name = nameText.getText();

				PacketDispatcher.sendToServer(new RequestTEStorageChange(blockPos, frequency, name, signal));

				this.player.closeScreen();
			}
		} else if (button.id == signalButton.id) {
			String currentSignal = signalButton.displayString;
			EStellwandSignal s = EStellwandSignal.valueOf(currentSignal);
			signalButton.displayString = s.getNextSignal().name();
		}

	}

	private boolean isInt(String text) {
		return NumberUtils.isNumber(text);
	}

}
