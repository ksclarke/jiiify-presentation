
package info.freelibrary.iiif.presentation.v2.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v2.utils.Constants;
import info.freelibrary.iiif.presentation.v2.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v2.utils.MetadataDeserializer;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * A list of short descriptive entries, given as pairs of human readable label and value to be displayed to the user.
 * The value should be either simple HTML, including links and text markup, or plain text, and the label should be plain
 * text. There are no semantics conveyed by this information, and clients should not use it for discovery or other
 * purposes. This list of descriptive pairs should be able to be displayed in a tabular form in the user interface.
 * Clients should have a way to display the information about manifests and canvases, and may have a way to view the
 * information about other resources. The client should display the pairs in the order provided by the description. A
 * pair might be used to convey the author of the work, information about its creation, a brief physical description, or
 * ownership information, amongst other use cases. The client is not expected to take any action on this information
 * beyond displaying the label and value. An example pair of label and value might be a label of &quot;Author&quot; and
 * a value of &quot;Jehan Froissart&quot;.
 * <p>
 * An example representation in JSON:
 * </p>
 *
 * <pre>
 * <code>
 * &quot;metadata&quot;: [
 *   { &quot;label&quot;: &quot;Author&quot;, &quot;value&quot;: &quot;Anne Author&quot; },
 *   { &quot;label&quot;: &quot;Published&quot;, &quot;value&quot;: [
 *       { &quot;@value&quot;: &quot;Paris, circa 1400&quot;, &quot;@language&quot;: &quot;en&quot; },
 *       { &quot;@value&quot;: &quot;Paris, environ 1400&quot;, &quot;@language&quot;: &quot;fr&quot; }
 *     ]
 *   },
 *   { &quot;label&quot;: &quot;Notes&quot;, &quot;value&quot;: [&quot;Note 1&quot;, &quot;Note 2&quot;] },
 *   { &quot;label&quot;: &quot;Source&quot;,
 *       &quot;value&quot;: &quot;From: &lt;a href='http://example.org/1.html'&gt;link&lt;/a&gt;&quot;
 *   }
 * ]
 * </code>
 * </pre>
 */
@JsonDeserialize(using = MetadataDeserializer.class)
public class Metadata {

    /**
     * The metadata's logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Metadata.class, MessageCodes.BUNDLE);

    /**
     * The metadata's entries.
     */
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
     * Creates metadata from the supplied label and string values list.
     *
     * @param aLabel A metadata label
     * @param aValue A list of string values
     */
    public Metadata(final String aLabel, final String... aValue) {
        getEntries().add(new Metadata.Entry(aLabel, aValue));
    }

    /**
     * Creates metadata from the supplied label and I18n values list.
     *
     * @param aLabel A metadata label
     * @param aValue A list of I18n values
     */
    public Metadata(final String aLabel, final Value... aValue) {
        getEntries().add(new Metadata.Entry(aLabel, aValue));
    }

