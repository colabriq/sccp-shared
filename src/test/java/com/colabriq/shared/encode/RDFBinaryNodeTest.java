package com.colabriq.shared.encode;

import java.util.Arrays;

import org.apache.jena.graph.NodeFactory;

public class RDFBinaryNodeTest {
	public static void main(String[] args) throws Exception {
//		NodeFactory.createLiteral("#ian");
//		
//		NodeFactory.createURI("http://xmlns.com/foaf/0.1/age");
//		
//		NodeFactory.createLiteral("o", XSDDatatype.XSDinteger);
		
		var node = NodeFactory.createLiteral("#ian");
		
		var bytes = RDFBinary.encodeNode(node);
		System.out.println(Arrays.toString(bytes));
		
		System.out.println(RDFBinary.decodeNode(bytes));
	}
}
