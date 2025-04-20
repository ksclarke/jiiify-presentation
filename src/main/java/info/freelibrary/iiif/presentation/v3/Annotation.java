
package info.freelibrary.iiif.presentation.v3;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import info.freelibrary.iiif.presentation.v3.annotation.Motivation;
import info.freelibrary.iiif.presentation.v3.annotation.Target;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.TimeMode;
import info.freelibrary.iiif.presentation.v3.utils.json.AnnotationDeserializer;

/**
 * An interface that defines methods related to the Web Annotation Data Model as it's used by IIIF.
 *
 * @param <A> A class that implements {@code Annotation}
 */
@JsonDeserialize(using = AnnotationDeserializer.class)
public interface Annotation<A extends Annotation<A>> {

    /**
     * Indicates whether there is a choice between annotation resources or just individual resources on an annotation.
     *
     * @return True if body contains a choice; else, false
     */
    boolean bodyHasChoice();

    /**
     * Gets the annotation body's resources.
     *
     * @return The resources associated with this annotation
     */
    List<ContentResource> getBody();

    /**
     * Gets the annotation's ID.
     *
     * @return The annotation's ID.
     */
    String getID();

    /**
     * Gets the annotation's label.
     *
     * @return The label
     */
    Optional<Label> getLabel();

    /**
     * Gets the motivation of the annotation.
     *
     * @return The motivation
     */
    Optional<Motivation> getMotivation();

    /**
     * Gets the targets of this annotation.
     *
     * @return The target
     */
    List<Target> getTargets();

    /**
     * Gets the optional time mode.
     *
     * @return The time mode
     */
    Optional<TimeMode> getTimeMode();

    /**
     * Sets the annotation body's resources.
     *
     * @param aResourceArray An array of resources
     * @return This annotation
     */
    @JsonIgnore
    A setBody(ContentResource... aResourceArray);

    /**
     * Sets the annotation body's resources.
     *
     * @param aResourceList A list of resources
     * @return This annotation
     */
    A setBody(List<ContentResource> aResourceList);

    /**
     * Sets whether the annotation body contains a choice.
     *
     * @param aChoice A boolean indicating whether the annotation's body contains a choice
     * @return This annotation
     */
    A setChoice(boolean aChoice);

    /**
     * Sets the annotation ID.
     *
     * @param aID An ID
     * @return The annotation
     */
    A setID(String aID);

    /**
     * Sets the annotation label.
     *
     * @param aLabel A label
     * @return The annotation
     */
    A setLabel(Label aLabel);

    /**
     * Sets the annotation's motivation.
     *
     * @param aMotivation A motivation
     * @return The annotation
     */
    A setMotivation(Motivation aMotivation);

    /**
     * Sets the targets of this annotation.
     *
     * @param aTargetList A list of targets
     * @return This annotation
     */
    A setTargets(List<Target> aTargetList);

    /**
     * Sets the targets of this annotation.
     *
     * @param aTargetArray An array of targets
     * @return This annotation
     */
    @JsonIgnore
    A setTargets(Target... aTargetArray);

    /**
     * Sets the time mode.
     *
     * @param aTimeMode A time mode
     * @return This annotation
     */
    A setTimeMode(TimeMode aTimeMode);
}
