package com.dotcink.android.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.text.TextUtils;

/**
 * About security.
 * @author dot.cink
 *
 */
public class Security {

	/**
	 * Check if a String matches a MD5 checksum.
	 * @param string The String who wants check.
	 * @param md5sum The MD5 checksum that will be checked.
	 * @return true if success, otherwise false.
	 */
	public static boolean checkMd5sum(String string, String md5sum){
		if(TextUtils.isEmpty(md5sum))
			return false;
		return md5sum.equals(md5sum(string));
	}

	/**
	 * Get the MD5 (128-bit) checksums of a String.
	 * @param string The string that is consumed.
	 * @return String of MD5 checksums, or null if failed.
	 */
	public static String md5sum(String string){
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(string.getBytes("UTF-8"));
			byte digestBytes[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i=0; i<digestBytes.length; i++)
				hexString.append(Integer.toHexString(0xFF & digestBytes[i]));
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
