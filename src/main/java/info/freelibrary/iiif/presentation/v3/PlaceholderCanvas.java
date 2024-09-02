
package info.freelibrary.iiif.presentation.v3;

import java.util.List;

import info.freelibrary.util.warnings.Eclipse;
import info.freelibrary.util.warnings.PMD;

import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.selectors.MediaFragmentSelector;

/**
 * A single canvas that provides additional content for use before the main content of the resource is rendered. It may
 * also be used as an advertisement or stand-in for that content. Examples include images, text and sound standing in
 * for video content before the user initiates playback; or a film poster to attract user attention. The content
 * provided by placeholderCanvas differs from a thumbnail: a client might use thumbnail to summarize and navigate
 * multiple resources, then show content from placeholderCanvas as part of the initial presentation of a single
 * resource. A placeholder canvas is likely to have different dimensions to those of the canvas(es) of the resource that
 * has the placeholderCanvas property.
 */
@SuppressWarnings({ PMD.TOO_MANY_METHODS, PMD.EXCESSIVE_PUBLIC_COUNT, PMD.COUPLING_BETWEEN_OBJECTS, PMD.GOD_CLASS })
public class PlaceholderCanvas extends AbstractCanvas<PlaceholderCanvas> implements CanvasResource<PlaceholderCanvas> {

    /**
     * Creates a new placeholder canvas, using the supplied minter to create the canvas ID.
     *
     * @param aMinter A minter that should be used to get an ID for the canvas
     */
    public PlaceholderCanvas(final Minter aMinter) {
        super(aMinter);
    }

    /**
     * Creates a new placeholder canvas from the supplied label, using the supplied minter to create the canvas' ID.
     *
     * @param aMinter A minter that will create the canvas ID
     * @param aLabel A placeholder canvas label
     */
    public PlaceholderCanvas(final Minter aMinter, final Label aLabel) {
        super(aMinter, aLabel);
    }

    /**
     * Creates a new placeholder canvas from the supplied ID.
     *
     * @param aID A placeholder canvas ID
     */
    public PlaceholderCanvas(final String aID) {
        super(aID);
    }

    /**
     * Creates a new placeholder canvas from the supplied ID and label.
     *
     * @param aID A placeholder canvas ID
     * @param aLabel A placeholder canvas label
     */
    public PlaceholderCanvas(final String aID, final Label aLabel) {
        super(aID, aLabel);
    }

