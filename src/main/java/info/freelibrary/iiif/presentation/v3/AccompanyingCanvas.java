
package info.freelibrary.iiif.presentation.v3;

import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;
import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.PMD;

import java.util.List;

/**
 * A single canvas that provides additional content that can be used while rendering the resource. Examples include: 1)
 * an image to show while a duration-only canvas is playing audio, or 2) background audio to play while a user is
 * navigating an image-only manifest.
 */
@SuppressWarnings({ PMD.TOO_MANY_METHODS })
public class AccompanyingCanvas extends AbstractCanvas<AccompanyingCanvas>
        implements CanvasResource<AccompanyingCanvas> {

    /**
     * Creates a new accompanying canvas using the supplied minter to create the canvas ID.
     *
     * @param aMinter A minter that should be used to mint an ID for the canvas
     */
    public AccompanyingCanvas(final Minter aMinter) {
        super(aMinter);
    }

    /**
     * Creates a new accompanying canvas using the supplied minter to create the canvas ID.
     *
     * @param aMinter A minter that will create the canvas ID
     * @param aLabel A accompanying canvas label
     */
    public AccompanyingCanvas(final Minter aMinter, final Label aLabel) {
        super(aMinter, aLabel);
    }

    /**
     * Creates a new accompanying canvas from the supplied ID.
     *
     * @param aID An accompanying canvas ID
     */
    public AccompanyingCanvas(final String aID) {
        super(aID);
    }

    /**
     * Creates a new accompanying canvas from the supplied ID and label.
     *
     * @param aID An accompanying canvas ID
     * @param aLabel A accompanying canvas label
     */
    public AccompanyingCanvas(final String aID, final Label aLabel) {
        super(aID, aLabel);
    }

    /**
     * Creates a new accompanying canvas. This is just used by Jackson's deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private AccompanyingCanvas() {
        super();
    }

    @Override
    public final AccompanyingCanvas paintWith(final boolean aChoice, final ContentResource<?>... aContentArray) {
        return super.paint(this, aChoice, aContentArray);
    }

    @Override
    public final AccompanyingCanvas paintWith(final boolean aChoice, final List<ContentResource<?>> aContentList) {
        return super.paint(this, aChoice, aContentList);
    }

    @Override
    public final AccompanyingCanvas paintWith(final ContentResource<?>... aContentArray) {
        return super.paint(this, false, aContentArray);
    }

    @Override
    public final AccompanyingCanvas paintWith(final List<ContentResource<?>> aContentList) {
        return super.paint(this, false, aContentList);
    }

    @Override
    public final AccompanyingCanvas paintWith(final MediaFragmentSelector aCanvasRegion, final boolean aChoice,
            final ContentResource<?>... aContentArray) {
        return super.paint(this, aCanvasRegion, aChoice, aContentArray);
    }

    @Override
    public final AccompanyingCanvas paintWith(final MediaFragmentSelector aCanvasRegion, final boolean aChoice,
            final List<ContentResource<?>> aContentList) {
        return super.paint(this, aCanvasRegion, aChoice, aContentList);
    }

    @Override
    public final AccompanyingCanvas paintWith(final MediaFragmentSelector aCanvasRegion,
            final ContentResource<?>... aContentArray) {
        return super.paint(this, aCanvasRegion, false, aContentArray);
    }

    @Override
    public final AccompanyingCanvas paintWith(final MediaFragmentSelector aCanvasRegion,
            final List<ContentResource<?>> aContentList) {
        return super.paint(this, aCanvasRegion, false, aContentList);
    }

    @Override
    public final AccompanyingCanvas paintWith(final String aCanvasRegion, final boolean aChoice,
            final ContentResource<?>... aContentArray) {
        return super.paint(this, new MediaFragmentSelector(aCanvasRegion), aChoice, aContentArray);
    }

    @Override
    public final AccompanyingCanvas paintWith(final String aCanvasRegion, final boolean aChoice,
            final List<ContentResource<?>> aContentList) {
        return super.paint(this, new MediaFragmentSelector(aCanvasRegion), aChoice, aContentList);
    }

    @Override
    public final AccompanyingCanvas paintWith(final String aCanvasRegion, final ContentResource<?>... aContentArray) {
        return super.paint(this, new MediaFragmentSelector(aCanvasRegion), false, aContentArray);
    }

    @Override
    public final AccompanyingCanvas paintWith(final String aCanvasRegion, final List<ContentResource<?>> aContentList) {
        return super.paint(this, new MediaFragmentSelector(aCanvasRegion), false, aContentList);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final boolean aChoice, final ContentResource<?>... aContentArray) {
        return super.supplement(this, aChoice, aContentArray);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final boolean aChoice, final List<ContentResource<?>> aContentList) {
        return super.supplement(this, aChoice, aContentList);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final ContentResource<?>... aContentArray) {
        return super.supplement(this, false, aContentArray);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final List<ContentResource<?>> aContentList) {
        return super.supplement(this, false, aContentList);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final MediaFragmentSelector aCanvasRegion, final boolean aChoice,
            final ContentResource<?>... aContentArray) {
        return super.supplement(this, aCanvasRegion, aChoice, aContentArray);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final MediaFragmentSelector aCanvasRegion, final boolean aChoice,
            final List<ContentResource<?>> aContentList) {
        return super.supplement(this, aCanvasRegion, aChoice, aContentList);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final MediaFragmentSelector aCanvasRegion,
            final ContentResource<?>... aContentArray) {
        return super.supplement(this, aCanvasRegion, false, aContentArray);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final MediaFragmentSelector aCanvasRegion,
            final List<ContentResource<?>> aContentList) {
        return super.supplement(this, aCanvasRegion, false, aContentList);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final String aCanvasRegion, final boolean aChoice,
            final ContentResource<?>... aContentArray) {
        return super.supplement(this, new MediaFragmentSelector(aCanvasRegion), aChoice, aContentArray);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final String aCanvasRegion, final boolean aChoice,
            final List<ContentResource<?>> aContentList) {
        return super.supplement(this, new MediaFragmentSelector(aCanvasRegion), aChoice, aContentList);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final String aCanvasRegion,
            final ContentResource<?>... aContentArray) {
        return super.supplement(this, new MediaFragmentSelector(aCanvasRegion), false, aContentArray);
    }

    @Override
    public final AccompanyingCanvas supplementWith(final String aCanvasRegion,
            final List<ContentResource<?>> aContentList) {
        return super.supplement(this, new MediaFragmentSelector(aCanvasRegion), false, aContentList);
    }
}
