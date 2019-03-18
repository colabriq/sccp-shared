package com.goodforgoodbusiness.shared;

import static org.apache.jena.graph.Node.ANY;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;

public class TripleUtil {
	public static final Triple ANY_ANY_ANY = new Triple(ANY, ANY, ANY);
	
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
}
