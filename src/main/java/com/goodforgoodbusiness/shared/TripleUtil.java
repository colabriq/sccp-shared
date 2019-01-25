package com.goodforgoodbusiness.shared;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;

public class TripleUtil {
	public static String valueOf(Node node) {
		if (node == null || node.equals(Node.ANY)) {
			return null;
		}
		else if (node.isURI()) {
			return node.getURI();
		}
		else if (node.isLiteral()) {
			return node.getLiteralValue().toString();
		}
		else {
			throw new IllegalArgumentException(node.toString());
		}
	}
	
	public static String [] toValueArray(Triple triple) {
		return new String [] {
			valueOf(triple.getSubject()),
			valueOf(triple.getPredicate()),
			valueOf(triple.getObject())
		};
	}
}
