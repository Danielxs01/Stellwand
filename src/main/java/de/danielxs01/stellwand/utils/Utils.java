package de.danielxs01.stellwand.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;

public class Utils {

	private Utils() {

	}

	public static String getPath(Class<?> c) {
		String path = c.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.split("!/")[1];
		path = path.replaceAll("(.class)$", "").replace("/", ".");
		return path;
	}

	public static void drawLineWithGL(Vec3 a, Vec3 b) {
		GL11.glColor3f(0, 0, 1);
		Tessellator.instance.startDrawing(GL11.GL_LINE_STRIP);
		Tessellator.instance.addVertex(a.xCoord, a.yCoord, a.zCoord);
		Tessellator.instance.addVertex(b.xCoord, b.yCoord, b.zCoord);
		Tessellator.instance.draw();
	}

}
