
package info.freelibrary.iiif.webrepl;

import info.freelibrary.util.Constants;
import info.freelibrary.util.StringUtils;

/**
 * A {@code WebRepl} parsing error.
 */
public class ParsingError extends Exception {

    /** The message template used in constructing the exception message. */
    static final String MESSAGE_TEMPLATE =
            "Parsing error, but there wasn't a useful diagnostic message. Submitted source code:" + Constants.EOL +
                    Constants.EOL + "{}";

    /** The {@code serialVersionUID} for the ParsingError class. */
    private static final long serialVersionUID = 8614571124201029070L;

    /**
     * Creates a parsing error for the supplied source. This type of parsing error lacks any diagnostic information from
     * JShell.
     *
     * @param aSource A source code snippet
     */
    public ParsingError(final String aSource) {
        super(StringUtils.format(MESSAGE_TEMPLATE, aSource));
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
