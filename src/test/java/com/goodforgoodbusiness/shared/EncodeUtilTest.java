package com.goodforgoodbusiness.shared;

import java.util.Base64;

public class EncodeUtilTest {
//	private static final ObjectMapper CBOR_MAPPER = new ObjectMapper(new CBORFactory()); 
	
	public static void main(String[] args) throws Exception {
		System.out.println(Base64.getEncoder().encodeToString(new byte [] { 1 }));
	}
}
