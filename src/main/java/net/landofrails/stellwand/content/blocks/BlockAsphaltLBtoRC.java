package net.landofrails.stellwand.content.blocks;

import net.landofrails.stellwand.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockAsphaltLBtoRC extends Block {

	private IIcon[] tops = new IIcon[4];
	private IIcon side = null;

	public static final String FULLTEXTURENAME = Constants.MODID + ":" + "asphaltLBtoRC/asphalt";

	private static final int TOP = 1;

	protected BlockAsphaltLBtoRC() {
		super(Material.ground);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		side = reg.registerIcon(FULLTEXTURENAME);

		for (int i = 1; i <= 4; i++)
			tops[i - 1] = reg.registerIcon(FULLTEXTURENAME + "_" + i);

	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == TOP)
			return tops[meta];
		return this.side;
	}

	@Override
	/***
	 * onBlockPlacedBy(...) Checks the direction the player is facing and sets the
	 * metadata according to this
	 */
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item) {

		int direction = MathHelper.floor_double((double) ((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, direction, 2);

	}

}
