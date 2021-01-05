package de.danielxs01.stellwand.content.blocks.stellwand.trackMainSignal;

import de.danielxs01.stellwand.Constants;
import de.danielxs01.stellwand.content.tileentities.TEBlockSignal;
import de.danielxs01.stellwand.utils.BlockSignal;
import de.danielxs01.stellwand.utils.EFacing;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTrackMainSignal extends BlockSignal {

	private IIcon displayOff = null;
	private IIcon side = null;

	private IIcon displaySingleGreen = null;
	private IIcon displaySingleOrange = null;
	private IIcon displaySingleRed = null;

	// @formatter:off
	public final String path;
	public final String standardtexture;
	public final String displayofftexture;

	public final String displaySingleGreenTexture;
	public final String displaySingleOrangeTexture;
	public final String displaySingleRedTexture;
	// @formatter:on

	// LRD, RLD, RLU, LRU
	public BlockTrackMainSignal(String side) {
		super();

		path = Constants.MODID + ":trackMainSignal/block_HSig_" + side + "_";
		standardtexture = Constants.MODID + ":others/filler";
		displayofftexture = path + "dark";

		displaySingleGreenTexture = path + "green";
		displaySingleOrangeTexture = path + "orange";
		displaySingleRedTexture = path + "red";

	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		displayOff = reg.registerIcon(displayofftexture);
		side = reg.registerIcon(standardtexture);

		displaySingleGreen = reg.registerIcon(displaySingleGreenTexture);
		displaySingleOrange = reg.registerIcon(displaySingleOrangeTexture);
		displaySingleRed = reg.registerIcon(displaySingleRedTexture);

	}

	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {

		int meta = access.getBlockMetadata(x, y, z);
		EFacing f = EFacing.fromMeta(meta).getOpposite();
		if (side == f.getSide()) {
			TEBlockSignal signal = getBlockSignal(access, x, y, z);
			if (signal != null) {

				// @formatter:off
				switch(signal.getSignal()) {
					case SINGLE_GREEN:
						return displaySingleGreen;
					case SINGLE_ORANGE:
						return displaySingleOrange;
					case SINGLE_RED:
						return displaySingleRed;
					default:
						return displayOff;
				}
				// @formatter:on

			}
			return displayOff;

		}
		return this.side;

	}

	@Override
	public IIcon getIcon(int side, int meta) {
		EFacing f = EFacing.fromMeta(meta);
		if (side == f.getSide())
			return this.displayOff;
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
