
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.Constants;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * Interface that allows resources to be localized.
 */
public interface Localized<T> {

    /**
     * Gets this localized external resource's languages.
     *
     * @return The localized external resource's languages
     */
    @JsonGetter(Constants.LANGUAGE)
    @JsonInclude(Include.NON_EMPTY)
    List<String> getLanguages();

    /**
     * Sets the languages for this localized external resource.
     *
     * @param aLangArray The languages to set
     * @return The localized external resource
     * @throws IllegalArgumentException If the language tag is invalid
     */
    @JsonSetter(Constants.LANGUAGE)
    default Localized<T> setLanguages(final String... aLangArray) throws IllegalArgumentException {
        final List<String> languages = getLanguages();

        languages.clear();

        for (int index = 0; index < aLangArray.length; index++) {
            final String tag = Locale.forLanguageTag(aLangArray[index]).toLanguageTag();

            if ("und".equals(tag)) {
                final Logger logger = LoggerFactory.getLogger(Localized.class, MessageCodes.BUNDLE);
                throw new IllegalArgumentException(logger.getMessage(MessageCodes.JPA_020, aLangArray[index]));
            }

            languages.add(tag);
        }

        return this;
    }
}
