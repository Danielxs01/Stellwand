package de.danielxs01.stellwand.content.connection;

import org.lwjgl.opengl.GL11;

import de.danielxs01.stellwand.content.tileentities.TEBlockSender;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TESRBlockSender extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float unknownFloat) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		((TEBlockSender) tileEntity).func_145828_a(null);

		GL11.glPopMatrix();
	}

	@Override
	protected void bindTexture(ResourceLocation rl) {
		// Dont need no texture.
	}

}
