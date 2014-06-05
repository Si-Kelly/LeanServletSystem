package com.lss.bind;


public interface ParamHelper<T> {

    /**
     * Checks if the given fieldName is present on the request parameters. If not a ValidationException is thrown with the provided message.
     * @param message
     * @return
     * @throws ValidationException
     */
    public ParamHelper<T> require(String message) throws ValidationException;

    /**
     * Sets the value of this form field that will be returned with value() and also puts a String version of the value into the request Attributes.
     * @param value
     */
    public abstract void set(T value);

    /**
     * Returns the value set using set() or, if no value has been set will look for the field name on the request parameters. If a parameter is present, it will be parsed,
     * otherwise null is returned.
     * @return
     * @throws ValidationException
     */
    public T value() throws ValidationException;
    
    /**
     * Creates an Attribute on the request having the value returned by value(). Equivalent to calling set(value()).
     */
    public void push() throws ValidationException;
}
