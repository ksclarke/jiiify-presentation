
package info.freelibrary.iiif.presentation;

import java.net.URI;

import info.freelibrary.iiif.presentation.properties.Behavior;
import info.freelibrary.iiif.presentation.properties.TimeMode;
import info.freelibrary.iiif.presentation.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * An annotation used for associating supplementary content resources with a {@link Canvas}.
 */
public class SupplementingAnnotation extends AbstractAnnotation {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplementingAnnotation.class, Constants.BUNDLE_NAME);

    private static final String MOTIVATION = "supplementing";

    /**
     * Creates a supplementing annotation.
     *
     * @param aID An ID
     * @param aCanvas A canvas
     */
    protected SupplementingAnnotation(final URI aID, final Canvas aCanvas) {
        super(aID, aCanvas);
        myMotivation = MOTIVATION;
    }

    /**
     * Creates a supplementing annotation.
     *
     * @param aID An ID in string form
     * @param aCanvas A canvas
     */
    protected SupplementingAnnotation(final String aID, final Canvas aCanvas) {
        super(aID, aCanvas);
        myMotivation = MOTIVATION;
    }

    /**
     * Creates a supplementing annotation.
     */
    protected SupplementingAnnotation() {
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
    public SupplementingAnnotation addBody(final ContentResource... aContentArray) {
        super.addBody(aContentArray);
        return this;
    }

    @Override
    public SupplementingAnnotation clearBody() {
        super.clearBody();
        return this;
    }

    @Override
    public SupplementingAnnotation setBody(final ContentResource... aContentArray) {
        super.setBody(aContentArray);
        return this;
    }

    @Override
    public SupplementingAnnotation setTarget(final URI aURI) {
        super.setTarget(aURI);
        return this;
    }

    @Override
    public SupplementingAnnotation setTarget(final String aURI) {
        super.setTarget(aURI);
        return this;
    }

    @Override
    public SupplementingAnnotation setBehaviors(final Behavior... aBehaviorArray) {
        super.setBehaviors(aBehaviorArray);
        return this;
    }

    @Override
    public SupplementingAnnotation addBehaviors(final Behavior... aBehaviorArray) {
        super.addBehaviors(aBehaviorArray);
        return this;
    }

    @Override
    public SupplementingAnnotation setTimeMode(final TimeMode aTimeMode) {
        super.setTimeMode(aTimeMode);
        return this;
    }
}
