package com.lss.bind;

public enum FileType {
	HTML("html", false), GIF("gif", true), JPEG("jpg", true), JS("js", false), CSS("css", false), PNG("png", true),

	UNKNOWN("unknown", false);

	public boolean isBinary() {
		return binary;
	}

	private String extension;
	private boolean binary;

	public String getExtension() {
		return extension;
	}

	FileType(String extension, boolean binary) {
		this.extension = extension;
		this.binary = binary;
	}

	public static FileType get(String contentType, String file) {
		// first check for file extension
		if (file.indexOf('.') != -1) {
			String ext = file.substring(file.lastIndexOf('.') + 1);
			if (ext.length() < 5 && ext.length() > 0) {
				if (ext.startsWith("html"))
					return HTML;
				else if (ext.equals("gif"))
					return GIF;
				else if (ext.equals("css"))
					return CSS;
				else if (ext.equals("jpg") || ext.equals("jpeg"))
					return JPEG;
				else if (ext.equals("js"))
					return JS;
				else if (ext.equals("png"))
					return PNG;
			}
		}
		if (contentType != null && contentType.length() > 0) {
			String[] fileFormat = contentType.split("\\/");
			String type = fileFormat[0];
			String format = fileFormat[1];
			if (type.equals("text")) {
				if (format.startsWith("html"))
					return HTML;
				else if (format.equals("css"))
					return CSS;
				else if (format.equals("javascript"))
					return JS;
			} else {
				if (format.equals("gif"))
					return GIF;
				else if (format.equals("jpg") || format.equals("jpeg"))
					return JPEG;
				else if (format.equals("png"))
					return PNG;
			}
		}
		return UNKNOWN;
	}
}
