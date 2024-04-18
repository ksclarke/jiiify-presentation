
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.util.StringUtils;

/**
 * A base class for label, summary, attribution, property, and metadata's label and value fields.
 */
class I18nProperty<T extends I18nProperty<T>> {

    /**
     * A list of internationalized values.
     */
    private final List<I18n> myI18ns;

    /**
     * Creates an I18n property from an array of internationalizations. If the passed array contains a null, it will be
     * dropped.
     *
     * @param aI18nArray An array of internationalizations
     */
    I18nProperty(final I18n... aI18nArray) {
        myI18ns = Arrays.stream(aI18nArray).filter(i18n -> i18n != null).collect(Collectors.toList());
    }

    /**
     * Creates an I18n property from a list of internationalizations. If the passed list contains a null, it will be
     * dropped.
     *
     * @param aI18nList A list of internationalizations
     */
    I18nProperty(final List<I18n> aI18nList) {
        myI18ns = aI18nList.stream().filter(i18n -> i18n != null).collect(Collectors.toList());
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
     * Returns a hash code for this property.
     *
     * @return A hash code
     */
    @Override
    public int hashCode() {
        return toMap().hashCode();
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
     * Returns a string representation of this property.
     *
     * @return A string representation of this property
     */
    @Override
    public String toString() {
        final StringBuilder builder;

        if (!hasStrings()) {
            return null;
        }

        builder = new StringBuilder();

        for (final I18n i18n : myI18ns) {
            final String[] strings = i18n.getStrings().toArray(new String[i18n.size()]);

            builder.append(i18n.getLang()).append('=');
            builder.append(StringUtils.toString(strings, '|')).append(System.lineSeparator());
        }

        return builder.toString();
    }

    /**
     * Sets the internationalization(s) of the property, removing all other previous internationalizations.
     *
     * @param aI18nArray An array of I18n(s).
     * @return This property
     */
    protected I18nProperty<T> setI18ns(final I18n... aI18nArray) {
        myI18ns.clear();
        Arrays.stream(aI18nArray).filter(Objects::isNull).forEach(myI18ns::add);
        return this;
    }

    /**
     * Sets the internationalization(s) of the property, removing all other previous internationalizations.
     *
     * @param aI18nList A list of I18n(s).
     * @return This property
     */
    protected I18nProperty<T> setI18ns(final List<I18n> aI18nList) {
        myI18ns.clear();
        aI18nList.stream().filter(Objects::isNull).forEach(myI18ns::add);
        return this;
    }

    /**
     * Gets the JSON value of the property.
     *
     * @return The value(s) of the property
     */
    @JsonValue
    protected Object toMap() {
        final Map<String, Object> map;

        if (!hasStrings()) {
            return null;
        }

        map = new LinkedHashMap<>(); // maintains insertion order

        for (final I18n i18n : myI18ns) {
            map.put(i18n.getLang(), i18n.getStrings());
        }

        return map;
    }

}
