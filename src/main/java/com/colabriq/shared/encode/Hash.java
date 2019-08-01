package com.goodforgoodbusiness.shared.encode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	private static final String HASH_ALGORITHM = "SHA-512";
	
	public static byte [] sha512(byte [] input) {
		try {
			return MessageDigest.getInstance(HASH_ALGORITHM).digest( input );
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(HASH_ALGORITHM + " not supported", e);
		}
	}
	
	/**
	 * Truncate the hash results for various purposes.
	 * Not hard, but encapsulate to stay consistent.
	 */
	public static byte [] truncate(byte [] input, int length) {
		if (length > input.length) {
			throw new IllegalArgumentException("Requested length longer than input length: " + length);
		}
		
		var result = new byte[length];
		System.arraycopy(input, 0, input, 0, result.length);
		return result;
	}
}
