package de.danielxs01.stellwand.proxy;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.danielxs01.stellwand.Stellwand;
import de.danielxs01.stellwand.content.blocks.stellwand.others.BlockSender;
import de.danielxs01.stellwand.content.gui.stellwand.GuiBlockName;
import de.danielxs01.stellwand.content.tileentities.TEBlockSender;
import de.danielxs01.stellwand.content.tileentities.TEBlockSignal;
import de.danielxs01.stellwand.proxy.client.ClientProxy;
import de.danielxs01.stellwand.utils.BlockSignal;
import de.danielxs01.stellwand.utils.EFacing;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class StellwandEventHandler {

	@SubscribeEvent
	public void onEvent(EntityJoinWorldEvent event) {
		if ((event.entity instanceof EntityPlayer) && event.world.isRemote) {
			Stellwand.logger.info("EntityJoinWorldEvent");
			ClientProxy.signalHandler.clear();
		}
	}

	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent.Post event) {
		if (!(Stellwand.commonProxy instanceof ClientProxy))
			return;
		if (event.type != ElementType.EXPERIENCE)
			return;

		WorldClient world = Minecraft.getMinecraft().theWorld;
		MovingObjectPosition mop = Minecraft.getMinecraft().renderViewEntity.rayTrace(200, 1.0F);
		if (mop != null) {
			int blockHitSide = mop.sideHit;
			int x = mop.blockX;
			int y = mop.blockY;
			int z = mop.blockZ;

			blockHit(world, blockHitSide, x, y, z);
		}

	}

	private void blockHit(World world, int blockHitSide, int x, int y, int z) {
		Block blockLookingAt = world.getBlock(x, y, z);

		Vec3 vec1 = Vec3.createVectorHelper(x, y, z);
		Vec3 vec2 = Minecraft.getMinecraft().thePlayer.getPosition(0f);
		double distance = vec1.distanceTo(vec2);
		final double maxdistance = 5;

		if (distance > maxdistance)
			return;

		if (blockLookingAt instanceof BlockSignal) {

			int meta = world.getBlockMetadata(x, y, z);
			EFacing f = EFacing.fromMeta(meta).getOpposite();
			if (blockHitSide == f.getSide()) {
				TileEntity te = world.getTileEntity(x, y, z);
				if (te instanceof TEBlockSignal) {
					TEBlockSignal teBlockSignal = (TEBlockSignal) te;
					// @formatter:off
					String[] arr = new String[] { 
							"B: " + blockLookingAt.getLocalizedName(),
							"F: " + teBlockSignal.getFrequency() 
					};
					// @formatter:on
					new GuiBlockName(Minecraft.getMinecraft(), arr);
				}
			}

		} else if (blockLookingAt instanceof BlockSender) {

			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TEBlockSender) {
				TEBlockSender teBlockSender = (TEBlockSender) te;
				// @formatter:off
				String[] arr = new String[] { 
						"B: " + blockLookingAt.getLocalizedName(),
						"F: " + teBlockSender.getFrequency(),
						"S: " + teBlockSender.getSignal().name()
				};
				// @formatter:on
				new GuiBlockName(Minecraft.getMinecraft(), arr);
			}

		}
	}

}
