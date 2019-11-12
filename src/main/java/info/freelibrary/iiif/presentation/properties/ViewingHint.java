
package info.freelibrary.iiif.presentation.properties;

import static info.freelibrary.iiif.presentation.utils.Constants.BUNDLE_NAME;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * A hint to the client as to the most appropriate method of displaying the resource. This specification defines the
 * values specified in the table below. Other values may be given, and if they are, they must be URIs.
 */
public class ViewingHint {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewingHint.class, BUNDLE_NAME);

    /**
     * The supported out of the box viewing hints: individuals, paged, continuous, multi-part, non-paged, top, and
     * facing-pages.
     */
    public enum Option {

        INDIVIDUALS("individuals"), PAGED("paged"), CONTINUOUS("continuous"), MULTIPART("multi-part"), NONPAGED(
                "non-paged"), TOP("top"), FACINGPAGES("facing-pages");

        private final String myValue;

        Option(final String aHint) {
            myValue = aHint;
        }

        @Override
        public String toString() {
            return myValue;
        }

    }

    private final List<Value> myValues = new ArrayList<>();

    /**
     * Creates a new viewing hint from one of the specified options.
     *
     * @param aValue A viewing hint
     */
    public ViewingHint(final Option... aValue) {
        for (final Option opt : aValue) {
            myValues.add(new Value(opt));
        }
    }

    /**
     * Creates a new viewing hint from a URI.
     *
     * @param aValue A viewing hint
     */
    public ViewingHint(final URI... aValue) {
        for (final URI uri : aValue) {
            myValues.add(new Value(uri));
        }
    }

    /**
     * Creates a new viewing hint from a String.
     *
     * @param aValue A viewing hint
     */
    public ViewingHint(final String... aValue) {
        for (final String value : aValue) {
            myValues.add(new Value(value));
        }
    }

    /**
     * Returns the first string value, regardless whether it's an Option value or a user supplied URI.
     *
     * @return The first string value
     */
    @JsonIgnore
    public String getString() {
        return myValues.get(0).getString();
    }

    /**
     * Returns all values as strings, regardless of whether they're from options or a user supplied URI.
     *
     * @return All the viewing hint strings
     */
    @JsonIgnore
    public List<String> getStrings() {
        final List<String> strings = new ArrayList<>();

        for (final Value value : myValues) {
            strings.add(value.getString());
        }

        return strings;
    }

    /**
     * Gets the first value of the viewing hint.
     *
     * @return The viewing hint value
     */
    @JsonIgnore
    public Value getValue() {
        return myValues.get(0);
    }

    /**
     * Gets a list of all the viewing hint's values.
     *
     * @return A list of all the viewing hint's values
     */
    @JsonIgnore
    public List<Value> getValues() {
        return myValues;
    }

    /**
     * Adds the value(s) of the viewing hint.
     *
     * @param aValue The viewing hint value
     * @return The viewing hint
     */
    public ViewingHint addValue(final Option... aValue) {
        for (final Option value : aValue) {
            myValues.add(new Value(value));
        }

        return this;
    }

    /**
     * Sets the value of the viewing hint to one of the out of the box options. This deletes all previous values.
     *
     * @param aValue The viewing hint value
     * @return The viewing hint
     */
    public ViewingHint setValue(final Option... aValue) {
        myValues.clear();
        return addValue(aValue);
    }

    /**
     * Sets the value of the viewing hint to a URI value. The deletes all previous values.
     *
     * @param aValue The viewing hint value
     * @return The viewing hint
     */
    public ViewingHint setValue(final URI... aValue) {
        myValues.clear();
        return addValue(aValue);
    }

    /**
     * Adds the value(s) to the viewing hint.
     *
     * @param aValue The viewing hint value
     * @return The viewing hint
     */
    public ViewingHint addValue(final URI... aValue) {
        for (final URI uri : aValue) {
            myValues.add(new Value(uri));
        }

        return this;
    }

    /**
     * Sets the value of the viewing hint. The deletes all previous values.
     *
     * @param aValue The viewing hint value
     * @return The viewing hint
     */
    public ViewingHint setValue(final String... aValue) {
        myValues.clear();
        return addValue(aValue);
    }

    /**
     * Adds the value(s) to the viewing hint.
     *
     * @param aValue New values to add to the viewing hint
     * @return The viewing hint
     */
    public ViewingHint addValue(final String... aValue) {
        final ViewingHint.Value[] array = new ViewingHint.Value[aValue.length];
        int index = 0;

        // Make sure the supplied values are valid before adding
        for (final String value : aValue) {
            array[index++] = new Value(value);
        }

        if (!Collections.addAll(myValues, array)) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Gets the raw value of the viewing hint
     * <p>
     * For a viewing hint with a single value this will be a <code>String</code> and for a viewing hint with multiple
     * values this will be a <code>List&lt;ViewingHint.Value&gt;</code>.
     * </p>
     *
     * @return The value of the viewing hint
     */
    @JsonGetter(Constants.VIEWING_HINT)
    private Object getValueJson() {
        if (myValues.size() == 1) {
            return myValues.get(0).getString();
        } else if (myValues.size() > 1) {
            return myValues;
        } else {
            return null;
        }
    }

    /**
     * A value of the viewing hint.
     */
    public class Value {

        private URI myURI;

        private Option myOption;

        /**
         * Create a new viewing hint from the supplied option.
         *
         * @param aOption A predefined list of values for the viewing hint
         */
        public Value(final Option aOption) {
            myOption = aOption;
        }

        /**
         * Create a new viewing hint from the supplied string.
         *
         * @param aValue A string representation of the viewing hint value
         */
        public Value(final String aValue) {
            final String value = aValue.toUpperCase(Locale.US).replaceAll("\\-", "");

            for (final Option option : Option.values()) {
                if (option.name().equals(value)) {
                    myOption = Option.valueOf(value);
                    break;
                }
            }

            if (myOption == null) {
                try {
                    myURI = URI.create(aValue);
                } catch (final IllegalArgumentException uriDetails) {
                    throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_010, aValue), uriDetails);
                }
            }
        }

        /**
         * Create a new viewing hint from the supplied URI.
         *
         * @param aURI A URI from which to create the viewing hint
         */
        public Value(final URI aURI) {
            myURI = aURI;
        }

        @Override
        public String toString() {
            final String value = getString();
            return value == null ? super.toString() : value;
        }

        /**
         * Gets a string representation of the viewing hint.
         *
         * @return A string representation of the viewing hint
         */
        @JsonValue
        public String getString() {
            final String result;

            if ((myURI == null) && (myOption == null)) {
                result = null;
            } else if (myOption == null) {
                result = myURI.toString();
            } else {
                return myOption.toString();
            }

            return result;
        }

        /**
         * Determines whether the viewing hint's value is a user input URI.
         *
         * @return True if the value is a URI; else, false
         */
        public boolean isURI() {
            return myURI != null;
        }

        /**
         * Gets the viewing hint if it's a URI.
         *
         * @return The viewing hint URI value
         * @throws ClassCastException If the value is an Option, not a URI
         */
        public URI getURI() {
            if (myURI == null) {
                throw new ClassCastException("Value is an Option, not a URI");
            }

            return myURI;
        }

        /**
         * Gets the viewing hint if it's an pre-canned Option.
         *
         * @return The viewing hint Option value
         * @throws ClassCastException If the value is a URI instead of an Option
         */
        public Option getOption() {
            if (myOption == null) {
                throw new ClassCastException("Value is a URI, not an Option");
            }

            return myOption;
        }
    }

}
