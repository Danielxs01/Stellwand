package de.danielxs01.stellwand.content.tileentities;

import de.danielxs01.stellwand.utils.BlockPos;
import de.danielxs01.stellwand.utils.EStellwandSignal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TEBlockSignal extends TileEntity {

	protected static final int TARGETTICK = 10;
	protected int currentTick = 0;
	private boolean initialRender = false;

	private int frequency;
	private EStellwandSignal signal;

	public TEBlockSignal() {
		this.frequency = 0;
		this.signal = EStellwandSignal.OFF;

	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {

		compound.setInteger("frequency", frequency);
		compound.setInteger("signalID", signal.getID());

		super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {

		this.frequency = compound.getInteger("frequency");
		this.signal = EStellwandSignal.fromID(compound.getInteger("signalID"));

		super.readFromNBT(compound);
	}

	public BlockPos getBlockPos() {
		return new BlockPos(this.xCoord, this.yCoord, this.zCoord);
	}

	public void setSignal(EStellwandSignal signal) {
		this.signal = signal;

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);

		markDirty();
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);

		markDirty();
	}

	public int getFrequency() {
		return frequency;
	}

	public EStellwandSignal getSignal() {
		return signal;
	}

	@Override
	public boolean canUpdate() {
		return true;
	}

	// Client
	@Override
	public void updateEntity() {

		if (!worldObj.isRemote)
			return;

		if (!initialRender) {

			// TODO: Get data from server if this is client

			initialRender = true;
		}

		currentTick++;
		if (currentTick >= TARGETTICK) {
			currentTick = 0;

			// TODO: Get current signal of frequency
			EStellwandSignal s = null; // ClientProxy.signalHandler.getSignal(frequency);

			if (s != this.signal) {
				this.setSignal(s);
			}
		}
	}

}
