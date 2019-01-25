package com.goodforgoodbusiness.shared.encode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	private static final String HASH_ALGORITHM = "SHA-256";
	
	public static byte [] sha256(byte [] input) {
		try {
			return MessageDigest.getInstance(HASH_ALGORITHM).digest( input );
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(HASH_ALGORITHM + " not supported", e);
		}
	}
}
