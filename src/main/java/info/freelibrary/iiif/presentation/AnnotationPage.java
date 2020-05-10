
package info.freelibrary.iiif.presentation;

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
 * An ordered list of annotations, typically associated with a single canvas.
 */
public class AnnotationPage extends Resource<AnnotationPage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationPage.class, Constants.BUNDLE_NAME);

    private static final int REQ_ARG_COUNT = 2;

    private List<ImageContent> myImageContent;

    /**
     * Creates an annotation page resource.
     *
     * @param aID An annotation page ID
     */
    public AnnotationPage(final String aID) {
        super(ResourceTypes.ANNOTATION_PAGE, aID, REQ_ARG_COUNT);
    }

    /**
     * Creates an annotation list resource.
     *
     * @param aID An annotation list ID
     */
    public AnnotationPage(final URI aID) {
        super(ResourceTypes.ANNOTATION_PAGE, aID, REQ_ARG_COUNT);
    }

    /**
     * Allows Jackson to deserialize JSON.
     */
    private AnnotationPage() {
        super(ResourceTypes.ANNOTATION_PAGE);
    }

    /**
     * Sets the canvas' image content.
     *
     * @param aContentArray A canvas image content
     * @return The canvas
     */
    @JsonGetter(Constants.ITEMS)
    public AnnotationPage setImageContent(final ImageContent... aContentArray) {
        if (myImageContent != null) {
            myImageContent.clear();
        }

        return addImageContent(aContentArray);
    }

    /**
     * Adds image content to the canvas.
     *
     * @param aContentArray Image content to be added to the canvas
     * @return The canvas
     */
    public AnnotationPage addImageContent(final ImageContent... aContentArray) {
        if (!Collections.addAll(getImageContent(), aContentArray)) {
            final String message = LOGGER.getMessage(MessageCodes.JPA_050, StringUtils.toString(aContentArray, ' '));
            throw new UnsupportedOperationException(message);
        }

        return this;
    }

    /**
     * Gets the canvas' image content.
     *
     * @return The canvas' image content
     */
    @JsonGetter(Constants.ITEMS)
    public List<ImageContent> getImageContent() {
        if (myImageContent == null) {
            myImageContent = new ArrayList<>();
        }

        return myImageContent;
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
