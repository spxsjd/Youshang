/**
 * 
 */
package com.zoo.youshang.api.protocol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

/**
 * @author sunpeng
 * 
 */
public abstract class FormFileUploadHelper {

	public static String getFileType(InputPart inputPart) {
		MultivaluedMap<String, String> header = inputPart.getHeaders();
		String fileName = FormFileUploadHelper.getFileName(header);
		return fileName.substring(fileName.indexOf('.'));
	}

	public static void saveFile(InputPart inputPart,String savedFilePath) throws IOException{
		InputStream inputStream = inputPart
				.getBody(InputStream.class, null);
		byte[] bytes = IOUtils.toByteArray(inputStream);
		FormFileUploadHelper.writeFile(bytes, savedFilePath);
	}
	
	public static String getFileName(MultivaluedMap<String, String> header) {
		String[] contentDisposition = header.getFirst("Content-Disposition")
				.split(";");
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {
				String[] name = filename.split("=");
				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	// save to somewhere
	public static void writeFile(byte[] content, String filename)
			throws IOException {
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fop = new FileOutputStream(file);
		fop.write(content);
		fop.flush();
		fop.close();
	}

}
