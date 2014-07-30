package com.lss.bind;

public class UploadedFile {

	private final FileType fileType;
	private final byte[] bytes;

	public UploadedFile(FileType fileType, byte[] bytes) {
		this.fileType = fileType;
		this.bytes = bytes;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public FileType getFileType() {
		return fileType;
	}
}
