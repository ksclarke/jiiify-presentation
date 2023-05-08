
package info.freelibrary.iiif.presentation.v3;

import info.freelibrary.util.I18nRuntimeException;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An exception thrown when what's being painted doesn't fit within the {@link Canvas}' or region's bounds.
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
