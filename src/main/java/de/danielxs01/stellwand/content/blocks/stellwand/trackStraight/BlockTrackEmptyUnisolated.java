package de.danielxs01.stellwand.content.blocks.stellwand.trackStraight;

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

public class BlockTrackEmptyUnisolated extends BlockSignal {

	private IIcon displayOff = null;
	private IIcon displayWhite = null;
	private IIcon side = null;

	public static final String PATH = Constants.MODID + ":" + "";
	public static final String DISPLAYOFFTEXTURE = PATH + "trackStraight/block_track_non_iso_empty_dark";
	public static final String DISPLAYWHITETEXTURE = PATH + "trackStraight/block_track_non_iso_empty_white";
	public static final String STANDARDTEXTURE = PATH + "others/filler";

	public BlockTrackEmptyUnisolated() {
		super();
		this.setTickRandomly(true);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		displayOff = reg.registerIcon(DISPLAYOFFTEXTURE);
		displayWhite = reg.registerIcon(DISPLAYWHITETEXTURE);
		side = reg.registerIcon(STANDARDTEXTURE);
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
					case SINGLE_WHITE:
					case DOUBLE_WHITE:
					case BRANCH_WHITE:
						return displayWhite;
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