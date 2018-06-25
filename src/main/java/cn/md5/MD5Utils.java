package cn.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		String s = toMD5("test_sssssssssssssssssssssssssssssssssssssssssssssss");
		System.out.println(s);
	}

	private static String toMD5(String string) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] results = digest.digest();
		System.out.println("获取的字节数组长度：" + results.length);
		String md5Str = toHex(results);
		return md5Str;
	}

	/**
	 * 转为16进制
	 * @param results
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static String toHex(byte[] results) throws NoSuchAlgorithmException {
		if (results == null) {
			return null;
		}

		StringBuilder s = new StringBuilder();
		for (int i = 0; i < results.length; i++) {
			// 一个字节等于8bit，而16位的数字，每个数占用4bit
			int h = (results[i] >> 4) & 0x0f;
			int low = results[i] & 0x0f;
			s.append(Character.forDigit(h, 16)).append(Character.forDigit(low, 16));
		}
		return s.toString();
	}

}
