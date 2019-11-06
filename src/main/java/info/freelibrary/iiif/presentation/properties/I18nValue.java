
package info.freelibrary.iiif.presentation.properties;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * A property value that can be used to handle IIIF's
 * <a href= "http://iiif.io/api/presentation/2.1/#language-of-property-values">language of property values</a> JSON-LD
 * consideration. This pattern may be used in label, description, attribution and the label and value fields of the
 * metadata construction. It's more complex than a simple string or array of string values, so warrants its own class.
 */
@JsonPropertyOrder({ "@value", "@language" })
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class I18nValue {

    private String myValue;

    private Optional<String> myLang;

    /**
     * Creates a new property value.
     *
     * @param aValue A property value
     */
    public I18nValue(final String aValue) {
        this(aValue, (String) null);
    }

    /**
     * Creates an I18n value for use in label, description, attribution and metadata properties.
     *
     * @param aValue A string value
     * @param aLang A language code
     */
    public I18nValue(final String aValue, final String aLang) {
        myValue = aValue;
        myLang = Optional.ofNullable(aLang);
    }

    /**
     * Gets the string value of the value.
     *
     * @return The string value
     */
    @JsonGetter(Constants.I18N_VALUE)
    public String getValue() {
        return myValue;
    }

    /**
     * Gets the language code of the value.
     *
     * @return The language code
     */
    @JsonGetter(Constants.I18N_LANG)
    public Optional<String> getLang() {
        return myLang;
    }

    /**
     * Sets the string value of the value.
     *
     * @param aValue The string value to set
     * @return The value
     */
    @JsonSetter(Constants.I18N_VALUE)
    public I18nValue setValue(final String aValue) {
        myValue = aValue;
        return this;
    }

    /**
     * Sets the language code of the value.
     *
     * @param aLang The language code
     * @return The value
     */
    @JsonSetter(Constants.I18N_LANG)
    public I18nValue setLang(final String aLang) {
        myLang = Optional.ofNullable(aLang);
        return this;
    }

}
