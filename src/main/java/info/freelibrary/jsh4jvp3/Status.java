
package info.freelibrary.jsh4jvp3;

/**
 * An encapsulation of HTTP response information.
 */
public enum Status {

    /** A <code>Method Not Allowed</code> HTTP response. */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    /** A <code>Not Found</code> HTTP response. */
    NOT_FOUND(404, "Not Found"),

    /** An <code>OK</code> HTTP response. */
    OK(201, "OK");

    /** An HTTP response status code. */
    private final int myStatusCode;

    /** An HTTP response status message. */
    private final String myStatusMessage;

    /**
     * Creates a new {@code HttpResponse}.
     *
     * @param aStatusCode An HTTP response status code
     * @param aStatusMessage An HTTP response status message
     */
    Status(final int aStatusCode, final String aStatusMessage) {
        myStatusCode = aStatusCode;
        myStatusMessage = aStatusMessage;
    }

    /**
     * Gets the response's status code.
     *
     * @return An HTTP response status code
     */
    public int getCode() {
        return myStatusCode;
    }

    /**
     * Gets the response's status message.
     *
     * @return An HTTP response status message
     */
    public String getMessage() {
        return myStatusMessage;
    }
}
