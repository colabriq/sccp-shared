package com.goodforgoodbusiness.shared;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static java.net.URLEncoder.encode;

import java.io.UnsupportedEncodingException;

import static java.lang.Integer.parseInt;

public class URIModifier {
	public static URIModifier from(URI base) {
		return new URIModifier(base);
	}
	
	private String scheme;
	private String authority;
	private String path;
	private String query;
	private String fragment;
	
	private URIModifier(URI base) {
		this.scheme = base.getScheme();
		this.authority = base.getAuthority();
		this.path = base.getPath();
		this.query = base.getQuery();
		this.fragment = base.getFragment();
	}
	
	public URIModifier appendPath(String pathPart) {
		if (this.path == null || this.path.length() == 0) {
			if (path.charAt(0) != '/') {
				this.path += '/';
			}
		}
		
		this.path += pathPart;
		return this;
	}
	
	public URIModifier addParam(String name, String value) {
		try {
			if (this.query == null) {
				this.query = "";
			}
			else if (this.query.length() > 0) {
				this.query += "&";
			}
			
			this.query += encode(name, "UTF-8") + "=" + encode(value, "UTF-8");
			return this;
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 is unsupported? That's broken.", e);
		}
	}
	
	public URI build() throws URISyntaxException {
		var authparts = this.authority.split(":");
		var host = authparts[0];
		var port = authparts.length > 1 ? parseInt(authparts[1]) : -1;
		
		var file = "";
		
		if (this.path != null && this.path.length() > 0) {
			file += this.path;
		}
		
		if (this.query != null && this.query.length() > 0) {
			if (file.length() == 0) {
				file += '/';
			}
			
			file += '?' + this.query;
		}
		
		if (this.fragment != null && this.fragment.length() > 0) {
			if (file.length() == 0) {
				file += '/';
			}
			
			file += '#' + this.fragment;
		}
		
		try {
			// going via URL avoids double encoding the query string
			var url = new URL(scheme, host, port, file);
			return url.toURI();
		}
		catch (MalformedURLException e) {
			throw new URISyntaxException(scheme + authority + path + query, e.getMessage(), -1);
		}
	}
	
	@Override
	public String toString() {
		try {
			return build().toString();
		}
		catch (URISyntaxException e) {
			return super.toString();
		}
	}
}
