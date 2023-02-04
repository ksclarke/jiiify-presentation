
package info.freelibrary.iiif.presentation.v3.annotations;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A W3C Web Annotation relationship which can be used as an annotation's motivation or the purpose of an annotation's
 * textual body.
 */
public enum Relationship {

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
     * Creates a new <code>Relationship</code> enumeration.
     *
     * @param aLabel A relationship value
     */
    Relationship(final String aLabel) {
        myLabel = aLabel;
    }

    @Override
    @JsonValue
    public String toString() {
        return myLabel;
    }

    /**
     * Gets a relationship by its label.
     *
     * @param aLabel A label
     * @return The relationship for the supplied label
     * @throws IllegalArgumentException If the supplied label isn't a supported relationship
     */
    public static Relationship forLabel(final String aLabel) {
        for (final Relationship relationship : values()) {
            if (relationship.myLabel.equalsIgnoreCase(aLabel)) {
                return relationship;
            }
        }

        throw new IllegalArgumentException(aLabel);
    }
}
