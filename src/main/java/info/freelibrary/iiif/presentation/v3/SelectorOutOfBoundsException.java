
package info.freelibrary.iiif.presentation.v3;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.I18nRuntimeException;

/**
 * An exception thrown when attempting to paint a non-existent region of a {@link Canvas}.
 */
public class SelectorOutOfBoundsException extends I18nRuntimeException {

    /**
     * The <code>serialVersionUID</code> for an FragmentSelectorOutOfBoundsException.
     */
    private static final long serialVersionUID = 500985625287874950L;

    /**
     * Creates a new FragmentSelectorOutOfBoundsException.
     *
     * @param aMessageCode A message code
     * @param aDetails Additional details about the exception
     */
    public SelectorOutOfBoundsException(final String aMessageCode, final Object... aDetails) {
        super(MessageCodes.BUNDLE, aMessageCode, aDetails);
    }

}
