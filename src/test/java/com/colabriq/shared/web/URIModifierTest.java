package com.colabriq.shared.web;

import java.net.URI;

import com.colabriq.shared.URIModifier;

public class URIModifierTest {
	public static void main(String[] args) throws Exception {
		System.out.println(
			URIModifier.from(new URI("http://localhost:8082")).build()
		);
		
		System.out.println(
			URIModifier.from(new URI("http://localhost:8082")).addParam("foo", "bar").build()
		);
		
		System.out.println(
			URIModifier.from(new URI("http://localhost:8082")).appendPath("/foo").build()
		);
	
		System.out.println(
			URIModifier.from(new URI("http://localhost:8082")).appendPath("foo/bar").build()
		);
		
		System.out.println(
			URIModifier.from(new URI("http://localhost:8082")).addParam("foo", "bar").appendPath("foo").build()
		);
		
		System.out.println(
			URIModifier.from(new URI("http://localhost:8082")).addParam("fo o", "b?ar").appendPath("foo").build()
		);
		
		System.out.println(
			URIModifier.from(new URI("http://localhost:8082/bar#fragment")).build()
		);
	}
}
