package info.freelibrary.iiif.presentation.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IllformedLocaleException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.utils.Constants;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * Represents a localized external resource.
 */
abstract class Localized<T extends Localized<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Localized.class, Constants.BUNDLE_NAME);

    private final Locale.Builder myLocaleBuilder = new Locale.Builder();

    private List<String> myLanguages;

    @JsonGetter(Constants.LANGUAGE)
    public List<String> getLanguages() {
        return myLanguages;
    }

    /**
     * Sets the languages for this localized external resource.
     *
     * @param aLanguagesArray The languages to set
     * @return The localized external resource
     * @throws IllegalArgumentException If the language tag is invalid
     */
    @JsonSetter(Constants.LANGUAGE)
    public T setLanguages(final String... aLanguagesArray) {
        if (myLanguages == null) {
            myLanguages = new ArrayList<>();
        } else {
            myLanguages.clear();
        }
        myLanguages.addAll(Arrays.asList(aLanguagesArray).stream()
                .map(language -> {
                    try {
                        return myLocaleBuilder.setLanguageTag(language).build().toLanguageTag();
                    } catch (final IllformedLocaleException details) {
                        throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_020, language));
                    }
                }).collect(Collectors.toList()));
        return (T) this;
    }
}
