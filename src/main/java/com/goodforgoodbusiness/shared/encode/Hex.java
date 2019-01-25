package com.goodforgoodbusiness.shared.encode;

public class Hex {
	public static String encode(byte [] input) {
		StringBuilder sb = new StringBuilder();
		
		for (byte b : input) {
			sb.append(String.format("%02x", b & 0xff));
		}
		
		return sb.toString();
	}
	
	public static byte [] decode(String input) {
		byte [] result = new byte[input.length() >> 1];
		
		for (int i = 0; i < result.length; i++) 
			result[i] = (byte)Integer.parseInt(input, i << 1, (i << 1) + 2, 16);
		
		return result;
	}
	
	public static void main(String[] args) {
		String hex = encode(new byte [] { -1, -128, 0, 1, 10, 127 });
		System.out.println(hex);
		
		for (byte b : decode(hex)) {
			System.out.print(b + " ");
		}
	}
}
