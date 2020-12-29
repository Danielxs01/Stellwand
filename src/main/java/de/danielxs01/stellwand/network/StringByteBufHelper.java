package de.danielxs01.stellwand.network;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;

public class StringByteBufHelper {

	private StringByteBufHelper() {

	}

	private static final Charset DEFAULTCHARSET = StandardCharsets.UTF_8;

	public static void writeString(ByteBuf buf, String text) {
		byte[] bytes = text.getBytes(DEFAULTCHARSET);
		buf.writeInt(bytes.length);
		for (byte b : bytes)
			buf.writeByte(b);
	}

	public static String readString(ByteBuf buf) {
		int length = buf.readInt();
		byte[] bytes = new byte[length];
		for (int i = 0; i < length; i++)
			bytes[i] = buf.readByte();
		return new String(bytes, DEFAULTCHARSET);
	}

}
