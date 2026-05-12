
package info.freelibrary.iiif.presentation.v3.properties;

import static java.util.stream.Collectors.toCollection;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.freelibrary.iiif.presentation.v3.utils.I18nUtils;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.ValueDeserializer;

import java.util.ArrayList;
import java.util.List;

/**
 * A human-readable internationalized value.
 */
@JsonDeserialize(using = ValueDeserializer.class)
public class Value extends I18nProperty<Value> {

    /**
     * Creates a value from the supplied internationalization(s).
     *
     * @param aI18nArray An array of internationalizations for the value
     */
    public Value(final I18n... aI18nArray) {
        super(aI18nArray);
    }

    /**
     * Creates a value from the supplied internationalization(s).
     *
     * @param aI18nList A list of internationalizations for the value
     */
    public Value(final List<I18n> aI18nList) { super(aI18nList); }

    /**
     * Creates a value using the 'none' language tag.
     *
     * @param aValue A value
     */
    public Value(final String aValue) {
        this(new I18n(I18n.DEFAULT_LANG, aValue, true));
    }

    /**
     * Creates a value from the supplied internationalization(s). The submitted array should alternate between language
     * code and string value.
     *
     * @param aDataArray An array of language codes and string values
     */
    public Value(final String... aDataArray) {
        super(I18nUtils.parseArray(true, aDataArray));
    }

    /**
     * Creates a value using the supplied language tag and value.
     *
     * @param aLangTag A language tag
     * @param aValue A value of the label
     */
    public Value(final String aLangTag, final String aValue) {
        this(new I18n(aLangTag, aValue, true));
    }

    /**
     * Creates a value from another value.
     *
     * @param aValue The value to deep copy
     */
    public Value(final Value aValue) {
        super(aValue.getI18ns().stream().map(I18n::new).collect(toCollection(ArrayList::new)));
    }

    /**
     * Creates a copy of the value.
     *
     * @return A copy of the value
     */
    public Value copy() {
        return new Value(this);
    }

    @Override
    @JsonGetter(JsonKeys.VALUE)
    protected Object toMap() {
        return super.toMap();
    }

}
