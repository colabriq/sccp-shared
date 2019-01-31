package com.goodforgoodbusiness.shared;

import java.util.Optional;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;

public class TripleUtil {
	public static Optional<String> valueOf(Node node) {
		if (node == null || node.equals(Node.ANY)) {
			return Optional.empty();
		}
		else if (node.isURI()) {
			return Optional.of(node.getURI());
		}
		else if (node.isLiteral()) {
			return Optional.of(node.getLiteralValue().toString());
		}
		else {
			throw new IllegalArgumentException(node.toString());
		}
	}
	
	public static String [] toValueArray(Triple triple) {
		return new String [] {
			valueOf(triple.getSubject()).orElse(null),
			valueOf(triple.getPredicate()).orElse(null),
			valueOf(triple.getObject()).orElse(null)
		};
	}
}
