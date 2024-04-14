
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.Objects;

import info.freelibrary.iiif.presentation.v3.utils.I18nUtils;

/**
 * A generic property that can be used in navPlace features(s).
 */
public class Property extends I18nProperty<Property> {

    /** The name of the property. */
    private String myName;

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
     * Creates a new property from the supplied name and I18n values matrix.
     *
     * @param aName A property name
     * @param aMatrix A matrix of strings that can be composed into a series of I18ns
     */
    @SuppressWarnings("PMD.UseVarargs")
    public Property(final String aName, final String[][] aMatrix) {
        super(I18nUtils.createI18ns(false, null, aMatrix));
        myName = Objects.requireNonNull(aName);
    }

    /**
     * Creates a new property from the supplied name, default language tag, and I18n values matrix.
     *
     * @param aName A property name
     * @param aDefaultLangTag A default language tag to use with the supplied matrix
     * @param aMatrix A matrix of strings that can be composed into a series of I18ns
     */
    @SuppressWarnings("PMD.UseVarargs")
    public Property(final String aName, final String aDefaultLangTag, final String[][] aMatrix) {
        super(I18nUtils.createI18ns(false, aDefaultLangTag, aMatrix));
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
