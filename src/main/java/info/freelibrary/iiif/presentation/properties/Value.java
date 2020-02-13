
package info.freelibrary.iiif.presentation.properties;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.utils.Constants;

/**
 * An internationalized text value used by {@link Attribution}, {@link Summary}, and {@link Metadata}. It's more
 * complex than a simple string or array of string values, so warrants its own class.
 */
@JsonPropertyOrder({ "@value", "@language" })
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Value {

    private String myValue;

    private Optional<String> myLang;

    /**
     * Creates a new property value.
     *
     * @param aValue A property value
     */
    public Value(final String aValue) {
        this(aValue, (String) null);
    }

    /**
     * Creates an I18n value for use in label, summary, attribution and metadata properties.
     *
     * @param aValue A string value
     * @param aLang A language code
     */
    public Value(final String aValue, final String aLang) {
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
    public Value setValue(final String aValue) {
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
    public Value setLang(final String aLang) {
        myLang = Optional.ofNullable(aLang);
        return this;
    }

    @Override
    public boolean equals(final Object aObject) {
        if (aObject != null && aObject instanceof Value) {
            final Value value = (Value) aObject;
            final Optional<String> langOpt = value.getLang();
            final String otherLang = langOpt != null && langOpt.isPresent() ? langOpt.get() : "";
            final String otherValue = value.getValue() != null ? value.getValue() : "";
            final String thisLang = myLang != null && myLang.isPresent() ? myLang.get() : "";
            final String thisValue = myValue != null ? myValue : "";

            return thisValue.equals(otherValue) && thisLang.equals(otherLang);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 17;

        hash = hash * 23 + (myValue == null ? 1 : myValue.hashCode());
        hash = hash * 23 + (myLang == null || myLang.isEmpty() ? 1 : myLang.get().hashCode());

        return hash;
    }

}
