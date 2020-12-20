package de.danielxs01.stellwand.content.blocks.stellwand.trackStraight;

import de.danielxs01.stellwand.Constants;
import de.danielxs01.stellwand.utils.EFacing;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTrackEmpty extends Block {

	private IIcon display = null;
	private IIcon side = null;

	public static final String PATH = Constants.MODID + ":";
	public static final String DISPLAYTEXTURE = PATH + "trackStraight/block_track_empty";
	public static final String STANDARDTEXTURE = PATH + "others/filler";

	public BlockTrackEmpty() {
		super(Material.rock);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		display = reg.registerIcon(DISPLAYTEXTURE);
		side = reg.registerIcon(STANDARDTEXTURE);

	}

	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {

		int meta = access.getBlockMetadata(x, y, z);
		EFacing f = EFacing.fromMeta(meta).getOpposite();
		if (side == f.getSide()) {
			return this.display;
		}
		return this.side;

	}

	@Override
	public IIcon getIcon(int side, int meta) {
		EFacing f = EFacing.fromMeta(meta);
		if (side == f.getSide())
			return this.display;
		return this.side;
	}

	@Override
	/***
	 * onBlockPlacedBy(...) Checks the direction the player is facing and sets the
	 * metadata according to this. To get the correct side use EFacing
	 */
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item) {

		int direction = MathHelper.floor_double((double) ((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, direction, 2);

	}

}
