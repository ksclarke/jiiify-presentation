
package info.freelibrary.iiif.presentation.v3.annotation.targets;

import java.util.List;
import java.util.Optional;

import info.freelibrary.iiif.presentation.v3.CanvasResource;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.id.UriUtils;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;

/**
 * A canvas that's used as the target of an annotation.
 */
public non-sealed class CanvasTarget extends Target {

    /**
     * Creates a new canvas target of an annotation.
     *
     * @param <T> A type of Canvas
     * @param aCanvas A canvas to target
     */
    public <T extends CanvasResource<T>> CanvasTarget(final CanvasResource<T> aCanvas) {
        super(aCanvas.getID(), true);
    }

    /**
     * Creates a new canvas target of an annotation.
     *
     * @param <T> A type of Canvas
     * @param aCanvas A canvas to target
     * @param aPartOf A part of a manifest
     */
    public <T extends CanvasResource<T>> CanvasTarget(final CanvasResource<T> aCanvas, final PartOf aPartOf) {
        super(aCanvas.getID(), true, aPartOf);
    }

    /**
     * Creates a new canvas target of an annotation.
     *
     * @param aID An ID of a canvas
     */
    public CanvasTarget(final String aID) {
        super(UriUtils.checkID(aID, true));
    }

    /**
     * Creates a new canvas target of an annotation from the supplied ID and list of partOf(s).
     *
     * @param aID A ID of a canvas to target
     * @param aPartOfList A list of partOf(s)
     */
    public CanvasTarget(final String aID, final List<PartOf> aPartOfList) {
        super(aID, true, aPartOfList);
    }

    /**
     * Creates a new canvas target of an annotation from the supplied ID and array of partOf(s).
     *
     * @param aID A ID of a canvas to target
     * @param aPartOfArray An array of partOf(s)
     */
    public CanvasTarget(final String aID, final PartOf... aPartOfArray) {
        super(aID, true, aPartOfArray);
    }

    @Override
    public List<PartOf> getPartOfs() {
        return super.getPartOfs();
    }

    @Override
    public Optional<String> getType() {
        return Optional.of(ResourceTypes.CANVAS);
    }

    @Override
    public CanvasTarget setID(final String aID) {
        return (CanvasTarget) super.setID(aID);
    }

    @Override
    public CanvasTarget setPartOfs(final List<PartOf> aPartOfList) {
        return (CanvasTarget) super.setPartOfs(aPartOfList);
    }

    @Override
    public CanvasTarget setPartOfs(final PartOf... aPartOfArray) {
        return (CanvasTarget) super.setPartOfs(aPartOfArray);
    }

    @Override
    protected CanvasTarget setType(final String aType) {
        if (!ResourceTypes.CANVAS.equals(aType)) {
            throw new IllegalArgumentException();
        }

        return this;
    }
}
