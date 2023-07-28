
package info.freelibrary.iiif.presentation.v3.properties;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.util.Labeled;

/**
 * A pattern for indicating the level of text granularity for an annotation.
 */
public class TextGranularity implements Labeled {

    /** The context for TextGranularity. It should be added to a document that uses the text granularity extension. */
    public static final String CONTEXT = "http://iiif.io/api/extension/text-granularity/context.json";

    /** The granularity label. */
    private final String myLabel;

    /**
     * Creates a new text granularity from the supplied level.
     *
     * @param aLevel A text granularity level
     */
    public TextGranularity(final String aLevel) {
        myLabel = Level.fromLabel(aLevel).map(Level::label).orElse(aLevel);
    }

    /**
     * Creates a new text granularity from the supplied level.
     *
     * @param aLevel A text granularity level
     */
    public TextGranularity(final Level aLevel) {
        myLabel = aLevel.label();
    }

    /**
     * Returns the label representation of the text granularity level.
     *
     * @return A label for the text granularity level
     */
    @Override
    @JsonValue
    public String label() {
        return myLabel;
    }

    @Override
    public String toString() {
        return myLabel;
    }

    /**
     * Creates a new text granularity from the supplied level.
     *
     * @param aLevel A text granularity
     * @return A text granularity
     */
    public static TextGranularity fromLabel(final Level aLevel) {
        return new TextGranularity(aLevel);
    }

    /**
     * Creates a new text granularity from the supplied label.
     *
     * @param aLabel A granularity label
     * @return A text granularity
     */
    public static TextGranularity fromLabel(final String aLabel) {
        return new TextGranularity(aLabel);
    }

    /**
     * A text granularity level.
     */
    public enum Level implements Labeled {

        /** A page in a paginated document. */
        PAGE("page"),

        /** An arbitrary region of text */
        BLOCK("block"),

        /** A paragraph. */
        PARAGRAPH("paragraph"),

        /** A topographic line */
        LINE("line"),

        /** A single word */
        WORD("word"),

        /** A single glyph or symbol. */
        GLYPH("glyph");

        /** The text granularity level's label. */
        private final String myLabel;

        /**
         * Creates a new text granularity level from the supplied label.
         *
         * @param aLabel A text granularity label.
         */
        Level(final String aLabel) {
            myLabel = aLabel;
        }

        /**
         * Returns the label representation of the text granularity level.
         *
         * @return A label for the text granularity level
         */
        @Override
        @JsonValue
        public String label() {
            return myLabel;
        }

        @Override
        public String toString() {
            return myLabel;
        }

        /**
         * Gets a text granularity level by its label.
         *
         * @param aLabel A label
         * @return An empty optional or one containing the level corresponding to the supplied label
         */
        public static Optional<Level> fromLabel(final String aLabel) {
            for (final Level level : values()) {
                if (level.myLabel.equalsIgnoreCase(aLabel)) {
                    return Optional.of(level);
                }
            }

            return Optional.empty();
        }
    }
}
