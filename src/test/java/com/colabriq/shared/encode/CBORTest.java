package com.goodforgoodbusiness.shared.encode;

import java.io.IOException;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;

public class CBORTest {
	public static void main(String[] args) throws Exception {
		
		var foo = NodeFactory.createLiteral("foo");
		
		var f = new CBORFactory();
		var mapper = new ObjectMapper(f);
		
		SimpleModule module = new SimpleModule();
		
		module.addSerializer(Node.class, new JsonSerializer<Node>() {
			@Override
			public void serialize(Node value, JsonGenerator gen, SerializerProvider sp) throws IOException {
				gen.writeNumber(1);
			}
		});
		
		module.addDeserializer(Node.class, new JsonDeserializer<Node>() {
			@Override
			public Node deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JsonProcessingException {
				return NodeFactory.createLiteral("foo");
			}
		});
		
		mapper.registerModule(module);
		
		byte[] data = mapper.writeValueAsBytes(foo);
		var foo2 = mapper.readValue(data, Node.class);
		
		System.out.print(foo2);
	}
}
