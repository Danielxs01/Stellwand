package de.danielxs01.stellwand.content.tileentities;

import de.danielxs01.stellwand.network.PacketDispatcher;
import de.danielxs01.stellwand.network.server.RequestTEStorage;
import de.danielxs01.stellwand.proxy.client.ClientProxy;
import de.danielxs01.stellwand.utils.BlockPos;
import de.danielxs01.stellwand.utils.EStellwandSignal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TEBlockSignal extends TileEntity {

	protected static final int TARGETTICK = 10;
	protected int currentTick = 0;
	private boolean initialTick = true;

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

		if (this.initialTick) {
			this.initialTick = false;
			PacketDispatcher.sendToServer(new RequestTEStorage(getBlockPos()));
		}

		currentTick++;
		if (currentTick >= TARGETTICK) {
			currentTick = 0;

			EStellwandSignal s = ClientProxy.signalHandler.getHighestPrio(frequency);

			if (s != this.signal) {
				this.setSignal(s);
			}
		}

	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		readFromNBT(packet.func_148857_g());
	}

}
