package com.xiaodou.common.util;

/**
 * Utf字符集Util类
 * 
 * @author dan.zhao
 * 
 */
public class UtfUtil {

	public static String utf16To8(String srcData) {
		String out;
		int i, len;
		char c;
		out = "";
		len = srcData.length();
		for (i = 0; i < len; i++) {
			c = srcData.charAt(i);
			if ((c >= 0x0001) && (c <= 0x007F)) {
				out += srcData.charAt(i);
			} else if (c > 0x07FF) {
				out += String.valueOf(0xE0 | ((c >> 12) & 0x0F));
				out += String.valueOf(0x80 | ((c >> 6) & 0x3F));
				out += String.valueOf(0x80 | ((c >> 0) & 0x3F));
			} else {
				out += String.valueOf(0xC0 | ((c >> 6) & 0x1F));
				out += String.valueOf(0x80 | ((c >> 0) & 0x3F));
			}
		}
		return out;
	}

	public static String utf8To16(String srcData) {
		String out;
		int i, len;
		char c;
		char char2, char3;
		out = "";
		len = srcData.length();
		i = 0;
		while (i < len) {
			c = srcData.charAt(i++);
			switch (c >> 4) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				// 0xxxxxxx
				out += srcData.charAt(i - 1);
				break;
			case 12:
			case 13:
				// 110x xxxx 10xx xxxx
				char2 = srcData.charAt(i++);
				out += String.valueOf(((c & 0x1F) << 6) | (char2 & 0x3F));
				break;
			case 14:
				// 1110 xxxx 10xx xxxx 10xx xxxx
				char2 = srcData.charAt(i++);
				char3 = srcData.charAt(i++);
				out += String.valueOf(((c & 0x0F) << 12)
						| ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));
				break;
			}
		}
		return out;
	}
}
