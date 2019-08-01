package com.goodforgoodbusiness.shared;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType ;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.content.AbstractContentBody;

public class ReaderFileBody extends AbstractContentBody {  
	private final String filename;
	private final Reader reader;
	
    public ReaderFileBody(String filename, ContentType contentType, Reader reader) {
		super(contentType);
		this.filename = filename;
		this.reader = reader;
	}

	public InputStream getInputStream() {
		throw new UnsupportedOperationException();
    }

    @Override
    public void writeTo(final OutputStream out) throws IOException {
    	IOUtils.copy(reader, out, Charset.defaultCharset());
    	
    	out.flush();
    	reader.close();
    }

    @Override
    public String getTransferEncoding() {
        return MIME.ENC_BINARY;
    }

    @Override
    public String getCharset() {
        return null;
    }

    @Override
    public long getContentLength() {
        return -1;
    }
    
    @Override
    public String getFilename() {
        return filename;
    }
}