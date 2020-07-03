
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * An ordered list of descriptions to be displayed to the user when they interact with the resource, given as pairs of
 * human readable label and value entries. The content of these entries is intended for presentation only; descriptive
 * semantics should not be inferred. An entry might be used to convey information about the creation of the object, a
 * physical description, ownership information, or other purposes.
 */
@JsonDeserialize(using = MetadataDeserializer.class)
public class Metadata extends I18nEntry {

    private static final Logger LOGGER = LoggerFactory.getLogger(Metadata.class, MessageCodes.BUNDLE);

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
        checkEntryType(aMetadataEntry);
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
     * @param aLabel A metadata label in string form
     * @param aValue A metadata value in string form
     */
    public Metadata(final String aLabel, final String aValue) {
        getEntries().add(new Metadata.Entry(aLabel, aValue));
    }

    /**
     * Adds the supplied metadata entry.
     *
     * @param aMetadataEntry A metadata entry
     * @return The metadata
     */
    public Metadata add(final Metadata.Entry aMetadataEntry) {
        checkEntryType(aMetadataEntry);

        if (!getEntries().add(aMetadataEntry)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_044, aMetadataEntry));
        }

        return this;
    }

    /**
     * Adds the supplied metadata.
     *
     * @param aLabel A metadata label in string form
     * @param aValue A metadata value in string form
     * @return The metadata
     */
    public Metadata add(final String aLabel, final String aValue) {
        final Metadata.Entry metadataEntry = new Metadata.Entry(aLabel, aValue);

        if (!getEntries().add(metadataEntry)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_044, metadataEntry));
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
        final Metadata.Entry metadataEntry = new Metadata.Entry(aLabel, aValue);

        if (!getEntries().add(metadataEntry)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_044, metadataEntry));
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

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    /**
     * Checks whether the supplied entry is a metadata entry.
     *
     * @param aMetadataEntry A metadata entry
     * @throws IllegalArgumentException If the supplied entry isn't a metadata entry
     */
    private void checkEntryType(final Metadata.Entry aMetadataEntry) throws IllegalArgumentException {
        final Class<?> outerClass = aMetadataEntry.getOuterClass();
        final Class<?> thisClass = getClass();

        if (!outerClass.equals(thisClass)) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_034, outerClass, thisClass));
        }
    }

}
