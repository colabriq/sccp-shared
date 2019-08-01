package com.colabriq.shared.encode;

import java.util.Arrays;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;

public class RDFBinaryTest {
	public static void main(String[] args) {
		var tri1 = new Triple(
			NodeFactory.createLiteral("#ian"),
			NodeFactory.createURI("http://xmlns.com/foaf/0.1/age"),
			NodeFactory.createLiteral("o", XSDDatatype.XSDinteger)
		);
		
		var bytes1 = RDFBinary.encodeTriple(tri1);
		System.out.println(Arrays.toString(bytes1));
		
		System.out.println(RDFBinary.decodeTriple(bytes1));
		
		var tri2 = new Triple(
			NodeFactory.createLiteral("#ian"),
			NodeFactory.createURI("http://xmlns.com/foaf/0.1/age"),
			Node.ANY
		);
		
		var bytes2 = RDFBinary.encodeTriple(tri2);
		System.out.println(Arrays.toString(bytes2));
		
		System.out.println(RDFBinary.decodeTriple(bytes2));
	}
}
