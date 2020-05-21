
package info.freelibrary.iiif.presentation;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.behaviors.ResourceBehavior;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

/**
 * A page of annotations.
 */
public class AnnotationPage extends Resource<AnnotationPage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationPage.class, Constants.BUNDLE_NAME);

    private List<ContentAnnotation> myAnnotations;

    /**
     * Creates a new annotation page.
     *
     * @param aID An annotation page ID in string form
     */
    public AnnotationPage(final String aID) {
        super(ResourceTypes.ANNOTATION_PAGE, aID);
    }

    /**
     * Creates a new annotation page.
     *
     * @param aID An annotation page ID
     */
    public AnnotationPage(final URI aID) {
        super(ResourceTypes.ANNOTATION_PAGE, aID);
    }

    /**
     * Allows Jackson to deserialize JSON.
     */
    private AnnotationPage() {
        super(ResourceTypes.ANNOTATION_PAGE);
    }

    /**
     * Sets the annotation page's contents.
     *
     * @param aContentArray A content array
     * @return The annotation page
     */
    @JsonGetter(Constants.ITEMS)
    public AnnotationPage setAnnotations(final ContentAnnotation... aContentArray) {
        if (myAnnotations != null) {
            myAnnotations.clear();
        }

        return addAnnotations(aContentArray);
    }

    /**
     * Adds content to the annotation page.
     *
     * @param aContentArray Content to be added to the annotation page
     * @return The annotation page
     */
    public AnnotationPage addAnnotations(final ContentAnnotation... aContentArray) {
        if (!Collections.addAll(getAnnotations(), checkNotNull(aContentArray))) {
            final String contents = StringUtils.toString(aContentArray, '|');
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_050, contents));
        }

        return this;
    }

    /**
     * Gets the annotation page's content.
     *
     * @return The annotation page's content
     */
    @JsonGetter(Constants.ITEMS)
    public List<ContentAnnotation> getAnnotations() {
        if (myAnnotations == null) {
            myAnnotations = new ArrayList<>();
        }

        return myAnnotations;
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public AnnotationPage setBehaviors(final Behavior... aBehaviorArray) {
        return super.setBehaviors(checkBehaviors(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    public AnnotationPage addBehaviors(final Behavior... aBehaviorArray) {
        return super.addBehaviors(checkBehaviors(ResourceBehavior.class, aBehaviorArray));
    }

    @Override
    public String toString() {
        return String.join(":", getClass().getSimpleName(), getID().toString());
    }
}
