package de.danielxs01.stellwand.utils;

public class Utils {

	public static String getPath(Class<?> c) {
		String path = c.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.split("!/")[1];
		path = path.replaceAll("(.class)$", "").replace("/", ".");
		return path;
	}

}