    /**
     * Creates a new placeholder canvas. This is used by Jackson for its deserialization processes.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private PlaceholderCanvas() {
        super();
    }

    @Override
    public final PlaceholderCanvas paintWith(final boolean aChoice, final ContentResource... aContentArray) {
        return super.paint(this, aChoice, aContentArray);
    }

    @Override
    public final PlaceholderCanvas paintWith(final boolean aChoice, final List<ContentResource> aContentList) {
        return super.paint(this, aChoice, aContentList);
    }

    @Override
    public final PlaceholderCanvas paintWith(final ContentResource... aContentArray) {
        return super.paint(this, false, aContentArray);
    }

    @Override
    public final PlaceholderCanvas paintWith(final List<ContentResource> aContentList) {
        return super.paint(this, false, aContentList);
    }

    @Override
    public final PlaceholderCanvas paintWith(final MediaFragmentSelector aCanvasRegion, final boolean aChoice,
            final ContentResource... aContentArray) {
        return super.paint(this, aCanvasRegion, aChoice, aContentArray);
    }

    @Override
    public final PlaceholderCanvas paintWith(final MediaFragmentSelector aCanvasRegion, final boolean aChoice,
            final List<ContentResource> aContentList) {
        return super.paint(this, aCanvasRegion, aChoice, aContentList);
    }

    @Override
    public final PlaceholderCanvas paintWith(final MediaFragmentSelector aCanvasRegion,
            final ContentResource... aContentArray) {
        return super.paint(this, aCanvasRegion, false, aContentArray);
    }

    @Override
    public final PlaceholderCanvas paintWith(final MediaFragmentSelector aCanvasRegion,
            final List<ContentResource> aContentList) {
        return super.paint(this, aCanvasRegion, false, aContentList);
    }

    @Override
    public final PlaceholderCanvas paintWith(final String aCanvasRegion, final boolean aChoice,
            final ContentResource... aContentArray) {
        return super.paint(this, new MediaFragmentSelector(aCanvasRegion), aChoice, aContentArray);
    }

    @Override
    public final PlaceholderCanvas paintWith(final String aCanvasRegion, final boolean aChoice,
            final List<ContentResource> aContentList) {
        return super.paint(this, new MediaFragmentSelector(aCanvasRegion), aChoice, aContentList);
    }

    @Override
    public final PlaceholderCanvas paintWith(final String aCanvasRegion, final ContentResource... aContentArray) {
        return super.paint(this, new MediaFragmentSelector(aCanvasRegion), false, aContentArray);
    }

    @Override
    public final PlaceholderCanvas paintWith(final String aCanvasRegion, final List<ContentResource> aContentList) {
        return super.paint(this, new MediaFragmentSelector(aCanvasRegion), false, aContentList);
    }

    @Override
    public final PlaceholderCanvas supplementWith(final boolean aChoice, final ContentResource... aContentArray) {
        return super.supplement(this, aChoice, aContentArray);
    }

    @Override
    public final PlaceholderCanvas supplementWith(final boolean aChoice, final List<ContentResource> aContentList) {
        return super.supplement(this, aChoice, aContentList);
    }

    @Override
    public final PlaceholderCanvas supplementWith(final ContentResource... aContentArray) {
        return super.supplement(this, false, aContentArray);
    }

    @Override
    public final PlaceholderCanvas supplementWith(final List<ContentResource> aContentList) {
        return super.supplement(this, false, aContentList);

    }

    @Override
    public final PlaceholderCanvas supplementWith(final MediaFragmentSelector aCanvasRegion, final boolean aChoice,
            final ContentResource... aContentArray) {
        return super.supplement(this, aCanvasRegion, aChoice, aContentArray);
    }

    @Override
    public final PlaceholderCanvas supplementWith(final MediaFragmentSelector aCanvasRegion, final boolean aChoice,
            final List<ContentResource> aContentList) {
        return super.supplement(this, aCanvasRegion, aChoice, aContentList);
    }

    @Override
    public final PlaceholderCanvas supplementWith(final MediaFragmentSelector aCanvasRegion,
            final ContentResource... aContentArray) {
        return super.supplement(this, aCanvasRegion, false, aContentArray);
    }

    @Override
    public final PlaceholderCanvas supplementWith(final MediaFragmentSelector aCanvasRegion,
            final List<ContentResource> aContentList) {
        return super.supplement(this, aCanvasRegion, false, aContentList);
    }

    @Override
    public final PlaceholderCanvas supplementWith(final String aCanvasRegion, final boolean aChoice,
            final ContentResource... aContentArray) {
        return super.supplement(this, new MediaFragmentSelector(aCanvasRegion), aChoice, aContentArray);
    }

    @Override
    public final PlaceholderCanvas supplementWith(final String aCanvasRegion, final boolean aChoice,
            final List<ContentResource> aContentList) {
        return super.supplement(this, new MediaFragmentSelector(aCanvasRegion), aChoice, aContentList);
    }

    @Override
    public final PlaceholderCanvas supplementWith(final String aCanvasRegion, final ContentResource... aContentArray) {
        return super.supplement(this, new MediaFragmentSelector(aCanvasRegion), false, aContentArray);
    }

    @Override
    public final PlaceholderCanvas supplementWith(final String aCanvasRegion,
            final List<ContentResource> aContentList) {
        return super.supplement(this, new MediaFragmentSelector(aCanvasRegion), false, aContentList);
    }
}
