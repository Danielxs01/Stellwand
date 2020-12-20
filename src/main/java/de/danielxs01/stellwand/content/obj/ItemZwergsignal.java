package de.danielxs01.stellwand.content.obj;

import org.lwjgl.opengl.GL11;

import de.danielxs01.stellwand.Constants;
import de.danielxs01.stellwand.content.tabs.CustomTabs;
import de.danielxs01.stellwand.proxy.client.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemZwergsignal extends ItemBlock {

	public static ItemZwergsignal instance;

	@SuppressWarnings("java:S3010")
	public ItemZwergsignal(Block b) {
		super(b);
		setCreativeTab(CustomTabs.stellwandTab);
		setFull3D();
		setTextureName(Constants.MODID + ":obj/ch_zwergsignal_head.obj");
		instance = this;
	}

	public static class ItemZwergsignalRenderer implements IItemRenderer {

		public static final ResourceLocation texture = new ResourceLocation(Constants.MODID, "obj/Zwergsignal_off.png");

		@Override
		public boolean handleRenderType(ItemStack is, ItemRenderType type) {
			return true;
		}

		@Override
		public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack is, ItemRendererHelper helper) {
			return true;
		}

		@Override
		public void renderItem(ItemRenderType type, ItemStack is, Object... data) {
			GL11.glPushMatrix();

			if (type == ItemRenderType.INVENTORY) {
				GL11.glTranslatef(0.5f, 0.5f, 0.5f);
				GL11.glRotatef(180f, 0.0F, 1.0F, 0.0F);
			} else if (type == ItemRenderType.EQUIPPED) {
				GL11.glTranslatef(0.5f, 0.5f, 0.5f);
				GL11.glRotatef(45f, 0.0F, 1.0F, 0.0F);
			} else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
				GL11.glTranslatef(0.5f, 1.0f, 0.5f);
				GL11.glRotatef(90f, 0.0F, 1.0F, 0.0F);
			} else if (type == ItemRenderType.ENTITY) {
				GL11.glTranslatef(0.0f, 0.5f, 0.0f);
			}

			GL11.glScalef(1.5F, 1.5F, 1.5F);

			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			GL11.glCallList(ClientProxy.displayList[0]);
			GL11.glPopMatrix();
		}

	}

}
