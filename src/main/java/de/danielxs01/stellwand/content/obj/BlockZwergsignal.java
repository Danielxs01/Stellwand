package de.danielxs01.stellwand.content.obj;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.danielxs01.stellwand.Constants;
import de.danielxs01.stellwand.Stellwand;
import de.danielxs01.stellwand.content.tabs.CustomTabs;
import de.danielxs01.stellwand.proxy.client.ClientProxy;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockZwergsignal extends BlockContainer {

	public static BlockZwergsignal instance;

	protected BlockZwergsignal() {
		super(Material.circuits);
		setBlockTextureName(Constants.MODID + ":obj/ch_zwergsignal_head.obj");
		setBlockName("zwergsignal");
		setCreativeTab(CustomTabs.stellwandTab);
		setBlockBounds(0.2f, 0f, 0.2f, 0.8f, 1f, 0.8f);
		GameRegistry.registerBlock(this, ItemZwergsignal.class, "zwergsignal");

	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {

		int facing = MathHelper.floor_double((double) ((entity.rotationYaw * 16F) / 360F) + 0.5D) & 15;

		if (facing != 0 && facing != 8)
			facing = 16 - facing;

		world.setBlockMetadataWithNotify(x, y, z, facing, 2);

	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int oldMeta, float hitX,
			float hitY, float hitZ) {
		int meta = world.getBlockMetadata(x, y, z);
		player.addChatMessage(new ChatComponentText("Meta: " + meta));
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TEZwergsignal();
	}

	@SideOnly(Side.CLIENT)
	public static void register() {
		instance = new BlockZwergsignal();
		GameRegistry.registerTileEntity(TEZwergsignal.class, Constants.MODID + "_TEZwergsignal");
		if (Stellwand.commonProxy instanceof ClientProxy)
			ClientRegistry.bindTileEntitySpecialRenderer(TEZwergsignal.class, new TESRZwergsignal());
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z) {
		return null;
	}

}
