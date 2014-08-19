package org.leanservlet.bind.fileupload;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.leanservlet.bind.ParamHelper;
import org.leanservlet.bind.ValidationException;


public class FileUploadParam implements ParamHelper<FileUpload> {
	private static final Log LOGGER = LogFactory.getLog(FileUploadParam.class);
	private FileUpload upload;
	private final String fieldName;
	private HttpServletRequest request;

	public FileUploadParam(String fieldName, HttpServletRequest request) {
		this.fieldName = fieldName;
		this.request = request;
	}

	@Override
	public FileUploadParam require(String message) throws ValidationException {
		if (value() == null)
			throw new ValidationException(new String[] { fieldName, message });
		return this;
	}

	@Override
	public void set(FileUpload value) {
		request.setAttribute(fieldName, value.getFilename());
	}

	@Override
	public FileUpload value() throws ValidationException {
		if (upload == null) {
			try {
				Part filePart = request.getPart(fieldName);
				String filename = null;
				if (filePart != null) {
					final String partHeader = filePart.getHeader("content-disposition");
					LOGGER.info(String.format("Part Header = %s", partHeader));
					for (String content : partHeader.split(";")) {
						if (content.trim().startsWith("filename")) {
							filename = content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
						}
					}
					if (filename != null) {
						MimeType type = null;
						if (filePart.getHeader("content-type") != null)
							type = new MimeType(filePart.getHeader("content-type"));
						upload = new FileUpload(filename, type, filePart);
					}
				} else {
					LOGGER.debug(String.format("%s: File Part is null", fieldName));
				}
			} catch (ServletException e) {
				LOGGER.error(String.format("Error getting File Part %s from the request", fieldName), e);
				throw new ValidationException(new String[] { fieldName, "Unable to upload the file." });
			} catch (IllegalStateException e) {
				LOGGER.error(String.format("Error getting File Part %s from the request", fieldName), e);
				throw new ValidationException(new String[] { fieldName, "Unable to upload the file." });
			} catch (IOException e) {
				LOGGER.error(String.format("Error getting File Part %s from the request", fieldName), e);
				throw new ValidationException(new String[] { fieldName, "Unable to upload the file." });
			}
		}

		return upload;
	}

	@Override
	public void push() throws ValidationException {
		set(value());
	}

	public FileUploadParam requireText(String message) throws ValidationException {
		if (value() != null) {
			MimeType mt = value().getMimeType();
			if (mt != null && !mt.getType().equals("text")) {
				throw new ValidationException(new String[] { fieldName, message });
			}
		}
		return this;
	}

}
