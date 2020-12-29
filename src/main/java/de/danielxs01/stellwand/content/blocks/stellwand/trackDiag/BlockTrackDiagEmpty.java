package de.danielxs01.stellwand.content.blocks.stellwand.trackDiag;

import com.ibm.icu.text.MessageFormat;

import de.danielxs01.stellwand.Constants;
import de.danielxs01.stellwand.Stellwand;
import de.danielxs01.stellwand.utils.EFacing;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTrackDiagEmpty extends Block {

	private IIcon[] display = new IIcon[4];
	private IIcon side = null;

	public static final String[] DIRECTIONS = new String[] { "ul", "ur", "dl", "dr" };

	public static final String PATH = Constants.MODID + ":";
	public static final String DISPLAYTEXTURE = PATH + "trackDiag/block_track_diag_{0}_empty";
	public static final String STANDARDTEXTURE = PATH + "others/filler";

	public BlockTrackDiagEmpty() {
		super(Material.rock);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		for (int i = 0; i < DIRECTIONS.length; i++) {
			String texture = MessageFormat.format(DISPLAYTEXTURE, DIRECTIONS[i]);
			display[i] = reg.registerIcon(texture);
		}

		side = reg.registerIcon(STANDARDTEXTURE);
	}

	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {

		int meta = access.getBlockMetadata(x, y, z);

		if (side == 1) {
			return this.display[0];
		}
		return this.side;

	}

	@Override
	public IIcon getIcon(int side, int meta) {
		EFacing f = EFacing.fromMeta(meta);
		if (side == f.getSide())
			return this.display[0];
		return this.side;
	}

	@Override
	/***
	 * onBlockPlacedBy(...) Checks the direction the player is facing and sets the
	 * metadata according to this. To get the correct side use EFacing
	 */
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item) {

		int meta = world.getBlockMetadata(x, y, z);
		String val = String.format("%02d", meta);
		char[] arr = val.toCharArray();

		Stellwand.logger.warn("Val: " + val);

		boolean hitX = arr[0] == '1';
		boolean hitY = arr[1] == '1';
		boolean hitZ = arr[2] == '1';

		EntityPlayerMP player = (EntityPlayerMP) entity;
		String text = "hitX: " + hitX + ", hitY: " + hitY + ", hitZ: " + hitZ;
		player.addChatComponentMessage(new ChatComponentText(text));

		int dir = MathHelper.floor_double((double) ((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, dir, 2);

		Stellwand.logger.warn("onBlockPlacedBy");

	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {

		String val = "";
		val += hitX >= 0.5 ? "1" : "0";
		val += hitY >= 0.5 ? "1" : "0";
		val += hitZ >= 0.5 ? "1" : "0";
		meta = Byte.valueOf(val);

		world.setBlockMetadataWithNotify(x, y, z, meta, 0);

		return super.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, meta);
	}

}