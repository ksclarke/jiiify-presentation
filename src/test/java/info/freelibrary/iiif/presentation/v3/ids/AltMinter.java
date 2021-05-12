
package info.freelibrary.iiif.presentation.v3.ids;

import java.net.URI;

import info.freelibrary.iiif.presentation.v3.CanvasResource;

/**
 * An alternate minter just used for testing the {@link MinterFactory}.
 */
public final class AltMinter implements Minter {

    private final URI myManifestID;

    /**
     * Creates an alternate minter.
     *
     * @param aID A manifest ID to use with the AltMinter.
     */
    AltMinter(final URI aID) {
        myManifestID = aID;
    }

    @Override
    public URI getManifestID() {
        return myManifestID;
    }

    @Override
    public URI getCanvasID() {
        return URI.create(AltMinter.class.getSimpleName());
    }

    @Override
    public URI getAnnotationID() {
        return URI.create(AltMinter.class.getSimpleName());
    }

    @Override
    public <C extends CanvasResource<C>> URI getAnnotationPageID(final CanvasResource<C> aCanvasResource) {
        return URI.create(AltMinter.class.getSimpleName());
    }

    @Override
    public URI getRangeID() {
        return URI.create(AltMinter.class.getSimpleName());
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
