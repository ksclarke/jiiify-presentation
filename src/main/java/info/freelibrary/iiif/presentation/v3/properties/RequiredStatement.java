
package info.freelibrary.iiif.presentation.v3.properties;

/**
 * Text that must be displayed when the resource is displayed or used. For example, the requiredStatement property could
 * be used to present copyright or ownership statements, an acknowledgement of the owning and/or publishing institution,
 * or any other text that the publishing organization deems critical to display to the user.
 */
public class RequiredStatement extends Metadata {

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
     * Creates a required statement from the supplied label and value.
     *
     * @param aLabel A label
     * @param aValue A value
     */
    public RequiredStatement(final String aLabel, final String aValue) {
        super(new Label(aLabel), new Value(aValue));
    }

    /**
     * Constructor for Jackson deserialization.
     */
    private RequiredStatement() {
        super();
    }

    /**
     * Creates a copy of the supplied required statement.
     *
     * @param aRequiredStatement A required statement to copy
     */
    public RequiredStatement(final RequiredStatement aRequiredStatement) {
        super(aRequiredStatement.getLabel().copy(), aRequiredStatement.getValue().copy());
    }

    /**
     * Creates a copy of this required statement.
     *
     * @return A copy of this required statement
     */
    @Override
    public RequiredStatement copy() {
        return new RequiredStatement(this);
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
     * Sets the required statement's value.
     *
     * @param aValue A value
     * @return This required statement
     */
    @Override
    public RequiredStatement setValue(final Value aValue) {
        return (RequiredStatement) super.setValue(aValue);
    }
}