    /**
     * Add the supplied metadata.
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
     * Add the supplied metadata.
     *
     * @param aLabel A metadata label
     * @param aValue A metadata values list
     * @return The metadata
     */
    public Metadata add(final String aLabel, final String... aValue) {
        if (!getEntries().add(new Metadata.Entry(aLabel, aValue))) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Add the supplied metadata.
     *
     * @param aLabel A metadata label
     * @param aValue A metadata values list
     * @return The metadata
     */
    public Metadata add(final String aLabel, final Value... aValue) {
        if (!getEntries().add(new Metadata.Entry(aLabel, aValue))) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Gets the first metadata value for the supplied label.
     *
     * @param aLabel A metadata element name
     * @return The first metadata value for the supplied name
     */
    @JsonIgnore
    public Optional<String> getValue(final String aLabel) {
        final Iterator<Metadata.Entry> iterator = myEntries.iterator();

        while (iterator.hasNext()) {
            final Metadata.Entry entry = iterator.next();

            if (entry.getLabel().equals(aLabel)) {
                return Optional.of(entry.getString());
            }
        }

        return Optional.empty();
    }

    /**
     * Gets the first metadata value for the supplied label with the supplied language code.
     *
     * @param aLabel A metadata element name
     * @param aLangCode A language code (e.g. 'eng')
     * @return The first metadata value for the supplied name
     */
    @JsonIgnore
    public Optional<String> getValue(final String aLabel, final String aLangCode) {
        final Iterator<Metadata.Entry> entryIterator = myEntries.iterator();

        while (entryIterator.hasNext()) {
            final Metadata.Entry entry = entryIterator.next();

            if (entry.getLabel().equals(aLabel)) {
                final Iterator<Value> valuesIterator = entry.myValues.iterator();

                while (valuesIterator.hasNext()) {
                    final Value value = valuesIterator.next();
                    final Optional<String> lang = value.getLang();

                    if (lang.isPresent() && lang.get().equals(aLangCode)) {
                        return Optional.of(value.getValue());
                    }
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Gets the metadata values for the supplied label.
     *
     * @param aLabel A metadata element name
     * @return The values for the supplied name
     */
    @JsonIgnore
    public List<Value> getValues(final String aLabel) {
        final Iterator<Metadata.Entry> iterator = myEntries.iterator();

        while (iterator.hasNext()) {
            final Metadata.Entry entry = iterator.next();

            if (entry.getLabel().equals(aLabel)) {
                return entry.getValues();
            }
        }

        return new ArrayList<>();
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
     * A metadata entry with a label and values.
     */
    @JsonPropertyOrder({ "label", "value" })
    public class Entry {

        /**
         * The metadata entry's label.
         */
        private final String myLabel;

        /**
         * The metadata entry's values.
         */
        private final List<Value> myValues;

        /**
         * Creates a metadata entry from the supplied label and string values.
         *
         * @param aLabel A metadata label
         * @param aValue A list of string values
         */
        public Entry(final String aLabel, final String... aValue) {
            Objects.requireNonNull(aLabel, MessageCodes.JPA_002);
            myValues = new ArrayList<>();
            myLabel = aLabel;
            addValues(aValue);
        }

        /**
         * Creates a metadata entry from the supplied label and I18n values.
         *
         * @param aLabel A metadata label
         * @param aValue A list of I18n values
         */
        public Entry(final String aLabel, final Value... aValue) {
            Objects.requireNonNull(aLabel, MessageCodes.JPA_002);
            myValues = new ArrayList<>();
            myLabel = aLabel;
            addValues(aValue);
        }

        /**
         * Returns true if the metadata entry has values; else, false
         *
         * @return True if the metadata entry has values; else, false
         */
        public boolean hasValues() {
            return !myValues.isEmpty();
        }

        /**
         * Gets the label for the metadata entry.
         *
         * @return The label for the metadata entry
         */
        @JsonGetter(Constants.LABEL)
        public String getLabel() {
            return myLabel;
        }

        /**
         * Gets the first string value from the metadata entry; if the entry only contains I18n values, the string value
         * of the first language/value pair will be returned. If no string or I18n values exist, a null is returned.
         *
         * @return The first string value of the metadata entry
         */
        @JsonIgnore
        public String getString() {
            if (hasValues()) {
                return myValues.get(0).getValue();
            }

            return null;
        }

        /**
         * Gets the metadata entry's string values.
         *
         * @return The metadata entry's string values
         */
        @JsonIgnore
        public List<Value> getValues() {
            return myValues;
        }

        /**
         * Sets the supplied values in the metadata entry, clearing any previously existing ones.
         *
         * @param aValue A list of values
         * @return The metadata entry
         */
        @JsonIgnore
        public Entry setValues(final String... aValue) {
            myValues.clear();
            return addValues(aValue);
        }

        /**
         * Sets the supplied values in the metadata entry, clearing any previously existing ones.
         *
         * @param aValue A list of values
         * @return The metadata entry
         */
        @JsonIgnore
        public Entry setValues(final Value... aValue) {
            myValues.clear();
            return addValues(aValue);
        }

        /**
         * Adds the supplied values to the metadata entry.
         *
         * @param aValue A list of values
         * @return The metadata entry
         */
        public final Entry addValues(final String... aValue) {
            Objects.requireNonNull(aValue, LOGGER.getMessage(MessageCodes.JPA_001));

            for (final String value : aValue) {
                Objects.requireNonNull(value, LOGGER.getMessage(MessageCodes.JPA_001));

                if (!myValues.add(new Value(value))) {
                    throw new UnsupportedOperationException();
                }
            }

            return this;
        }

        /**
         * Adds the supplied values to the metadata entry.
         *
         * @param aValue A list of values
         * @return The metadata entry
         */
        public final Entry addValues(final Value... aValue) {
            Objects.requireNonNull(aValue, LOGGER.getMessage(MessageCodes.JPA_001));

            for (final Value value : aValue) {
                Objects.requireNonNull(value, LOGGER.getMessage(MessageCodes.JPA_001));

                if (!myValues.add(value)) {
                    throw new UnsupportedOperationException();
                }
            }

            return this;
        }

        /**
         * Returns the metadata entry's values.
         *
         * @return The metadata entry's values
         */
        @JsonGetter(Constants.VALUE)
        @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.UnusedPrivateMethod" })
        private Object getJsonValue() {
            if (!hasValues()) {
                return null;
            }

            if (myValues.size() == Constants.SINGLE_INSTANCE && !myValues.get(0).getLang().isPresent()) {
                return myValues.get(0).getValue();
            }

            final List<Object> list = new ArrayList<>();
            final Iterator<Value> iterator = myValues.iterator();

            while (iterator.hasNext()) {
                final Value entry = iterator.next();

                if (entry.getLang().isPresent()) {
                    list.add(entry);
                } else {
                    list.add(entry.getValue());
                }
            }

            return list;
        }
    }
}
