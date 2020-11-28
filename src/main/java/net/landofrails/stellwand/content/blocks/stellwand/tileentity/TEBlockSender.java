package net.landofrails.stellwand.content.blocks.stellwand.tileentity;

import java.util.UUID;

import net.landofrails.stellwand.content.blocks.stellwand.utils.EStellwandSignal;
import net.landofrails.stellwand.content.network.PacketDispatcher;
import net.landofrails.stellwand.content.network.server.ServerSenderNewSignal;
import net.landofrails.stellwand.content.util.BlockPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TEBlockSender extends TileEntity {

	protected static final int TARGETTICK = 20;
	protected int currentTick = 0;

	private UUID senderID = UUID.randomUUID();
	private int frequency;
	private EStellwandSignal signal;
	private boolean isPowered = false;

	public TEBlockSender() {
		this.frequency = 0;
		this.signal = EStellwandSignal.OFF;
	}

	public TEBlockSender(int frequency, EStellwandSignal signal) {
		this.frequency = frequency;
		this.signal = signal;
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
		worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
		markDirty();
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
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

	@Override
	public void updateEntity() {

		currentTick++;
		if (currentTick >= TARGETTICK) {
			currentTick = 0;

			boolean currentlyPowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);

			if (currentlyPowered != isPowered) {
				isPowered = currentlyPowered;
				EStellwandSignal sendSignal = isPowered ? getSignal() : EStellwandSignal.OFF;
				if (worldObj.isRemote)
					PacketDispatcher.sendToServer(new ServerSenderNewSignal(senderID, getFrequency(), sendSignal));
			}

		}
	}

	public UUID getSenderID() {
		return senderID;
	}

}
