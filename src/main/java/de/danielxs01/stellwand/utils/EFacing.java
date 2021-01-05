package de.danielxs01.stellwand.utils;

import net.minecraft.util.MathHelper;

public enum EFacing {

	NORTH(2, 2), EAST(3, 5), SOUTH(0, 3), WEST(1, 4);

	private int meta;
	private int side;

	private EFacing(int meta, int side) {
		this.meta = meta;
		this.side = side;
	}

	public int getMeta() {
		return meta;
	}

	public int getSide() {
		return side;
	}

	public static EFacing fromMeta(int meta) {
		for (EFacing e : EFacing.values()) {
			if (e.getMeta() == meta)
				return e;
		}
		return null;
	}

	public static EFacing fromSide(int side) {
		for (EFacing e : EFacing.values()) {
			if (e.getSide() == side)
				return e;
		}
		return null;
	}

	public EFacing getOpposite() {
		switch (this) {
		case NORTH:
			return EFacing.SOUTH;
		case SOUTH:
			return EFacing.NORTH;
		case EAST:
			return EFacing.WEST;
		case WEST:
			return EFacing.EAST;
		default:
			return null;
		}
	}

	public static int getDirectionTowards(float rotationYaw) {

		int direction = MathHelper.floor_double((double) ((rotationYaw * 4F) / 360F) + 0.5D) & 3;

		if (direction == 1 || direction == 3)
			direction = direction == 1 ? 3 : 1;

		return direction;
	}

	public static int getDirectionFacing(float rotationYaw) {
		int direction = getDirectionTowards(rotationYaw);
		direction = direction >= 2 ? direction - 2 : direction + 2;

		return direction;
	}

	public static int getDiagonalFacing(float rotationYaw) {

		int direction = MathHelper.floor_double((double) (rotationYaw * 4F) / 360F) & 3;
		direction = direction >= 2 ? direction - 2 : direction + 2;
		return direction;

	}

}
