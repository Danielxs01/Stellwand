package de.danielxs01.stellwand.content.blocks.trackSwitch;

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

public class BlockTrackSwitch extends BlockSignal {

	private IIcon displayOff = null;
	private IIcon side = null;

	private IIcon displaySingleWhite = null;
	private IIcon displayDoubleWhite = null;
	private IIcon displayBranchWhite = null;

	private IIcon displayDoubleRed = null;
	private IIcon displayBranchRed = null;

	// @formatter:off
	public final String path;
	public final String standardtexture;
	public final String displayofftexture;

	public final String DISPLAYSINGLEWHITETEXTURE;
	public final String DISPLAYDOUBLEWHITETEXTURE;
	public final String DISPLAYBRANCHWHITETEXTURE;


	public final String DISPLAYDOUBLEREDTEXTURE;
	public final String DISPLAYBRANCHREDTEXTURE;
	// @formatter:on

	// LRD, RLD, RLU, LRU
	public BlockTrackSwitch(String name) {
		super();

		name = name.toUpperCase();
		path = Constants.MODID + ":trackSwitch/switch" + name + "/block_track_switch_" + name.toLowerCase() + "_";
		standardtexture = Constants.MODID + ":others/filler";
		displayofftexture = path + "dark";

		DISPLAYSINGLEWHITETEXTURE = path + "white_1";
		DISPLAYDOUBLEWHITETEXTURE = path + "white_2";
		DISPLAYBRANCHWHITETEXTURE = path + "white_3";

		DISPLAYDOUBLEREDTEXTURE = path + "red_1";
		DISPLAYBRANCHREDTEXTURE = path + "red_2";
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		displayOff = reg.registerIcon(displayofftexture);
		side = reg.registerIcon(standardtexture);

		displaySingleWhite = reg.registerIcon(DISPLAYSINGLEWHITETEXTURE);
		displayDoubleWhite = reg.registerIcon(DISPLAYDOUBLEWHITETEXTURE);
		displayBranchWhite = reg.registerIcon(DISPLAYBRANCHWHITETEXTURE);

		displayDoubleRed = reg.registerIcon(DISPLAYDOUBLEREDTEXTURE);
		displayBranchRed = reg.registerIcon(DISPLAYBRANCHREDTEXTURE);
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
						return displaySingleWhite;
					case DOUBLE_WHITE:
						return displayDoubleWhite;
					case BRANCH_WHITE:
						return displayBranchWhite;
					case DOUBLE_RED:
						return displayDoubleRed;
					case BRANCH_RED:
						return displayBranchRed;
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