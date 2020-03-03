
package info.freelibrary.iiif.presentation.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * An ordered list of descriptions to be displayed to the user when they interact with the resource, given as pairs of
 * human readable label and value entries. The content of these entries is intended for presentation only; descriptive
 * semantics should not be inferred. An entry might be used to convey information about the creation of the object, a
 * physical description, ownership information, or other purposes.
 */
@JsonDeserialize(using = MetadataDeserializer.class)
public class Metadata {

    private static final Logger LOGGER = LoggerFactory.getLogger(Metadata.class, Constants.BUNDLE_NAME);

    private List<Metadata.Entry> myEntries;

    /**
     * Creates a metadata property.
     */
    public Metadata() {
        getEntries();
    }

    /**
     * Creates metadata from the supplied metadata entry.
     *
     * @param aMetadataEntry A metadata entry
     */
    public Metadata(final Metadata.Entry aMetadataEntry) {
        getEntries().add(aMetadataEntry);
    }

    /**
     * Creates metadata from the supplied label and value.
     *
     * @param aLabel A metadata label
     * @param aValue A metadata value
     */
    public Metadata(final Label aLabel, final Value aValue) {
        getEntries().add(new Metadata.Entry(aLabel, aValue));
    }

    /**
     * Creates metadata from the supplied label and value strings.
     *
     * @param aLabelString A metadata label in string form
     * @param aValueString A metadata value in string form
     */
    public Metadata(final String aLabelString, final String aValueString) {
        getEntries().add(new Metadata.Entry(aLabelString, aValueString));
    }

    /**
     * Adds the supplied metadata entry.
     *
     * @param aMetadataEntry A metadata entry
     * @return The metadata
     */
    public Metadata add(final Metadata.Entry aMetadataEntry) {
        if (!getEntries().add(aMetadataEntry)) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Adds the supplied metadata.
     *
     * @param aLabelString A metadata label in string form
     * @param aValueString A metadata value in string form
     * @return The metadata
     */
    public Metadata add(final String aLabelString, final String aValueString) {
        if (!getEntries().add(new Metadata.Entry(aLabelString, aValueString))) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Adds the supplied metadata.
     *
     * @param aLabel A metadata label
     * @param aValue A metadata value
     * @return The metadata
     */
    public Metadata add(final Label aLabel, final Value aValue) {
        if (!getEntries().add(new Metadata.Entry(aLabel, aValue))) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Gets the metadata entries.
     *
     * @return The metadata entries
     */
    @JsonValue
    public final List<Metadata.Entry> getEntries() {
        if (myEntries == null) {
            myEntries = new ArrayList<>();
        }

        return myEntries;
    }

    /**
     * A metadata entry with a label and value.
     */
    @JsonPropertyOrder({ "label", "value" })
    public class Entry {

        private final Label myLabel;

        private final Value myValue;

        /**
         * Creates a metadata entry from the supplied label and value.
         *
         * @param aLabelString A metadata label in string form
         * @param aValueString A metadata value in string form
         */
        public Entry(final String aLabelString, final String aValueString) {
            Objects.requireNonNull(aLabelString, LOGGER.getMessage(MessageCodes.JPA_002));
            Objects.requireNonNull(aValueString, LOGGER.getMessage(MessageCodes.JPA_022));

            myLabel = new Label(aLabelString);
            myValue = new Value(aValueString);
        }

        /**
         * Creates a metadata entry from the supplied label and value.
         *
         * @param aLabel A metadata label
         * @param aValue A metadata value
         */
        public Entry(final Label aLabel, final Value aValue) {
            Objects.requireNonNull(aLabel, LOGGER.getMessage(MessageCodes.JPA_002));
            Objects.requireNonNull(aValue, LOGGER.getMessage(MessageCodes.JPA_022));

            myLabel = aLabel;
            myValue = aValue;
        }

        /**
         * Gets the label for the metadata entry.
         *
         * @return The label for the metadata entry
         */
        @JsonGetter(Constants.LABEL)
        public Label getLabel() {
            return myLabel;
        }

        /**
         * Gets the metadata entry's value.
         *
         * @return The metadata entry's value
         */
        @JsonGetter(Constants.VALUE)
        public Value getValue() {
            return myValue;
        }
    }
}
