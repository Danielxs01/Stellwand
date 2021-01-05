package de.danielxs01.stellwand.content.obj;

import java.util.Random;

import de.danielxs01.stellwand.Constants;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TEZwergsignal extends TileEntity {

	public int version = 0;
	private ResourceLocation[] textures = new ResourceLocation[4];

	public TEZwergsignal() {

		textures[0] = new ResourceLocation(Constants.MODID, "obj/Zwergsignal_off.png");
		textures[1] = new ResourceLocation(Constants.MODID, "obj/Zwergsignal_Halt.png");
		textures[2] = new ResourceLocation(Constants.MODID, "obj/Zwergsignal_Fahrt.png");
		textures[3] = new ResourceLocation(Constants.MODID, "obj/Zwergsignal_FmV.png");

		version = getIntBetween(0, 3);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("version", version);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("version"))
			version = nbt.getInteger("version");
		else
			version = getIntBetween(0, 3);
	}

	public int getIntBetween(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}

	public ResourceLocation getTexture() {
		return textures[version];
	}

}
