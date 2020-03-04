
package info.freelibrary.iiif.presentation.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.StringUtils;

/**
 * A base class for label, summary, attribution and metadata's label and value fields.
 */
class I18nProperty<T extends I18nProperty<T>> {

    protected final List<I18n> myI18ns;

    /**
     * Creates a property from an array of internationalizations.
     *
     * @param aName A name of the property
     * @param aI18nArray An array of internationalizations for the property
     */
    I18nProperty(final I18n... aI18nArray) {
        myI18ns = new ArrayList<>();
        addI18ns(aI18nArray);
    }

    /**
     * Creates a property from an array of string(s).
     *
     * @param aName A name of the property
     * @param aStringArray An array of strings for the property
     */
    I18nProperty(final String... aStringArray) {
        myI18ns = new ArrayList<>();
        addStrings(aStringArray);
    }

    /**
     * Sets the string value of the property, removing all other previous strings.
     *
     * @param aStringArray A string value
     * @return True if the property's value was set
     */
    @JsonIgnore
    public T setStrings(final String... aStringArray) {
        myI18ns.clear();
        return addStrings(aStringArray);
    }

    /**
     * Sets the internationalization of the property, removing all other previous internationalizations.
     *
     * @param aI18nArray An array of I18n(s).
     * @return True if the property's value was set
     */
    public T setI18ns(final I18n... aI18nArray) {
        myI18ns.clear();
        return addI18ns(aI18nArray);
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
        } else {
            return null;
        }
    }

    /**
     * Adds a string value to the property.
     *
     * @param aStringArray An array of strings to add to the property
     * @return The property
     */
    public T addStrings(final String... aStringArray) {
        Objects.requireNonNull(aStringArray, MessageCodes.JPA_001);

        for (final String string : aStringArray) {
            Objects.requireNonNull(string, MessageCodes.JPA_001);

            if (!myI18ns.add(new I18n(I18n.DEFAULT_LANG, string))) {
                throw new UnsupportedOperationException();
            }
        }

        return (T) this;
    }

    /**
     * Adds an internationalization to the property.
     *
     * @param aI18nArray A list of internationalizations
     * @return The property
     */
    public T addI18ns(final I18n... aI18nArray) {
        Objects.requireNonNull(aI18nArray, MessageCodes.JPA_001);

        for (final I18n i18n : aI18nArray) {
            Objects.requireNonNull(i18n, MessageCodes.JPA_001);

            if (!myI18ns.add(i18n)) {
                throw new UnsupportedOperationException();
            }
        }

        return (T) this;
    }

    /**
     * Returns whether the property has any internationalizations.
     *
     * @return True if the property has internationalizations; else, false
     */
    public boolean hasStrings() {
        return !myI18ns.isEmpty();
    }

    @Override
    public boolean equals(final Object aObject) {
        if (aObject != null && getClass().getName().equals(aObject.getClass().getName())) {
            return toMap().equals(((I18nProperty) aObject).toMap());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return toMap().hashCode();
    }

    @Override
    public String toString() {
        if (hasStrings()) {
            final Iterator<I18n> iterator = myI18ns.iterator();
            final StringBuilder builder = new StringBuilder();
            final String eol = System.lineSeparator();

            while (iterator.hasNext()) {
                final I18n i18n = iterator.next();
                final String[] strings = i18n.getStrings().toArray(new String[i18n.size()]);

                builder.append(i18n.getLang()).append('=').append(StringUtils.toString(strings, '|')).append(eol);
            }

            return builder.toString();
        } else {
            return null;
        }
    }

    /**
     * Gets the JSON value of the property.
     *
     * @return The value(s) of the property
     */
    @JsonValue
    protected Object toMap() {
        if (hasStrings()) {
            final Map<String, Object> map = new LinkedHashMap<>(); // maintains insertion order
            final Iterator<I18n> iterator = myI18ns.iterator();

            while (iterator.hasNext()) {
                final I18n i18n = iterator.next();

                map.put(i18n.getLang(), i18n.getStrings());
            }

            return map;
        } else {
            return null;
        }
    }

}
