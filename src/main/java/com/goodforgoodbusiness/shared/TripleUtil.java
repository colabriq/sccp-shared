package com.goodforgoodbusiness.shared;

import static org.apache.jena.graph.Node.ANY;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;

public class TripleUtil {
	public static final Triple ANY_ANY_ANY = new Triple(ANY, ANY, ANY);
	
	/**
	 * Does a particular Node represent 'ANY' in a match?
	 */
	public static boolean isAny(Node node) {
		return (node == Node.ANY) || (node == null);
	}
	
	/**
	 * Is the triple fully concrete?
	 */
	public static boolean isConcrete(Triple triple) {
		return !isAny(triple.getSubject()) && !isAny(triple.getPredicate()) && !isAny(triple.getObject());
	}
	
	/**
	 * Does a concrete triple match a particular pattern?
	 */
	public static boolean isMatch(Triple pattern, Triple triple) {
		// would this triple have been caught by this iterator?
		if (isAny(pattern.getSubject()) || pattern.getSubject().equals(triple.getSubject())) {
			if (isAny(pattern.getPredicate()) || pattern.getPredicate().equals(triple.getPredicate())) {
				if (isAny(pattern.getObject()) || pattern.getObject().equals(triple.getObject())) {
					return true;
				}
			}
		}
		
		return false;
	}
}
