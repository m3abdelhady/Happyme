package com.happy.me.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Utils {
	private Utils() {
		super();
	}
	
	public static String getUniqueId() {
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString().substring(0, 8);
		return id;
	}
	
	public static String encryptPassword(String password) {
		StringBuilder hash = new StringBuilder();

		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] hashedBytes = sha.digest(password.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'a', 'b', 'c', 'd', 'e', 'f' };
			for (int idx = 0; idx < hashedBytes.length; ++idx) {
				byte b = hashedBytes[idx];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch (NoSuchAlgorithmException e) {
			// handle error here.
		}

		return hash.toString();
	}
	
	public static String getUniqueNumber() {
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString().substring(0, 8);
		return id;
	}
	
}
