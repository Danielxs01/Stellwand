package de.danielxs01.stellwand.content.gui.stellwand;

import org.apache.commons.lang3.math.NumberUtils;

import de.danielxs01.stellwand.content.items.ItemTool;
import de.danielxs01.stellwand.network.PacketDispatcher;
import de.danielxs01.stellwand.network.bidirectional.RequestToolDataChange;
import de.danielxs01.stellwand.utils.EStellwandSignal;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GuiToolData extends GuiScreen {

	// Gui ID
	public static final int GUIID = 3;

	// Gui Elements
	private GuiTextField frequencyText;
	private GuiButton signalButton;
	private GuiButton saveButton;

	// Gui Variables
	private EntityPlayer player;
	private int frequency;
	private EStellwandSignal signal;

	public GuiToolData() {

	}

	public GuiToolData(EntityPlayer player) {
		this.player = player;

		ItemStack stack = player.getCurrentEquippedItem();
		NBTTagCompound nbt = ItemTool.getPreparedNBT(stack, true);

		this.frequency = nbt.getInteger("frequency");
		this.signal = EStellwandSignal.valueOf(nbt.getString("signal"));

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
				frequency = Integer.parseInt(frequencyText.getText());
				signal = EStellwandSignal.valueOf(signalButton.displayString);

				PacketDispatcher.sendToServer(new RequestToolDataChange(frequency, signal));

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