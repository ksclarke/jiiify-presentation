
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * A metadata property with a label and value.
 */
@JsonPropertyOrder({ Constants.LABEL, Constants.VALUE })
public class Metadata {

    private static final Logger LOGGER = LoggerFactory.getLogger(Metadata.class, MessageCodes.BUNDLE);

    private Label myLabel;

    private Value myValue;

    /**
     * Constructor for Jackson deserialization.
     */
    Metadata() {
    }

    /**
     * Creates a metadata property from the supplied label and value.
     *
     * @param aLabel A label
     * @param aValue A value
     */
    public Metadata(final Label aLabel, final Value aValue) {
        setLabel(aLabel);
        setValue(aValue);
    }

    /**
     * Creates a metadata property from the supplied label and value.
     *
     * @param aLabel A label in string form
     * @param aValue A value in string form
     */
    public Metadata(final String aLabel, final String aValue) {
        this(new Label(aLabel), new Value(aValue));
    }

    /**
     * Gets the label for the metadata property.
     *
     * @return The label for the metadata property
     */
    @JsonGetter(Constants.LABEL)
    public Label getLabel() {
        return myLabel;
    }

    /**
     * Sets the label for the metadata property.
     *
     * @param aLabel A label
     * @return This metadata property
     */
    @JsonSetter(Constants.LABEL)
    public Metadata setLabel(final Label aLabel) {
        Objects.requireNonNull(aLabel, LOGGER.getMessage(MessageCodes.JPA_002));
        myLabel = aLabel;

        return this;
    }

    /**
     * Sets the label for the metadata property.
     *
     * @param aLabel A label in string form
     * @return This metadata property
     */
    @JsonIgnore
    public Metadata setLabel(final String aLabel) {
        return setLabel(new Label(aLabel));
    }

    /**
     * Gets the metadata property's value.
     *
     * @return The metadata property's value
     */
    @JsonGetter(Constants.VALUE)
    public Value getValue() {
        return myValue;
    }

    /**
     * Sets the metadata property's value.
     *
     * @param aValue A value
     * @return This metadata property
     */
    @JsonSetter(Constants.VALUE)
    public Metadata setValue(final Value aValue) {
        Objects.requireNonNull(aValue, LOGGER.getMessage(MessageCodes.JPA_022));
        myValue = aValue;

        return this;
    }

    /**
     * Sets the metadata property's value.
     *
     * @param aValue A value in string form
     * @return This metadata property
     */
    @JsonIgnore
    public Metadata setValue(final String aValue) {
        return setValue(new Value(aValue));
    }

    @Override
    public String toString() {
        return myLabel + ":" + myValue;
    }
}
