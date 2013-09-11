/**
 * 
 */
package com.zoo.youshang.api.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;

import org.apache.commons.io.IOUtils;

/**
 * @author sunpeng
 * 
 */
public class BigFileOutputStream implements javax.ws.rs.core.StreamingOutput {
	private InputStream inputStream;

	public BigFileOutputStream() {
	}

	public BigFileOutputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public void write(OutputStream output) throws IOException,
			WebApplicationException {
		// TODO Auto-generated method stub
		IOUtils.copy(inputStream, output);
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
