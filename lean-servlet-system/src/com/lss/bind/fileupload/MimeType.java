package com.lss.bind.fileupload;

public final class MimeType {
	private final String type;
	private final String format;

	public MimeType(String contentTypeHeader) {
		String[] pair = contentTypeHeader.split("\\/");
		this.type = pair[0];
		this.format = pair[1];
	}

	public String getFormat() {
		return format;
	}

	public String getType() {
		return type;
	}
}
