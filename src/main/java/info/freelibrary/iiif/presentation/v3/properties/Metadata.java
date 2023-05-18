
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A metadata property with a label and value.
 */
@JsonPropertyOrder({ JsonKeys.LABEL, JsonKeys.VALUE })
public class Metadata {

    /** The metadata logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Metadata.class, MessageCodes.BUNDLE);

    /** The metadata label. */
    private Label myLabel;

    /** The metadata value. */
    private Value myValue;

    /**
     * Creates a metadata property from the supplied label and value.
     *
     * @param aLabel A label
     * @param aValue A value
     */
    public Metadata(final Label aLabel, final Value aValue) {
        Objects.requireNonNull(aLabel, LOGGER.getMessage(MessageCodes.JPA_002));
        Objects.requireNonNull(aValue, LOGGER.getMessage(MessageCodes.JPA_022));

        myLabel = aLabel;
        myValue = aValue;
    }

    /**
     * Creates a metadata property from the supplied label and value.
     *
     * @param aLabel A label
     * @param aValue A value
     */
    public Metadata(final String aLabel, final String aValue) {
        this(new Label(aLabel), new Value(aValue));
    }

    /**
     * Constructor for Jackson deserialization.
     */
    Metadata() {
        // This is intentionally empty
    }

    /**
     * Gets the label for the metadata property.
     *
     * @return The label for the metadata property
     */
    @JsonGetter(JsonKeys.LABEL)
    public Label getLabel() {
        return myLabel;
    }

    /**
     * Sets the label for the metadata property.
     *
     * @param aLabel A label
     * @return This metadata property
     */
    @JsonSetter(JsonKeys.LABEL)
    public Metadata setLabel(final Label aLabel) {
        Objects.requireNonNull(aLabel, LOGGER.getMessage(MessageCodes.JPA_002));
        myLabel = aLabel;

        return this;
    }

    /**
     * Sets the label for the metadata property.
     *
     * @param aLabel A label
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
    @JsonGetter(JsonKeys.VALUE)
    public Value getValue() {
        return myValue;
    }

    /**
     * Sets the metadata property's value.
     *
     * @param aValue A value
     * @return This metadata property
     */
    @JsonSetter(JsonKeys.VALUE)
    public Metadata setValue(final Value aValue) {
        Objects.requireNonNull(aValue, LOGGER.getMessage(MessageCodes.JPA_022));
        myValue = aValue;

        return this;
    }

    /**
     * Sets the metadata property's value.
     *
     * @param aValue A value
     * @return This metadata property
     */
    @JsonIgnore
    public Metadata setValue(final String aValue) {
        return setValue(new Value(aValue));
    }

    /**
     * Gets a string representation of the metadata.
     *
     * @return A string representation of the metadata
     */
    @Override
    public String toString() {
        return myLabel + ":" + myValue;
    }
}
