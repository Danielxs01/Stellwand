package de.danielxs01.stellwand.content.obj;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import de.danielxs01.stellwand.Constants;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class TESRZwergsignal extends TileEntitySpecialRenderer {

	private IModelCustom model = AdvancedModelLoader
			.loadModel(new ResourceLocation(Constants.MODID, "obj/ch_zwergsignal_head.obj"));

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float arg) {

		if (entity instanceof TEZwergsignal) {

			TEZwergsignal signal = (TEZwergsignal) entity;
			bindTexture(signal.getTexture());

			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float) x + 0.5f, (float) y + 0.75f, (float) z + 0.5f);
			GL11.glScalef(1.5F, 1.5F, 1.5F);
			GL11.glRotatef(22.5f * entity.getBlockMetadata(), 0.0F, 1.0F, 0.0F);

			model.renderAll();
			GL11.glPopMatrix();

		}

	}

}
