package com.goodforgoodbusiness.shared.encode.json;

import java.lang.reflect.Type;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.sparql.util.NodeFactoryExtra;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class NodeSerializer implements JsonSerializer<Node>, JsonDeserializer<Node> {
	public static final String URI_NODE = "uri";
	public static final String LITERAL_NODE = "literal";
	public static final String LITERAL_NODE_VALUE = "value";
	public static final String LITERAL_NODE_TYPE = "type";
	public static final String LITERAL_NODE_LANG = "lang";
	
	@Override
	public JsonElement serialize(Node node, Type type, JsonSerializationContext ctx) {
		if (node.isURI()) {
			var uri = new JsonObject();
			uri.addProperty(URI_NODE, node.getURI());
			return uri;
		}
		
		if (node.isLiteral()) {
			var lit = new JsonObject();
			lit.addProperty(LITERAL_NODE_VALUE, node.getLiteralLexicalForm());
			
			var litType = node.getLiteralDatatypeURI();
			if (litType != null && litType.length() > 0) {
				var obj = new JsonObject();
				obj.addProperty(URI_NODE, node.getLiteralDatatypeURI());
				lit.add(LITERAL_NODE_TYPE, obj);
			}
		    
			var litLang = node.getLiteralLanguage();
		    if (litLang != null && litLang.length() > 0) {
		    	lit.addProperty(LITERAL_NODE_LANG, node.getLiteralLanguage());
		    }
			
			var obj = new JsonObject();
			obj.add(LITERAL_NODE, lit);
			return obj;
		}
		
		throw new IllegalArgumentException("Can't serialize " + node.toString());
	}
	
	@Override
	public Node deserialize(JsonElement json, Type t, JsonDeserializationContext ctx) throws JsonParseException {
		var obj = json.getAsJsonObject();
		
		var uri = obj.get(URI_NODE);
		if (uri != null) {
			return NodeFactory.createURI(uri.getAsString());
		}
		
		var literal = obj.get(LITERAL_NODE);
		if (literal != null && literal.isJsonObject()) {
			var literalObj = literal.getAsJsonObject(); 
			
			var value = literalObj.get(LITERAL_NODE_VALUE);
			var type = literalObj.get(LITERAL_NODE_TYPE);
			var lang = literalObj.get(LITERAL_NODE_LANG);
			
			return NodeFactoryExtra.createLiteralNode(
				value.getAsString(),
				(lang != null ? lang.getAsString() : null),
				(type != null ? type.getAsJsonObject().get("uri").getAsString() : null)
			);
		}

		
		throw new JsonParseException("Node type not parseable: " + obj);
	}
}
