/**
 * @filename			MD5.java
 * @function			Md5����
 * @copyright 			ku6.com
 * @datetime			Jul 2, 2007
 * @lastmodify			Jul 2, 2007
 */
package com.zoo.youshang.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Md5工具类
 * 
 * @version 1.0
 */

public abstract class MD5Util {

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	private static Logger logger=LoggerFactory.getLogger(MD5Util.class);

	public static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return (new StringBuilder()).append(hexDigits[d1])
				.append(hexDigits[d2]).toString();
	}

	/**
	 * md5转码
	 * 
	 * @param origin 
	 * @return
	 */
	public static String encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			logger.error("Md5Encode string "+origin+" error",e);
		}
		return resultString;
	}

}