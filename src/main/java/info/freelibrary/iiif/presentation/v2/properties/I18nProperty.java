
package info.freelibrary.iiif.presentation.v2.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v2.utils.Constants;
import info.freelibrary.iiif.presentation.v2.utils.MessageCodes;

/**
 * A property value that can be used in the label, description, attribution and the label and value fields.
 */
class I18nProperty<T extends I18nProperty<T>> {

    /**
     * The I18n property's logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(I18nProperty.class, MessageCodes.BUNDLE);

    /**
     * The I18n property's list of values.
     */
    private final List<Value> myValues;

    /**
     * Creates a property from a list of I18n Value(s).
     *
     * @param aValue An array of values for the property
     */
    I18nProperty(final Value... aValue) {
        myValues = new ArrayList<>();
        Collections.addAll(myValues, aValue);
    }

    /**
     * Creates a property from a list of String(s).
     *
     * @param aValue An array of values for the property
     */
    I18nProperty(final String... aValue) {
        final List<Value> values = new ArrayList<>();

        for (final String value : aValue) {
            values.add(new Value(value));
        }

        myValues = new ArrayList<>();
        Collections.addAll(myValues, values.toArray(new Value[0]));
    }

    /**
     * Sets the string value of the property, removing all other previous values.
     *
     * @param aValue A string value
     * @return True if the property's value was set
     */
    protected I18nProperty<T> setValue(final String... aValue) {
        myValues.clear();
        return addValue(aValue);
    }

    /**
     * Gets a List of the property's String values.
     *
     * @return A List of the property's String values
     */
    public List<Value> getValues() {
        return myValues;
    }

    /**
     * Returns the first string value (regardless of language) if any. If there isn't one it returns a null;
     *
     * @return A String value for the property
     */
    public String getString() {
        if (hasValues()) {
            return myValues.get(0).getValue();
        }
        return null;
    }

    /**
     * Adds a String value to the property. Useful for adding secondary or tertiary, etc., values.
     *
     * @param aValue A String property value
     * @return A String value to the property
     * @throws UnsupportedOperationException If a supplied value could not be added
     */
    protected I18nProperty<T> addValue(final String... aValue) {
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
     * Adds a I18n Value to the property. Useful for adding secondary or tertiary, etc., values.
     *
     * @param aValue A I18N property value
     * @return A String value to the property
     * @throws UnsupportedOperationException If a supplied value could not be added
     */
    protected I18nProperty<T> addValue(final Value... aValue) {
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
     * Returns whether the property has a value.
     *
     * @return True if the property has a value; else, false
     */
    public boolean hasValues() {
        return !myValues.isEmpty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(final Object aObject) {
        if (aObject != null && getClass().getName().equals(aObject.getClass().getName())) {
            return getJsonValue().equals(((I18nProperty<T>) aObject).getJsonValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getJsonValue().hashCode();
    }

    /**
     * Gets the value(s) of the property; this might be a single String or it might be a List that contains plain
     * strings and/or I18n Value(s).
     *
     * @return The value(s) of the property
     */
    @JsonValue
    protected Object getJsonValue() {
        final List<Object> list = new ArrayList<>();

        if (!hasValues()) {
            return null;
        }

        if (myValues.size() == Constants.SINGLE_INSTANCE) {
            if (myValues.get(0).getLang().isPresent()) {
                return myValues.get(0);
            }
            return myValues.get(0).getValue();
        }

        for (final Value entry : myValues) {
            if (entry.getLang().isPresent()) {
                list.add(entry);
            } else {
                list.add(entry.getValue());
            }
        }

        return list;
    }
}
