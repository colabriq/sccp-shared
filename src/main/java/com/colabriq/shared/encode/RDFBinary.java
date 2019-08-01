package com.colabriq.shared.encode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.LinkedList;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.riot.system.PrefixMapFactory;
import org.apache.jena.riot.system.StreamRDFBase;
import org.apache.jena.riot.thrift.BinRDF;
import org.apache.jena.riot.thrift.StreamRDF2Thrift;
import org.apache.jena.riot.thrift.TRDF;
import org.apache.jena.riot.thrift.ThriftConvert;
import org.apache.jena.riot.thrift.wire.RDF_Term;
import org.apache.thrift.TException;

/**
 * Encodes and decodes the RDF binary format using Apache Thrift.
 * @author ijmad
 */
public class RDFBinary {
	/**
	 * Convert a triple to TRDF bytes
	 */
	public static byte [] encodeTriple(Triple triple) {
		var os = new ByteArrayOutputStream();
		
		try (var r2t = new StreamRDF2Thrift(os, true)) {
			r2t.triple(triple);
			r2t.finish();
			
			return os.toByteArray();
		}
	}
	
	/**
	 * Convert TRDF bytes to triple
	 */
	public static Triple decodeTriple(byte [] triple) {
		// this is set up with the expectation of one triple
		// unless we get exactly one triple, through Exception
		
		var triples = new LinkedList<Triple>();
		
		BinRDF.inputStreamToStream(
			new ByteArrayInputStream(triple),
			new StreamRDFBase() {
				@Override
				public void triple(Triple t) {
					triples.add(t);
				}
			}
		);
		
		if (triples.size() != 1) {
			throw new IllegalArgumentException("Input did not contain exactly one triple");
		}
		
		return triples.iterator().next();
	}

	/**
	 * Convert a node to TRDF bytes
	 */
	public static byte [] encodeNode(Node node) {
		try {
			var term = new RDF_Term();
			ThriftConvert.toThrift(node, PrefixMapFactory.create(), term, true);
			
			var baos = new ByteArrayOutputStream();
			
			var protocol = TRDF.protocol(baos);
			term.write(protocol);
			TRDF.flush(protocol);
			
			return baos.toByteArray();
		}
		catch (TException e) {
			throw new IllegalArgumentException("Could not write node to TRDF", e);
		}
	}
	
	/**
	 * Convert TRDF bytes to node
	 */
	public static Node decodeNode(byte [] node) {
		try {
			var term = new RDF_Term();
			term.read(TRDF.protocol(new ByteArrayInputStream(node)));
			
			return ThriftConvert.convert(term);
		}
		catch (TException e) {
			throw new IllegalArgumentException("Could not write node to TRDF", e);
		}
	}
}
