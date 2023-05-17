
package info.freelibrary.iiif.presentation.v3.annotations;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.presentation.v3.utils.Labeled;

/**
 * A relationship that serves as an annotation's motivation or the purpose of an annotation's body.
 */
public enum Purpose implements Labeled {

    /** Used when one intends to assess a target resource in some way, rather than simply make a comment about it. */
    ASSESSING("assessing"),

    /** Used when one intends to create a bookmark to a target or part thereof. */
    BOOKMARKING("bookmarking"),

    /** Used when one intends to classify a target as something. */
    CLASSIFYING("classifying"),

    /** Used when one intends to comment about a target. */
    COMMENTING("commenting"),

    /** Used when one intends to describe a target, as opposed to, for example, a comment about it. */
    DESCRIBING("describing"),

    /** Used when one intends to request a change or edit to a target resource. */
    EDITING("editing"),

    /** Used when one intends to highlight a target resource or segment of it. */
    HIGHLIGHTING("highlighting"),

    /** Used when the user intends to link to a resource related to a target. */
    IDENTIFYING("identifying"),

    /** Used when the user intends to assign some value or quality to a target. */
    LINKING("linking"),

    /** Used when the user intends to assign some value or quality to a target. */
    MODERATING("moderating"),

    /** Used when the user intends to ask a question about a target. */
    QUESTIONING("questioning"),

    /** Used when the user intends to reply to a previous statement, either an Annotation or another resource. */
    REPLYING("replying"),

    /** Used when the user intends to associate a tag with a target. */
    TAGGING("tagging"),

    /** Used when the user intends to associate a painted resource with a target canvas. */
    PAINTING("painting"),

    /** Used when the user intends to associate a supplementing resource with the target canvas. */
    SUPPLEMENTING("supplementing");

    /** The string form of the enumeration. */
    private final String myLabel;

    /**
     * Creates a new purpose enumeration.
     *
     * @param aLabel A relationship value
     */
    Purpose(final String aLabel) {
        myLabel = aLabel;
    }

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
     * Gets a purpose by its label.
     *
     * @param aLabel A label
     * @return An empty optional or one containing the purpose corresponding to the supplied label
     */
    public static Optional<Purpose> fromLabel(final String aLabel) {
        for (final Purpose purpose : values()) {
            if (purpose.myLabel.equalsIgnoreCase(aLabel)) {
                return Optional.of(purpose);
            }
        }

        return Optional.empty();
    }
}
