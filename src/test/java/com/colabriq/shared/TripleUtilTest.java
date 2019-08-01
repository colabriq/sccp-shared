package com.goodforgoodbusiness.shared;


import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;

public class TripleUtilTest {
	public static void main(String[] args) {
		var a = new Triple(
			NodeFactory.createURI("urn:s1"),
			NodeFactory.createURI("urn:p1"),
			NodeFactory.createURI("urn:o1")
		);
		
		System.out.println(TripleUtil.isMatch(a,  a));
		
		var p1 = new Triple(
			Node.ANY,
			NodeFactory.createURI("urn:p1"),
			NodeFactory.createURI("urn:o1")
		);
		
		System.out.println(TripleUtil.isMatch(p1,  a));
		
		var p2 = new Triple(
			Node.ANY,
			NodeFactory.createURI("urn:p1"),
			Node.ANY
		);
		
		System.out.println(TripleUtil.isMatch(p2,  a));
		
		var p3 = new Triple(
			NodeFactory.createURI("urn:s1"),
			NodeFactory.createURI("urn:p2"),
			NodeFactory.createURI("urn:o1")
		);
		
		System.out.println(TripleUtil.isMatch(p3,  a));
		
		var p4 = new Triple(
			Node.ANY,
			NodeFactory.createURI("urn:p2"),
			NodeFactory.createURI("urn:o1")
		);
		
		System.out.println(TripleUtil.isMatch(p4,  a));
	}
}
