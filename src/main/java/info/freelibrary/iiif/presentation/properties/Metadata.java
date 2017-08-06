
package info.freelibrary.iiif.presentation.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.MessageCodes;
import info.freelibrary.iiif.presentation.helpers.Constants;

/**
 * A metadata property.
 * <p>
 * An example representation in JSON:
 * </p>
 * <code><pre>
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
 * </pre></code>
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class Metadata {

    private List<Metadata.Entry> myEntries;

    /**
     * Creates a metadata property.
     */
    public Metadata() {
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
     * Gets the metadata entries.
     *
     * @return The metadata entries
     */
    @JsonValue
    public List<Metadata.Entry> getEntries() {
        if (myEntries == null) {
            myEntries = new ArrayList<Metadata.Entry>();
        }

        return myEntries;
    }

    /**
     * A metadata entry with a label and values.
     *
     * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
     */
    @JsonPropertyOrder({ "label", "value" })
    public class Entry {

        private final String myLabel;

        private final List<Value> myValues;

        /**
         * Creates a metadata entry from the supplied label and string values.
         *
         * @param aLabel A metadata label
         * @param aValue A list of string values
         */
        public Entry(final String aLabel, final String... aValue) {
            Objects.requireNonNull(aLabel, MessageCodes.EXC_002);
            myValues = new ArrayList<>();
            myLabel = aLabel;
            addValues(aValue);
        }

        /**
         * Creates a metadata entry from the supplied label and I18n values.
         *
         * @param aLabel A metadata label
         * @param aValue A list of vI18n alues
         */
        public Entry(final String aLabel, final Value... aValue) {
            Objects.requireNonNull(aLabel, MessageCodes.EXC_002);
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
            return myValues.size() > 0;
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
         * Gets the first string value from the metadata entry; if the entry only contains I18n values, the string
         * value of the first language/value pair will be returned. If no string or I18n values exist, a null is
         * returned.
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
         * Returns the metadata entry's values.
         *
         * @return The metadata entry's values
         */
        @JsonGetter(Constants.VALUE)
        Object getValue() {
            if (hasValues()) {
                if ((myValues.size() == 1) && !myValues.get(0).getLang().isPresent()) {
                    return myValues.get(0).getValue();
                } else {
                    final List<Object> list = new ArrayList<>();
                    final Iterator<Value> iterator = myValues.iterator();

                    while (iterator.hasNext()) {
                        final Value entry = iterator.next();

                        if (!entry.getLang().isPresent()) {
                            list.add(entry.getValue());
                        } else {
                            list.add(entry);
                        }
                    }

                    return list;
                }
            } else {
                return null;
            }
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
        public Entry addValues(final String... aValue) {
            Objects.requireNonNull(aValue, MessageCodes.EXC_001);

            for (final String value : aValue) {
                Objects.requireNonNull(value, MessageCodes.EXC_001);

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
        public Entry addValues(final Value... aValue) {
            Objects.requireNonNull(aValue, MessageCodes.EXC_001);

            for (final Value value : aValue) {
                Objects.requireNonNull(value, MessageCodes.EXC_001);

                if (!myValues.add(value)) {
                    throw new UnsupportedOperationException();
                }
            }

            return this;
        }
    }
}
