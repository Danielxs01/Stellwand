package de.danielxs01.stellwand.content.tileentities;

import java.util.UUID;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import de.danielxs01.stellwand.Stellwand;
import de.danielxs01.stellwand.network.PacketDispatcher;
import de.danielxs01.stellwand.network.server.RequestTEStorage;
import de.danielxs01.stellwand.proxy.client.ClientProxy;
import de.danielxs01.stellwand.utils.BlockPos;
import de.danielxs01.stellwand.utils.EStellwandSignal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TEBlockSender extends TileEntity {

	// Client data
	private static final int TARGETTICK = 10;
	private static final int CASUALUPDATETICK = 20;
	private static final int MAXTICK = 20;
	private int currentTick = 0;
	private boolean initialTick = true;
	private boolean initialUpdate = true;

	// General data
	private UUID senderID;
	private String name;
	private int frequency;
	private EStellwandSignal signal;
	private boolean isPowered = false;

	public TEBlockSender() {
		this.senderID = UUID.randomUUID();
		this.frequency = 0;
		this.name = "";
		this.signal = EStellwandSignal.OFF;
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {

		compound.setInteger("frequency", frequency);
		compound.setInteger("signalID", signal.getID());
		compound.setLong("senderIDmsb", senderID.getMostSignificantBits());
		compound.setLong("senderIDlsb", senderID.getLeastSignificantBits());
		compound.setString("name", name);

		super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {

		this.frequency = compound.getInteger("frequency");
		this.signal = EStellwandSignal.fromID(compound.getInteger("signalID"));
		long msb = compound.getLong("senderIDmsb");
		long lsb = compound.getLong("senderIDlsb");
		this.senderID = new UUID(msb, lsb);
		this.name = compound.hasKey("name") ? compound.getString("name") : "";

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

	public void setName(String name) {
		this.name = name;
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

	public String getName() {
		return name;
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

		// Client
		if (initialTick) {
			initialTick = false;
			PacketDispatcher.sendToServer(new RequestTEStorage(getBlockPos()));
		}

		currentTick++;
		if (currentTick >= TARGETTICK) {

			boolean currentlyPowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);

			if (currentlyPowered != isPowered) {
				isPowered = currentlyPowered;
				update();
			}

		}

		// Sollte Sender die auf Grund von Distanz ausgeschaltet wurden wieder
		// aktivieren.
		if (currentTick >= CASUALUPDATETICK) {
			boolean currentlyPowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
			this.isPowered = currentlyPowered;
			update();
		}

		if (currentTick >= MAXTICK) {
			currentTick = 0;
		}

	}

	public void update() {
		initialUpdate = false;
		EStellwandSignal s = isPowered ? signal : EStellwandSignal.OFF;
		ClientProxy.signalHandler.change(senderID, name, frequency, s);
	}

	public boolean isInitialUpdate() {
		return initialUpdate;
	}

	public UUID getSenderID() {
		return senderID;
	}

	@Override
	public S35PacketUpdateTileEntity getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbttagcompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		if (pkt == null) {
			return;
		}
		readFromNBT(pkt.func_148857_g());
		markDirty();
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 1d, yCoord - 1d, zCoord - 1d, xCoord + 1d, yCoord, zCoord + 1d);
	}

	@Override
	public void func_145828_a(@Nullable CrashReportCategory report) {
		if (report == null) {

			if (worldObj.isRemote) {

				// GL ENABLE
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				//

				GL11.glColor3f(1.0f, 1.0f, 1.0f);
				GL11.glLineWidth(5);

				Tessellator.instance.startDrawing(GL11.GL_LINE_STRIP);

				Tessellator.instance.addVertex(.5d, .5d, .5d);
				Tessellator.instance.addVertex(2.5d, 2.5d, 2.5d);

				Tessellator.instance.draw();

				// GL DISABLE
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				//

				Stellwand.logger.info("DRAW");

			}

		} else {
			super.func_145828_a(report);
		}
	}

}
