package com.lss.bind.fileupload;


public class UploadedFile {

	private final MimeType mimeType;
	private final byte[] bytes;

	public UploadedFile(MimeType mimeType, byte[] bytes) {
		this.mimeType = mimeType;
		this.bytes = bytes;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public MimeType getMimeType() {
		return mimeType;
	}
}
