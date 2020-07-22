
package info.freelibrary.iiif.presentation.v3.id;

import info.freelibrary.util.I18nRuntimeException;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * An exception thrown when the ID minter has a problem returning a newly minted ID.
 */
public class MintingException extends I18nRuntimeException {

    /**
     * The <code>serialVersionUID</code> for {@link MintingException}.
     */
    private static final long serialVersionUID = -8994128436406185140L;

    /**
     * Creates a new minting exception from the supplied root cause.
     *
     * @param aCause A root cause of the minting exception
     */
    public MintingException(final Throwable aCause) {
        super(aCause);
    }

    /**
     * Creates a new minting exception from the supplied message key.
     *
     * @param aMessageKey A message key for the exception
     */
    public MintingException(final String aMessageKey) {
        super(MessageCodes.BUNDLE, aMessageKey);
    }

    /**
     * Creates a new minting exception from the supplied message key and additional details.
     *
     * @param aMessageKey A message key for the exception
     * @param aDetailsArray Additional details
     */
    public MintingException(final String aMessageKey, final Object... aDetailsArray) {
        super(MessageCodes.BUNDLE, aMessageKey, aDetailsArray);
    }

    /**
     * Creates a new minting exception from the supplied message key and root cause.
     *
     * @param aCause A root cause of the minting exception
     * @param aMessageKey A message key for the exception
     */
    public MintingException(final Throwable aCause, final String aMessageKey) {
        super(aCause, MessageCodes.BUNDLE, aMessageKey);
    }

    /**
     * Creates a new minting exception from the root cause, supplied message key, and additional details.
     *
     * @param aCause A root cause of the minting exception
     * @param aMessageKey A message key for the exception
     * @param aDetailsArray Additional details about the exception
     */
    public MintingException(final Throwable aCause, final String aMessageKey, final Object... aDetailsArray) {
        super(aCause, MessageCodes.BUNDLE, aMessageKey, aDetailsArray);
    }

}
