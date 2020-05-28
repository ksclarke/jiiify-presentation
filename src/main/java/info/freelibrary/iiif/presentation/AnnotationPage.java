
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
public class AnnotationPage<T> extends Resource<AnnotationPage<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationPage.class, Constants.BUNDLE_NAME);

    private List<T> myAnnotations;

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
     * Sets the annotation page's annotations.
     *
     * @param aAnnotationArray An annotation array
     * @return The annotation page
     */
    @JsonGetter(Constants.ITEMS)
    @SafeVarargs
    public final AnnotationPage<T> setAnnotations(final T... aAnnotationArray) {
        if (myAnnotations != null) {
            myAnnotations.clear();
        }

        return addAnnotations(aAnnotationArray);
    }

    /**
     * Adds annotations to the annotation page.
     *
     * @param aAnnotationArray Annotations to be added to the annotation page
     * @return The annotation page
     */
    @SafeVarargs
    public final AnnotationPage<T> addAnnotations(final T... aAnnotationArray) {
        if (!Collections.addAll(getAnnotations(), checkNotNull(aAnnotationArray))) {
            final String contents = StringUtils.toString(aAnnotationArray, '|');
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_050, contents));
        }

        return this;
    }

    /**
     * Gets the annotation page's annotations.
     *
     * @return The annotation page's annotations
     */
    @JsonGetter(Constants.ITEMS)
    public List<T> getAnnotations() {
        if (myAnnotations == null) {
            myAnnotations = new ArrayList<>();
        }

        return myAnnotations;
    }

    @Override
    @JsonSetter(Constants.BEHAVIOR)
    public AnnotationPage<T> setBehaviors(final Behavior... aBehaviorArray) {
        return super.setBehaviors(checkBehaviors(ResourceBehavior.class, true, aBehaviorArray));
    }

    @Override
    public AnnotationPage<T> addBehaviors(final Behavior... aBehaviorArray) {
        return super.addBehaviors(checkBehaviors(ResourceBehavior.class, false, aBehaviorArray));
    }

    @Override
    public String toString() {
        return String.join(":", getClass().getSimpleName(), getID().toString());
    }
}
