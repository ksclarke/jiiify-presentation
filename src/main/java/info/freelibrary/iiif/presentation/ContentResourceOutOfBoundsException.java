
package info.freelibrary.iiif.presentation;

import info.freelibrary.util.I18nException;

/**
 * An exception thrown when a canvas is painted with a content resource that doesn't fit within the canvas bounds.
 */
public class ContentResourceOutOfBoundsException extends I18nException {

    /**
     * The <code>serialVersionUID</code> for an ContentResourceOutOfBoundsException.
     */
    private static final long serialVersionUID = 500985625287874950L;

    /**
     * Creates a new ContentResourceOutOfBoundsException.
     *
     * @param aMessageCode A message code
     * @param aDetails Additional details about the exception
     */
    public ContentResourceOutOfBoundsException(final String aMessageCode, final Object... aDetails) {
        super(Constants.BUNDLE_NAME, aMessageCode, aDetails);
    }

}
