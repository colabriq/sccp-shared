package com.colabriq.shared.encode.json;

import java.lang.reflect.Type;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class TripleSerializer implements JsonSerializer<Triple>, JsonDeserializer<Triple> {
	public static final String SUBJECT_KEY = "s";
	public static final String PREDICATE_KEY = "p";
	public static final String OBJECT_KEY = "o";
	
	@Override
	public JsonElement serialize(Triple trup, Type type, JsonSerializationContext ctx) {
		var obj = new JsonObject();
		
		if ((trup.getSubject() != null) && !(trup.getSubject().equals(Node.ANY))) {
			obj.add(SUBJECT_KEY, ctx.serialize(trup.getSubject()));
		}
		
		if ((trup.getPredicate() != null) && !(trup.getPredicate().equals(Node.ANY))) {
			obj.add(PREDICATE_KEY, ctx.serialize(trup.getPredicate()));
		}
		
		if ((trup.getObject() != null) && !(trup.getObject().equals(Node.ANY))) {
			obj.add(OBJECT_KEY, ctx.serialize(trup.getObject()));
		}
		
		return obj;
	}
	
	@Override
	public Triple deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) {
		if (json.isJsonObject()) {
			JsonObject obj = json.getAsJsonObject();
			
			var subject = obj.get(SUBJECT_KEY);
			var predicate = obj.get(PREDICATE_KEY);
			var object = obj.get(OBJECT_KEY);
			
			return new Triple(
				(subject != null) ? ctx.deserialize(subject, Node.class) : Node.ANY,
				(predicate != null) ? ctx.deserialize(predicate, Node.class) : Node.ANY,
				(object != null) ? ctx.deserialize(object, Node.class) : Node.ANY
			);
		}
		else {
			throw new JsonParseException("Not a JSON object");
		}
	}
}
