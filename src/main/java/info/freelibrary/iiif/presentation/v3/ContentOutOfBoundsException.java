
package info.freelibrary.iiif.presentation.v3;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.I18nRuntimeException;

/**
 * An exception thrown when a canvas is painted with a content resource that doesn't fit within the canvas bounds.
 */
public class ContentOutOfBoundsException extends I18nRuntimeException {

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
    public ContentOutOfBoundsException(final String aMessageCode, final Object... aDetails) {
        super(MessageCodes.BUNDLE, aMessageCode, aDetails);
    }

}
