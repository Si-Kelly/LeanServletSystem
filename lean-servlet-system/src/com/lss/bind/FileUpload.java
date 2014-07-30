package com.lss.bind;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.Part;

public class FileUpload {
	private final String filename;
	private final InputStream inputStream;
	private final FileType type;

	public FileUpload(String filename, FileType type, Part filePart) throws IOException {
		this.filename = filename;
		this.inputStream = filePart.getInputStream();
		this.type = type;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getFilename() {
		return filename;
	}

	public FileType getFileType() {
		return type;
	}

}
