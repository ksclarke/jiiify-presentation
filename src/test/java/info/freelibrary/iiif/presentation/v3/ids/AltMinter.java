
package info.freelibrary.iiif.presentation.v3.ids;

import info.freelibrary.iiif.presentation.v3.CanvasResource;

/**
 * An alternate minter just used for testing the {@link MinterFactory}.
 */
public final class AltMinter implements Minter {

    private final String myManifestID;

    /**
     * Creates an alternate minter.
     *
     * @param aID A manifest ID to use with the AltMinter.
     */
    AltMinter(final String aID) {
        myManifestID = aID;
    }

    @Override
    public String getManifestID() {
        return myManifestID;
    }

    @Override
    public String getCanvasID() {
        return AltMinter.class.getSimpleName();
    }

    @Override
    public String getAnnotationID() {
        return AltMinter.class.getSimpleName();
    }

    @Override
    public <C extends CanvasResource<C>> String getAnnotationPageID(final CanvasResource<C> aCanvasResource) {
        return AltMinter.class.getSimpleName();
    }

    @Override
    public String getRangeID() {
        return AltMinter.class.getSimpleName();
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public int size() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int remaining() {
        return Integer.MAX_VALUE;
    }
}
