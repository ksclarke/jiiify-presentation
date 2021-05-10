
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * Interface that allows resources to be localized.
 */
public interface Localized<T> {

    /**
     * Gets this localized external resource's languages.
     *
     * @return The localized external resource's languages
     */
    @JsonIgnore
    List<String> getLanguages();

    /**
     * Sets the languages for this localized external resource.
     *
     * @param aLangArray The languages to set
     * @return The localized external resource
     * @throws IllegalArgumentException If the language tag is invalid
     */
    @JsonIgnore
    default Localized<T> setLanguages(final String... aLangArray) {
        final List<String> languages = getLanguages();

        languages.clear();

        for (final String element : aLangArray) {
            final String tag = Locale.forLanguageTag(element).toLanguageTag();

            if ("und".equals(tag)) {
                final Logger logger = LoggerFactory.getLogger(Localized.class, MessageCodes.BUNDLE);
                throw new IllegalArgumentException(logger.getMessage(MessageCodes.JPA_020, element));
            }

            languages.add(tag);
        }

        return this;
    }
}
