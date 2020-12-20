package de.danielxs01.stellwand.proxy;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.danielxs01.stellwand.Stellwand;
import de.danielxs01.stellwand.content.gui.stellwand.GuiBlockName;
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

			Block blockLookingAt = world.getBlock(x, y, z);
			if (blockLookingAt instanceof BlockSignal) {
				int meta = world.getBlockMetadata(x, y, z);
				EFacing f = EFacing.fromMeta(meta).getOpposite();
				if (blockHitSide == f.getSide()) {
					TileEntity te = world.getTileEntity(x, y, z);
					if (te instanceof TEBlockSignal) {
						TEBlockSignal teBlockSignal = (TEBlockSignal) te;
						new GuiBlockName(Minecraft.getMinecraft(), "abc");
					}
				}
			}
		}

	}

}
