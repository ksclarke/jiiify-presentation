
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * Text that must be displayed when the resource is displayed or used. For example, the requiredStatement property
 * could be used to present copyright or ownership statements, an acknowledgement of the owning and/or publishing
 * institution, or any other text that the publishing organization deems critical to display to the user.
 */
@JsonDeserialize(using = RequiredStatementDeserializer.class)
public class RequiredStatement extends I18nEntry {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequiredStatement.class, MessageCodes.BUNDLE);

    private List<RequiredStatement.Entry> myEntries;

    /**
     * Creates a required statement property.
     */
    public RequiredStatement() {
        getEntries();
    }

    /**
     * Creates a required statement from the supplied required statement entry.
     *
     * @param aReqStatementEntry A required statement entry
     */
    public RequiredStatement(final RequiredStatement.Entry aReqStatementEntry) {
        checkEntryType(aReqStatementEntry);
        getEntries().add(aReqStatementEntry);
    }

    /**
     * Creates required statement from the supplied label and value.
     *
     * @param aLabel A required statement label
     * @param aValue A required statement value
     */
    public RequiredStatement(final Label aLabel, final Value aValue) {
        getEntries().add(new RequiredStatement.Entry(aLabel, aValue));
    }

    /**
     * Creates a required statement from the supplied label and value strings.
     *
     * @param aLabel A required statement label in string form
     * @param aValue A required statement value in string form
     */
    public RequiredStatement(final String aLabel, final String aValue) {
        getEntries().add(new RequiredStatement.Entry(aLabel, aValue));
    }

    /**
     * Adds the supplied required statement entry.
     *
     * @param aReqStatementEntry A required statement entry
     * @return The required statement
     */
    public RequiredStatement add(final RequiredStatement.Entry aReqStatementEntry) {
        checkEntryType(aReqStatementEntry);

        if (!getEntries().add(aReqStatementEntry)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_045, aReqStatementEntry));
        }

        return this;
    }

    /**
     * Adds the supplied required statement.
     *
     * @param aLabel A required statement label in string form
     * @param aValue A required statement value in string form
     * @return The required statement
     */
    public RequiredStatement add(final String aLabel, final String aValue) {
        final RequiredStatement.Entry requiredStmtEntry = new RequiredStatement.Entry(aLabel, aValue);

        if (!getEntries().add(requiredStmtEntry)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_045, requiredStmtEntry));
        }

        return this;
    }

    /**
     * Adds the supplied required statement.
     *
     * @param aLabel A required statement label
     * @param aValue A required statement value
     * @return The required statement
     */
    public RequiredStatement add(final Label aLabel, final Value aValue) {
        final RequiredStatement.Entry requiredStmtEntry = new RequiredStatement.Entry(aLabel, aValue);

        if (!getEntries().add(requiredStmtEntry)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_045, requiredStmtEntry));
        }

        return this;
    }

    /**
     * Gets the required statement entries.
     *
     * @return The required statement entries
     */
    @JsonValue
    public final List<RequiredStatement.Entry> getEntries() {
        if (myEntries == null) {
            myEntries = new ArrayList<>();
        }

        return myEntries;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    /**
     * Checks whether the supplied entry is a required statement entry.
     *
     * @param aMetadataEntry A required statement entry
     * @throws IllegalArgumentException If the supplied entry isn't a required statement entry
     */
    private void checkEntryType(final RequiredStatement.Entry aReqStatementEntry) throws IllegalArgumentException {
        final Class<?> outerClass = aReqStatementEntry.getOuterClass();
        final Class<?> thisClass = getClass();

        if (!outerClass.equals(thisClass)) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_034, outerClass, thisClass));
        }
    }

}
