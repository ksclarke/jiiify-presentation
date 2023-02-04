
package info.freelibrary.iiif.presentation.v3.ids;

import info.freelibrary.iiif.presentation.v3.CanvasResource;

/**
 * An alternate minter just used for testing the {@link MinterFactory}.
 */
public final class EnvAltMinter implements Minter {

    private final String myManifestID;

    /**
     * Creates an alternate minter.
     *
     * @param aManifestID A manifest ID to use with the EnvAltMinter
     */
    EnvAltMinter(final String aManifestID) {
        myManifestID = aManifestID;
    }

    @Override
    public String getManifestID() {
        return myManifestID;
    }

    @Override
    public String getCanvasID() {
        return EnvAltMinter.class.getSimpleName();
    }

    @Override
    public String getAnnotationID() {
        return EnvAltMinter.class.getSimpleName();
    }

    @Override
    public <C extends CanvasResource<C>> String getAnnotationPageID(final CanvasResource<C> aCanvasResource) {
        return EnvAltMinter.class.getSimpleName();
    }

    @Override
    public String getRangeID() {
        return EnvAltMinter.class.getSimpleName();
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
