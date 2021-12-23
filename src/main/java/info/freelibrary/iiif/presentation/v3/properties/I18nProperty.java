
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A base class for label, summary, attribution and metadata's label and value fields.
 */
class I18nProperty<T extends I18nProperty<T>> {

    /**
     * The logger used by I18nProperty.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(I18nProperty.class, MessageCodes.BUNDLE);

    /**
     * A list of internationalized values.
     */
    protected final List<I18n> myI18ns;

    /**
     * Creates a property from an array of internationalizations.
     *
     * @param aI18nArray An array of internationalizations for the property
     */
    I18nProperty(final I18n... aI18nArray) {
        myI18ns = new ArrayList<>();
        addCheckedI18ns(aI18nArray);
    }

    /**
     * Creates a property from an array of string(s).
     *
     * @param aStringArray An array of strings for the property
     */
    I18nProperty(final String... aStringArray) {
        myI18ns = new ArrayList<>();
        addCheckedStrings(aStringArray);
    }

    /**
     * Creates a property for the deserialization process.
     */
    protected I18nProperty() {
        myI18ns = new ArrayList<>();
    }

    /**
     * Sets the string value of the property, removing all other previous strings.
     *
     * @param aStringArray A string value
     * @return True if the property's value was set
     */
    @JsonIgnore
    protected I18nProperty<T> setStrings(final String... aStringArray) {
        myI18ns.clear();
        return addCheckedStrings(aStringArray);
    }

    /**
     * Sets the internationalization of the property, removing all other previous internationalizations.
     *
     * @param aI18nArray An array of I18n(s).
     * @return True if the property's value was set
     */
    protected I18nProperty<T> setI18ns(final I18n... aI18nArray) {
        myI18ns.clear();
        return addCheckedI18ns(aI18nArray);
    }

    /**
     * Gets a list of the property's internationalizations.
     *
     * @return A list of the property's internationalizations
     */
    public List<I18n> getI18ns() {
        return myI18ns;
    }

    /**
     * Returns the first string value (regardless of language). If there isn't one it returns a null;
     *
     * @return A string value for the property
     */
    public String getString() {
        if (hasStrings()) {
            return myI18ns.get(0).getStrings().get(0);
        }
        return null;
    }

    /**
     * Returns whether the property has any internationalizations.
     *
     * @return True if the property has internationalizations; else, false
     */
    public boolean hasStrings() {
        return !myI18ns.isEmpty();
    }

    /**
     * Tests if the supplied object equals this one.
     *
     * @return True if they are equal; else, false
     */
    @Override
    public boolean equals(final Object aObject) {
        if (aObject != null && getClass().getName().equals(aObject.getClass().getName())) {
            return toMap().equals(((I18nProperty<?>) aObject).toMap());
        }
        return false;
    }

    /**
     * Returns a hash code for this property.
     *
     * @return A hash code
     */
    @Override
    public int hashCode() {
        return toMap().hashCode();
    }

    /**
     * Returns a string representation of this property.
     *
     * @return A string representation of this property
     */
    @Override
    public String toString() {
        if (!hasStrings()) {
            return null;
        }
        final Iterator<I18n> iterator = myI18ns.iterator();
        final StringBuilder builder = new StringBuilder();

        while (iterator.hasNext()) {
            final I18n i18n = iterator.next();
            final String[] strings = i18n.getStrings().toArray(new String[i18n.size()]);

            builder.append(i18n.getLang()).append('=');
            builder.append(StringUtils.toString(strings, '|')).append(System.lineSeparator());
        }

        return builder.toString();
    }

    /**
     * Adds a string value to the property.
     *
     * @param aStringArray An array of strings to add to the property
     * @return The property
     */
    protected I18nProperty<T> addStrings(final String... aStringArray) {
        return addCheckedStrings(aStringArray);
    }

    /**
     * Adds an internationalization to the property.
     *
     * @param aI18nArray A list of internationalizations
     * @return The property
     */
    protected I18nProperty<T> addI18ns(final I18n... aI18nArray) {
        return addCheckedI18ns(aI18nArray);
    }

    /**
     * Gets the JSON value of the property.
     *
     * @return The value(s) of the property
     */
    @JsonValue
    protected Object toMap() {
        if (!hasStrings()) {
            return null;
        }
        final Map<String, Object> map = new LinkedHashMap<>(); // maintains insertion order
        final Iterator<I18n> iterator = myI18ns.iterator();

        while (iterator.hasNext()) {
            final I18n i18n = iterator.next();

            map.put(i18n.getLang(), i18n.getStrings());
        }

        return map;
    }

    /**
     * Adds strings to the I18nProperty after they've been checked and confirmed to not be null.
     *
     * @param aStringArray An array of strings
     * @return The property
     */
    private I18nProperty<T> addCheckedStrings(final String... aStringArray) {
        Objects.requireNonNull(aStringArray, MessageCodes.JPA_001);

        for (final String string : aStringArray) {
            Objects.requireNonNull(string, MessageCodes.JPA_001);

            if (!myI18ns.add(new I18n(I18n.DEFAULT_LANG, string))) {
                throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_043, string));
            }
        }

        return this;
    }

    /**
     * Adds internationalized values to the I18nProperty after they've been checked and confirmed to not be null.
     *
     * @param aI18nArray An array of internationalized values
     * @return The property
     */
    private I18nProperty<T> addCheckedI18ns(final I18n... aI18nArray) {
        Objects.requireNonNull(aI18nArray, MessageCodes.JPA_001);

        for (final I18n i18n : aI18nArray) {
            Objects.requireNonNull(i18n, MessageCodes.JPA_001);

            if (!myI18ns.add(i18n)) {
                throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_043, i18n.toString()));
            }
        }

        return this;
    }
}
