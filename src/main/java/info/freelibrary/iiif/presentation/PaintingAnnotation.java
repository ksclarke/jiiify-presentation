
package info.freelibrary.iiif.presentation;

import java.net.URI;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.TimeMode;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * An annotation used for painting content resources onto a {@link Canvas}.
 */
public class PaintingAnnotation extends AbstractAnnotation {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaintingAnnotation.class, Constants.BUNDLE_NAME);

    private static final String MOTIVATION = "painting";

    /**
     * Creates a painting annotation.
     *
     * @param aID An ID
     * @param aCanvas A canvas
     */
    protected PaintingAnnotation(final URI aID, final Canvas aCanvas) {
        super(aID, aCanvas);
        myMotivation = MOTIVATION;
    }

    /**
     * Creates a painting annotation.
     *
     * @param aID An ID in string form
     * @param aCanvas A canvas
     */
    protected PaintingAnnotation(final String aID, final Canvas aCanvas) {
        super(aID, aCanvas);
        myMotivation = MOTIVATION;
    }

    /**
     * Creates a painting annotation.
     */
    protected PaintingAnnotation() {
        super();
    }

    @Override
    void setMotivation(final String aMotivation) {
        if (!MOTIVATION.equals(aMotivation)) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_038, MOTIVATION));
        }
        myMotivation = MOTIVATION;
    }

    @Override
    public PaintingAnnotation addBody(final ContentResource... aContentArray) {
        super.addBody(aContentArray);
        return this;
    }

    @Override
    public PaintingAnnotation clearBody() {
        super.clearBody();
        return this;
    }

    @Override
    public PaintingAnnotation setBody(final ContentResource... aContentArray) {
        super.setBody(aContentArray);
        return this;
    }

    @Override
    public PaintingAnnotation setTarget(final URI aURI) {
        super.setTarget(aURI);
        return this;
    }

    @Override
    public PaintingAnnotation setTarget(final String aURI) {
        super.setTarget(aURI);
        return this;
    }

    @Override
    public PaintingAnnotation setBehaviors(final Behavior... aBehaviorArray) {
        super.setBehaviors(aBehaviorArray);
        return this;
    }

    @Override
    public PaintingAnnotation addBehaviors(final Behavior... aBehaviorArray) {
        super.addBehaviors(aBehaviorArray);
        return this;
    }

    @Override
    public PaintingAnnotation setTimeMode(final TimeMode aTimeMode) {
        super.setTimeMode(aTimeMode);
        return this;
    }
}
