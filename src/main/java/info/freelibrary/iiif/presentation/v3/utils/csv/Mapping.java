package info.freelibrary.iiif.presentation.v3.utils.csv;

import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.IllegalArgumentI18nException;
import info.freelibrary.util.warnings.PMD;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * A mapping between CSV column names and IIIF Presentation API concepts.
 */
public class Mapping {

    /** A mapping between CSV keys and values. */
    private final Map<String, List<String>> myKeys;

    /** A mapping between CSV aliases and keys. */
    private final Map<String, String> myAliases;

    /**
     * Creates a mapping between CSV column names as defined in the supplied YAML file and IIIF Presentation API
     * concepts.
     *
     * @param aFile A file describing the mapping
     * @throws IOException If there is trouble reading from the supplied YAML file
     */
    public Mapping(final File aFile) throws IOException {
        this(Objects.requireNonNull(aFile).toPath());
    }

    /**
     * Creates a mapping between CSV column names as defined in the supplied YAML file and IIIF Presentation API
     * concepts.
     *
     * @param aPath A path to a YAML file describing the mapping
     * @throws IOException If there is trouble reading from the supplied YAML file
     * @throws IllegalArgumentI18nException If the YAML file is not valid
     */
    @SuppressWarnings({ PMD.CYCLOMATIC_COMPLEXITY })
    public Mapping(final Path aPath) throws IOException {
        final Map<String, List<String>> keys = new HashMap<>();
        final Map<String, String> aliases = new HashMap<>();
        final Yaml yaml = new Yaml(new JPv3Constructor());
        final Object yamlDoc;

        try (Reader reader = Files.newBufferedReader(aPath, StandardCharsets.UTF_8)) {
            yamlDoc = yaml.load(reader);
        }

        if (!(yamlDoc instanceof Map<?, ?> entries)) {
            throw new IllegalArgumentI18nException(MessageCodes.JPA_208);
        }

        for (final Map.Entry<?, ?> entry : entries.entrySet()) {
            final List<String> keyAliases = new ArrayList<>();

            if (!(entry.getKey() instanceof String key) || !(entry.getValue() instanceof List<?> values)) {
                throw new IllegalArgumentI18nException(MessageCodes.JPA_205);
            }

            for (final Object value : values) {
                final String previous;

                if (!(value instanceof String alias)) {
                    throw new IllegalArgumentI18nException(MessageCodes.JPA_206, key);
                }

                keyAliases.add(alias);
                previous = aliases.putIfAbsent(alias, key);

                if (previous != null && !previous.equals(key)) {
                    throw new IllegalArgumentI18nException(MessageCodes.JPA_207, alias);
                }
            }

            // Store an immutable list
            keys.put(key, List.copyOf(keyAliases));
        }

        // Store immutable maps
        myKeys = Map.copyOf(keys);
        myAliases = Map.copyOf(aliases);
    }

    /**
     * Checks whether the supplied key is contained in the mapping.
     *
     * @param aKey A key to check
     * @return True if the key is contained in the mapping; otherwise, false
     */
    public boolean containsKey(final String aKey) {
        return myKeys.containsKey(aKey);
    }

    /**
     * Finds an alias list associated with the supplied key. If the supplied key doesn't have any aliases, an empty list
     * is returned.
     *
     * @param aKey An alias that serves as the key's lookup value
     * @return A list that contains the aliases associated with the key, if any
     */
    public List<String> findByKey(final String aKey) {
        final List<String> aliases = myKeys.get(aKey);
        return aliases == null ? List.of() : aliases;
    }

    /**
     * Finds a key associated with the supplied alias.
     *
     * @param anAlias An alias that serves as the key's lookup value
     * @return An optional match that may or may not contain a key/alias pair
     */
    public Optional<String> findByAlias(final String anAlias) {
        return Optional.ofNullable(myAliases.get(anAlias));
    }

    /**
     * A YAML constructor for the JPv3 YAML schema.
     */
    private static final class JPv3Constructor extends SafeConstructor {

        /**
         * Creates a new constructor for the JPv3 YAML schema.
         */
        private JPv3Constructor() {
            super(getLoaderOptions());
        }

        /**
         * Gets the loader options for the JPv3 YAML schema.
         *
         * @return Loader options for the JPv3 YAML schema
         */
        @NotNull
        private static LoaderOptions getLoaderOptions() {
            final LoaderOptions options = new LoaderOptions();

            options.setAllowDuplicateKeys(false);
            return options;
        }
    }
}
