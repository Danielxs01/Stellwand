package net.landofrails.stellwand.content.blocks.stellwand.tileentity;

import net.landofrails.stellwand.content.blocks.stellwand.utils.EStellwandSignal;
import net.landofrails.stellwand.content.network.PacketDispatcher;
import net.landofrails.stellwand.content.network.server.RequestRefresh;
import net.landofrails.stellwand.content.util.BlockPos;
import net.landofrails.stellwand.proxy.ClientProxy;
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
			PacketDispatcher.sendToServer(new RequestRefresh(getBlockPos()));
			initialRender = true;
		}

		currentTick++;
		if (currentTick >= TARGETTICK) {
			currentTick = 0;
			EStellwandSignal s = ClientProxy.signalHandler.getSignal(frequency);

			if (s != this.signal) {
				this.setSignal(s);
			}
		}
	}

}
