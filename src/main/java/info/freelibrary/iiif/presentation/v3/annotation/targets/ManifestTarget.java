
package info.freelibrary.iiif.presentation.v3.annotation.targets;

import java.util.List;
import java.util.Optional;

import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.id.UriUtils;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;

/**
 * A manifest target of an annotation.
 */
public non-sealed class ManifestTarget extends Target {

    /**
     * Creates a new manifest target of an annotation.
     *
     * @param aManifest A manifest to target
     */
    public ManifestTarget(final Manifest aManifest) {
        super(aManifest.getID(), true);
    }

    /**
     * Creates a new manifest target of an annotation.
     *
     * @param aManifest A manifest to target
     * @param aPartOf A part of a manifest
     */
    public ManifestTarget(final Manifest aManifest, final PartOf aPartOf) {
        super(aManifest.getID(), true, aPartOf);
    }

    /**
     * Creates a new manifest target of an annotation.
     *
     * @param aID An ID of a manifest
     */
    public ManifestTarget(final String aID) {
        super(UriUtils.checkID(aID, true));
    }

    /**
     * Creates a new manifest target of an annotation.
     *
     * @param aID A ID of a manifest
     * @param aPartOf A part of a manifest
     */
    public ManifestTarget(final String aID, final PartOf aPartOf) {
        super(aID, true, aPartOf);
    }

    @Override
    public List<PartOf> getPartOfs() {
        return super.getPartOfs();
    }

    @Override
    public Optional<String> getType() {
        return Optional.of(ResourceTypes.MANIFEST);
    }

    @Override
    public ManifestTarget setID(final String aID) {
        return (ManifestTarget) super.setID(aID);
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
    protected ManifestTarget setType(final String aType) {
        if (!ResourceTypes.MANIFEST.equals(aType)) {
            throw new IllegalArgumentException();
        }

        return this;
    }
}
