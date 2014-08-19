package org.leanservlet.bind.fileupload;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.leanservlet.bind.ParamHelper;
import org.leanservlet.bind.ValidationException;


public class UploadedFileParam implements ParamHelper<UploadedFile> {
	private FileUploadParam fileParam;
	private UploadedFile value;
	private String fieldName;

	public UploadedFileParam(String fieldName, HttpServletRequest request) {
		fileParam = new FileUploadParam(fieldName, request);
		this.fieldName = fieldName;
	}

	@Override
	public UploadedFileParam require(String message) throws ValidationException {
		fileParam.require(message);
		return this;
	}

	@Override
	public void set(UploadedFile f) {
		throw new UnsupportedOperationException();
	}

	@Override
	public UploadedFile value() throws ValidationException {
		if (value == null) {
			FileUpload f = fileParam.value();
			ByteArrayOutputStream bos = new ByteArrayOutputStream(5000000);
			int len = 0;
			try {
				len = IOUtils.copy(f.getInputStream(), bos);
			} catch (IOException e) {
				throw new ValidationException(new String[] { this.fieldName, "Unable to read file." });
			}
			if (len > 0)
				value = new UploadedFile(f.getMimeType(), bos.toByteArray());
		}
		return value;
	}

	@Override
	public void push() throws ValidationException {
		throw new UnsupportedOperationException();
	}

}
