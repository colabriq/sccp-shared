package com.goodforgoodbusiness.shared;

import static java.util.Optional.empty;
import static org.apache.jena.graph.Node.ANY;

import java.util.Optional;
import java.util.stream.Stream;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;

public class TripleUtil {
	public static final Triple ANY_ANY_ANY = new Triple(ANY, ANY, ANY);
	
	/**
	 * Does a particular Node represent 'ANY' in a match?
	 */
	public static boolean isConcrete(Node node) {
		return (node != Node.ANY) && (node != null);
	}
	
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
	
	/**
	 * Create possible match combinations
	 * This includes (_, _, _) and (_, p, _)
	 */
	public static Stream<Triple> allCombinations(Triple triple) {
		return Stream.concat(
			matchingCombinations(triple),
			Stream.of(
				ANY_ANY_ANY,
				new Triple(ANY, triple.getPredicate(), ANY)
			)
		);
	}
	
	/**
	 * Create possible match combinations
	 * This excludes (_, _, _) and (_, p, _)
	 */
	public static Stream<Triple> matchingCombinations(Triple triple) {
		var sub = triple.getSubject();
		var pre = triple.getPredicate();
		var obj = triple.getObject();
		
		var combinations = Stream.of(
			// pick 3
			new Triple(sub, pre, obj),
			
			// pick 2
			new Triple(sub, pre, ANY),
			new Triple(sub, ANY, obj),
			new Triple(ANY, pre, obj),
			
			// pick 1
			new Triple(sub, ANY, ANY),
			new Triple(ANY, ANY, obj)
		);
	
		
		return combinations;
	}
	
	/**
	 * Return the value part of a Node in a triple
	 */
	public static Optional<String> valueOf(Node node) {
		if (node == null || node.equals(Node.ANY)) {
			return empty();
		}
		else if (node.isURI()) {
			return Optional.of(node.getURI());
		}
		else if (node.isLiteral()) {
			return Optional.of(node.getLiteralLexicalForm());
		}
		else if (node.isBlank()) {
			return Optional.of(node.getBlankNodeId().toString());
		}
		else {
			throw new IllegalArgumentException(node.toString());
		}
	}
	
	/**
	 * Strip off N3 quotes
	 */
	public static String stripN3(String val) {
		if ((val.charAt(0) == '"') && (val.charAt(val.length() - 1) == '"')) {
			return val.substring(1, val.length() - 1);
		}
		
		if ((val.charAt(0) == '<') && (val.charAt(val.length() - 1) == '>')) {
			return val.substring(1, val.length() - 1);
		}
		
		throw new IllegalArgumentException(val);
	}
	
}
