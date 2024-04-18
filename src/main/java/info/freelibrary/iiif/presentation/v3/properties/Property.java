
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.Objects;

/**
 * A generic property that can be used in navPlace features(s).
 */
public class Property extends I18nProperty<Property> {

    /** The name of the property. */
    private String myName;

    /**
     * Creates a new property from the supplied name and {@code I18n} language code and value.
     *
     * @param aName A property name
     * @param aValue A value of the property
     */
    public Property(final String aName, final String aValue) {
        super(new I18n(I18n.DEFAULT_LANG, aValue));
        myName = Objects.requireNonNull(aName);
    }

    /**
     * Creates a new property from the supplied name and {@code I18n} language code and value.
     *
     * @param aName A property name
     * @param aLangCode A language code for the property
     * @param aValue A value of the property
     */
    public Property(final String aName, final String aLangCode, final String aValue) {
        super(new I18n(aLangCode, aValue));
        myName = Objects.requireNonNull(aName);
    }

    /**
     * Creates a new property from the supplied name and {@code I18n}s.
     *
     * @param aName A property name
     * @param aI18nArray An array of property values
     */
    public Property(final String aName, final I18n... aI18nArray) {
        super(aI18nArray);
        myName = Objects.requireNonNull(aName);
    }

    /**
     * A private constructor just used by Jackson for its deserialization process.
     */
    private Property() {
        super(new I18n[] {});
    }

    /**
     * Gets the name of this property.
     *
     * @return The property name
     */
    public String getName() {
        return myName;
    }

    /**
     * Sets the name of this property.
     *
     * @param aName The name of this property
     * @return This property
     */
    public Property setName(final String aName) {
        myName = aName;
        return this;
    }

}
