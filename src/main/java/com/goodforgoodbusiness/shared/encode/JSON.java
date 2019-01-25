package com.goodforgoodbusiness.shared.encode;

import java.lang.reflect.Type;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;

import com.goodforgoodbusiness.shared.encode.json.NodeSerializer;
import com.goodforgoodbusiness.shared.encode.json.TripleSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class JSON {
	private static final Gson ENCODER = 
		new GsonBuilder()
			.setPrettyPrinting()
			.excludeFieldsWithoutExposeAnnotation()
			.serializeNulls()
			.disableHtmlEscaping()
			.registerTypeHierarchyAdapter(Node.class, new NodeSerializer())
			.registerTypeHierarchyAdapter(Triple.class, new TripleSerializer())
			.create();
	
	private static final Gson DECODER = 
		new GsonBuilder()
			.setPrettyPrinting()
			.disableHtmlEscaping()
			.registerTypeAdapter(Node.class, new NodeSerializer())
			.registerTypeAdapter(Triple.class, new TripleSerializer())
			.create();
	
	
	public static JsonElement encode(Object o) {
		return ENCODER.toJsonTree(o);
	}
	
	public static String encodeToString(Object o) {
		return ENCODER.toJson(ENCODER.toJsonTree(o));
	}
	
	public static <T> T decode(String json, Type type) {
		return DECODER.fromJson(json, type);
	}
	
	public static <T> T decode(String json, Class<T> type) {
		return DECODER.fromJson(json, type);
	}
}
