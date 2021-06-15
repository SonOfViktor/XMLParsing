package com.fairycompany.xml.exception;

public class XmlTaskException extends Exception {
    public XmlTaskException() {
    }

    public XmlTaskException(String message) {
        super(message);
    }

    public XmlTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlTaskException(Throwable cause) {
        super(cause);
    }
}
