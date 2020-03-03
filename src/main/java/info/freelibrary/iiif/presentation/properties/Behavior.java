
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
 * A set of user experience features that the publisher of the content would prefer the client to use when presenting
 * the resource.
 */
public class Behavior {

    private static final Logger LOGGER = LoggerFactory.getLogger(Behavior.class, BUNDLE_NAME);

    /**
     * The supported out of the box behaviors: individuals, paged, continuous, multi-part, non-paged, top, and
     * facing-pages.
     */
    public enum Option {

        AUTOADVANCE("auto-advance"), NOAUTOADVANCE("no-auto-advance"), REPEAT("repeat"), NOREPEAT("no-repeat"),
        UNORDERED("unordered"), INDIVIDUALS("individuals"), PAGED("paged"), CONTINUOUS("continuous"), MULTIPART(
                "multi-part"), NONPAGED("non-paged"), TOP("top"), FACINGPAGES("facing-pages"), THUMBNAILNAV(
                        "thumbnail-nav"), NONAV("no-nav"), HIDDEN("hidden");

        private final String myValue;

        Option(final String aBehavior) {
            myValue = aBehavior;
        }

        @Override
        public String toString() {
            return myValue;
        }

    }

    private final List<Value> myValues = new ArrayList<>();

    /**
     * Creates a new behavior from one of the specified options.
     *
     * @param aOptionArray A behavior option
     */
    public Behavior(final Option... aOptionArray) {
        for (final Option opt : aOptionArray) {
            myValues.add(new Value(opt));
        }
    }

    /**
     * Creates a new behavior from a URI.
     *
     * @param aUriArray A behavior in URI form
     */
    public Behavior(final URI... aUriArray) {
        for (final URI uri : aUriArray) {
            myValues.add(new Value(uri));
        }
    }

    /**
     * Creates a new behavior from a String.
     *
     * @param aStringArray A behavior in string form
     */
    public Behavior(final String... aStringArray) {
        for (final String string : aStringArray) {
            myValues.add(new Value(string));
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
     * @return All the behavior strings
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
     * Gets the first value of the behavior.
     *
     * @return The behavior value
     */
    @JsonIgnore
    public Value getValue() {
        return myValues.get(0);
    }

    /**
     * Gets a list of all the behavior's values.
     *
     * @return A list of all the behavior's values
     */
    @JsonIgnore
    public List<Value> getValues() {
        return myValues;
    }

    /**
     * Adds the value(s) of the behavior.
     *
     * @param aOptionArray An array of behavior options
     * @return The behavior
     */
    public Behavior addValue(final Option... aOptionArray) {
        for (final Option option : aOptionArray) {
            myValues.add(new Value(option));
        }

        return this;
    }

    /**
     * Sets the value of the behavior to one of the out of the box options. This deletes all previous values.
     *
     * @param aOptionArray Behavior options
     * @return The behavior
     */
    public Behavior setValue(final Option... aOptionArray) {
        myValues.clear();
        return addValue(aOptionArray);
    }

    /**
     * Sets the value of the behavior to an array of URIs. The deletes all previous values.
     *
     * @param aUriArray Behavior URIs
     * @return The behavior
     */
    public Behavior setValue(final URI... aUriArray) {
        myValues.clear();
        return addValue(aUriArray);
    }

    /**
     * Adds the value(s) to the behavior.
     *
     * @param aUriArray The behavior value
     * @return The behavior
     */
    public Behavior addValue(final URI... aUriArray) {
        for (final URI uri : aUriArray) {
            myValues.add(new Value(uri));
        }

        return this;
    }

    /**
     * Sets the value of the behavior. The deletes all previous values.
     *
     * @param aStringArray The behavior value in string form
     * @return The behavior
     */
    public Behavior setValue(final String... aStringArray) {
        myValues.clear();
        return addValue(aStringArray);
    }

    /**
     * Adds the value(s) to the behavior.
     *
     * @param aStringArray New values, in string form, to add to the behavior
     * @return The behavior
     */
    public Behavior addValue(final String... aStringArray) {
        final Behavior.Value[] array = new Behavior.Value[aStringArray.length];
        int index = 0;

        // Make sure the supplied values are valid before adding
        for (final String value : aStringArray) {
            array[index++] = new Value(value);
        }

        if (!Collections.addAll(myValues, array)) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Gets the raw value of the behavior
     * <p>
     * For a behavior with a single value this will be a <code>String</code> and for a behavior with multiple values
     * this will be a <code>List&lt;Behavior.Value&gt;</code>.
     * </p>
     *
     * @return The value of the behavior
     */
    @JsonGetter(Constants.BEHAVIOR)
    private Object getJsonValue() {
        if (myValues.size() == 1) {
            return myValues.get(0).getString();
        } else if (myValues.size() > 1) {
            return myValues;
        } else {
            return null;
        }
    }

    /**
     * A value of the behavior.
     */
    public class Value {

        private URI myURI;

        private Option myOption;

        /**
         * Create a new behavior from the supplied option.
         *
         * @param aOption A predefined list of values for the behavior
         */
        public Value(final Option aOption) {
            myOption = aOption;
        }

        /**
         * Create a new behavior from the supplied string.
         *
         * @param aString A string representation of the behavior value
         */
        public Value(final String aString) {
            final String value = aString.toUpperCase(Locale.US).replaceAll("\\-", "");

            for (final Option option : Option.values()) {
                if (option.name().equals(value)) {
                    myOption = Option.valueOf(value);
                    break;
                }
            }

            if (myOption == null) {
                try {
                    myURI = URI.create(aString);
                } catch (final IllegalArgumentException uriDetails) {
                    throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_010, aString), uriDetails);
                }
            }
        }

        /**
         * Create a new behavior from the supplied URI.
         *
         * @param aURI A URI from which to create the behavior
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
         * Gets a string representation of the behavior.
         *
         * @return A string representation of the behavior
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
         * Determines whether the behavior's value is a user input URI.
         *
         * @return True if the value is a URI; else, false
         */
        public boolean isURI() {
            return myURI != null;
        }

        /**
         * Gets the behavior if it's a URI.
         *
         * @return The behavior URI value
         * @throws ClassCastException If the value is an Option, not a URI
         */
        public URI getURI() {
            if (myURI == null) {
                throw new ClassCastException("Value is an Option, not a URI");
            }

            return myURI;
        }

        /**
         * Gets the behavior if it's an pre-canned Option.
         *
         * @return The behavior Option value
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
