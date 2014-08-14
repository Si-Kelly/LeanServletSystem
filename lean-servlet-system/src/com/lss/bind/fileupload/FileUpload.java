package com.lss.bind.fileupload;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.Part;


public class FileUpload {
	private final String filename;
	private final InputStream inputStream;
	private final MimeType mimeType;
	private final String extension;

	public FileUpload(String filename, MimeType type, Part filePart) throws IOException {
		this.filename = filename;
		this.inputStream = filePart.getInputStream();
		this.mimeType = type;
		this.extension = (filename.indexOf(".") != -1 ? filename.substring(filename.lastIndexOf('.') + 1) : null);
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getFilename() {
		return filename;
	}

	public MimeType getMimeType() {
		return mimeType;
	}

	public String getExtension() {
		return extension;
	}

}
