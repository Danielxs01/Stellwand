package net.landofrails.stellwand.content.blocks.stellwand.switches;

import net.landofrails.stellwand.Constants;
import net.landofrails.stellwand.content.blocks.stellwand.tileentity.TEBlockSignal;
import net.landofrails.stellwand.content.blocks.stellwand.utils.BlockSignal;
import net.landofrails.stellwand.content.blocks.stellwand.utils.IStellwandBlock;
import net.landofrails.stellwand.content.util.EFacing;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTrackSwitch extends BlockSignal implements IStellwandBlock {

	private IIcon displayOff = null;
	private IIcon side = null;

	private IIcon displaySingleWhite = null;
	private IIcon displayDoubleWhite = null;
	private IIcon displayBranchWhite = null;

	private IIcon displayDoubleRed = null;
	private IIcon displayBranchRed = null;

	// @formatter:off
	public String path;
	public String standardtexture;
	public String displayofftexture;

	public String displaysinglewhitetexture;
	public String displaydoublewhitetexture;
	public String displaybranchwhitetexture;


	public final String displaydoubleredtexture;
	public final String displaybranchredtexture;
	// @formatter:on

	// LRD, RLD, RLU, LRU
	public BlockTrackSwitch(String name) {
		super();

		path = Constants.MODID + ":" + "stellwand/switch" + name.toUpperCase() + "/block_track_switch_"
				+ name.toLowerCase() + "_";
		standardtexture = Constants.MODID + ":stellwand/filler";
		displayofftexture = path + "dark";

		displaysinglewhitetexture = path + "white_1";
		displaydoublewhitetexture = path + "white_2";
		displaybranchwhitetexture = path + "white_3";

		displaydoubleredtexture = path + "red_1";
		displaybranchredtexture = path + "red_2";
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		displayOff = reg.registerIcon(displayofftexture);
		side = reg.registerIcon(standardtexture);

		displaySingleWhite = reg.registerIcon(displaysinglewhitetexture);
		displayDoubleWhite = reg.registerIcon(displaydoublewhitetexture);
		displayBranchWhite = reg.registerIcon(displaybranchwhitetexture);

		displayDoubleRed = reg.registerIcon(displaydoubleredtexture);
		displayBranchRed = reg.registerIcon(displaybranchredtexture);
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