package de.danielxs01.stellwand.content.tileentities;

import java.util.UUID;

import de.danielxs01.stellwand.network.PacketDispatcher;
import de.danielxs01.stellwand.network.server.RequestSignalChange;
import de.danielxs01.stellwand.network.server.RequestTEStorage;
import de.danielxs01.stellwand.utils.BlockPos;
import de.danielxs01.stellwand.utils.EStellwandSignal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TEBlockSender extends TileEntity {

	private static final int TARGETTICK = 20;
	private int currentTick = 0;
	private boolean initialTick = true;

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

		if (!worldObj.isRemote)
			return;

		if (initialTick) {
			initialTick = false;
			PacketDispatcher.sendToServer(new RequestTEStorage(getBlockPos()));
		}

		currentTick++;
		if (currentTick >= TARGETTICK) {
			currentTick = 0;

			boolean currentlyPowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);

			if (currentlyPowered != isPowered) {
				isPowered = currentlyPowered;

				// TODO: Change Signal and do some Packet-stuff
				EStellwandSignal s = isPowered ? signal : EStellwandSignal.OFF;
				PacketDispatcher.sendToServer(new RequestSignalChange(senderID, frequency, s));

			}

		}
	}

	public UUID getSenderID() {
		return senderID;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
	}

}
