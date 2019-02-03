package com.goodforgoodbusiness.shared;

import static org.apache.jena.graph.Node.ANY;

import java.util.Optional;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;

public class TripleUtil {
	public static final Triple ANYANYANY = new Triple(ANY, ANY, ANY);
	
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
	
	public static boolean isConcrete(Triple triple) {
		if (triple.getSubject() == null || triple.getSubject().equals(ANY)) {
			return false;
		}
		
		if (triple.getPredicate() == null || triple.getPredicate().equals(ANY)) {
			return false;
		}
		
		if (triple.getObject() == null || triple.getObject().equals(ANY)) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isMatch(Triple pattern, Triple triple) {
		// would this triple have been caught by this iterator?
		if (pattern.getSubject().equals(Node.ANY) || pattern.getSubject().equals(triple.getSubject())) {
			if (pattern.getPredicate().equals(Node.ANY) || pattern.getPredicate().equals(triple.getPredicate())) {
				if (pattern.getObject().equals(Node.ANY) || pattern.getObject().equals(triple.getObject())) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Create all possible pattern triples that match a concrete Triple
	 */
	public static Triple [] makePatterns(Triple t) {
		if (!isConcrete(t)) {
			throw new IllegalArgumentException("Triple must be concrete");
		}
		
		var sub = t.getSubject();
		var pre = t.getPredicate();
		var obj = t.getObject();
		
		return new Triple[] {
			// pick 3
			new Triple(sub, pre, obj),
			
			// pick 2
			new Triple(sub, pre, ANY),
			new Triple(sub, ANY, obj),
			new Triple(ANY, pre, obj),
			
			// pick 1
			new Triple(sub, ANY, ANY),
			new Triple(ANY, pre, ANY),
			new Triple(ANY, ANY, obj),
			
			// pick 0
			new Triple(ANY, ANY, ANY),
		};
	}
}
