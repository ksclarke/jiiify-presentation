
package info.freelibrary.jsh4jvp3;

import static info.freelibrary.util.Constants.EOL;

import java.util.Locale;
import java.util.function.Consumer;

import info.freelibrary.util.StringUtils;

import jdk.jshell.Diag;

/**
 * A diagnostic consumer for handling rejected code snippets.
 */
public class DiagConsumer implements Consumer<Diag> {

    /** A delimiter for the end of problematic code. */
    private static final String END = "]]";

    /** A constant for a double EOL. */
    private static final String EOLX2 = EOL + EOL;

    /** An output message header. */
    private static final String HEADER = "Reason: ";

    /** An output message introduction. */
    private static final String INTRO = "Could not parse:";

    /** A delimiter for the start of problematic code. */
    private static final String START = "[[";

    /** The delimiter for the first part of the code template. */
    private static final String TEMPLATE_START = "public static void main(String[] args) {";

    /** An output buffer. */
    @SuppressWarnings({ "PMD.AvoidStringBufferField" })
    private final StringBuilder myBuffer;

    /**
     * Creates a new diagnostic consumer.
     *
     * @param aBuffer An output buffer
     */
    public DiagConsumer(final StringBuilder aBuffer) {
        myBuffer = aBuffer;
    }

    @Override
    @SuppressWarnings({ "PMD.SystemPrintln" })
    public void accept(final Diag aDiagnostic) {
        final String message = aDiagnostic.getMessage(Locale.US);
        final String code;

        int start = (int) aDiagnostic.getStartPosition();
        int end = (int) aDiagnostic.getEndPosition();

        if (start == end) {
            myBuffer.insert(start, START).insert(start + START.length(), END);
        } else {
            myBuffer.insert(start, START).insert(end, END);
        }

        end = myBuffer.indexOf(TEMPLATE_START) + TEMPLATE_START.length() + 2;
        start = myBuffer.length() - 4;

        myBuffer.delete(start, myBuffer.length()).delete(0, end);
        code = StringUtils.addLineNumbers(myBuffer.toString());

        myBuffer.setLength(0);
        myBuffer.append(INTRO).append(EOLX2).append(code).append(EOLX2).append(HEADER).append(message).append(EOL);
    }

}
