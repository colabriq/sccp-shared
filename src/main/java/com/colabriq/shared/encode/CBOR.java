package com.goodforgoodbusiness.shared.encode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;

public class CBOR {
	private static final ObjectMapper JSON_MAPPER = new ObjectMapper(new JsonFactory());
	private static final ObjectMapper CBOR_MAPPER = new ObjectMapper(new CBORFactory()); 
	
	/**
	 * Convert JSON in to CBOR. 
	 * Re-parsing the JSON is not efficient but stops 
	 * me having to write the world from GSON to Jackson.
	 */
	public static byte [] forJSON(String json) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JsonNode obj = JSON_MAPPER.readTree(json);
			CBOR_MAPPER.writeTree(CBOR_MAPPER.getFactory().createGenerator(baos), obj);
			return baos.toByteArray();
		}
		catch (IOException e) {
			throw new RuntimeException("CBOR encoding failed", e);
		}
	}
	
	/**
	 * Convert object to CBOR of an object.
	 * Object is JSON encoded first.
	 */
	public static byte [] forObject(Object o) {
		return forJSON(JSON.encodeToString(o));
	}
}
