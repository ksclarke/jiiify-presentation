
package info.freelibrary.iiif.presentation.v3.properties;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * Text that must be displayed when the resource is displayed or used. For example, the requiredStatement property
 * could be used to present copyright or ownership statements, an acknowledgement of the owning and/or publishing
 * institution, or any other text that the publishing organization deems critical to display to the user.
 */
public class RequiredStatement extends Metadata {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequiredStatement.class, MessageCodes.BUNDLE);

    /**
     * Constructor for Jackson deserialization.
     */
    private RequiredStatement() {
    }

    /**
     * Creates a required statement from the supplied label and value.
     *
     * @param aLabel A label
     * @param aValue A value
     */
    public RequiredStatement(final Label aLabel, final Value aValue) {
        super(aLabel, aValue);
    }

    /**
     * Creates a required statement from the supplied label and value strings.
     *
     * @param aLabel A label in string form
     * @param aValue A value in string form
     */
    public RequiredStatement(final String aLabel, final String aValue) {
        super(new Label(aLabel), new Value(aValue));
    }

    /**
     * Sets the label for the required statement.
     *
     * @param aLabel A label
     * @return This required statement
     */
    @Override
    public RequiredStatement setLabel(final Label aLabel) {
        return (RequiredStatement) super.setLabel(aLabel);
    }

    /**
     * Sets the label for the required statement.
     *
     * @param aLabel A label in string form
     * @return This required statement
     */
    @Override
    public RequiredStatement setLabel(final String aLabel) {
        return (RequiredStatement) super.setLabel(aLabel);
    }

    /**
     * Sets the required statement's value.
     *
     * @param aValue A value
     * @return This required statement
     */
    @Override
    public RequiredStatement setValue(final Value aValue) {
        return (RequiredStatement) super.setValue(aValue);
    }

    /**
     * Sets the required statement's value.
     *
     * @param aValue A value in string form
     * @return This required statement
     */
    @Override
    public RequiredStatement setValue(final String aValue) {
        return (RequiredStatement) super.setValue(aValue);
    }
}
