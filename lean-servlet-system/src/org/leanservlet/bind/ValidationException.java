package org.leanservlet.bind;

import java.util.HashMap;
import java.util.Map;

import org.leanservlet.BaseCheckedException;


public class ValidationException extends BaseCheckedException {
    /**
     * 
     */
    private static final long serialVersionUID = 3738453555507875197L;
    private final Map<String, String> validations;

    public ValidationException() {
        super("Validation Exception");
        this.validations = new HashMap<String, String>();
    }

    public ValidationException(String[]... messages) {
        this();
        for (String[] strings : messages) {
            validations.put(strings[0], strings[1]);
        }
    }

    public Map<String, String> getValidations() {
        return validations;
    }

    public void setMessage(String fieldName, String message) {
        validations.put(fieldName, message);
    }
}
