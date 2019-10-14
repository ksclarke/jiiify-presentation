
package info.freelibrary.iiif.presentation;

import java.io.File;

import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * The manifestor reads and writes manifests.
 */
public class Manifestor {

    private final Vertx myVertx;

    /**
     * Creates a new manifestor.
     */
    public Manifestor() {
        myVertx = Vertx.vertx();
    }

    /**
     * Creates a new manifestor using the supplied Vert.x context.
     *
     * @param aVertx A Vert.x instance.
     */
    public Manifestor(final Vertx aVertx) {
        myVertx = aVertx;
    }

    /**
     * Reads a JSON manifest file into a Manifest object.
     *
     * @param aJsonFile A JSON manifest file
     * @return A Manifest object
     */
    public Manifest read(final File aJsonFile) {
        return Manifest.fromJSON(new JsonObject(myVertx.fileSystem().readFileBlocking(aJsonFile.getAbsolutePath())));
    }

    /**
     * Reads a JSON file using the supplied promise.
     *
     * @param aJsonFile A JSON manifest file
     * @param aPromise A Manifest object
     */
    public void read(final File aJsonFile, final Promise<Manifest> aPromise) {
        myVertx.fileSystem().readFile(aJsonFile.getAbsolutePath(), read -> {
            if (read.succeeded()) {
                aPromise.complete(Manifest.fromJSON(new JsonObject(read.result())));
            } else {
                aPromise.fail(read.cause());
            }
        });
    }

    /**
     * Writes a manifest to a JSON file.
     *
     * @param aManifest A manifest to serialize
     * @param aJsonFile A JSON manifest file
     */
    public void write(final Manifest aManifest, final File aJsonFile) {
        myVertx.fileSystem().writeFileBlocking(aJsonFile.getAbsolutePath(), aManifest.toJSON().toBuffer());
    }

    /**
     * Writes a manifest to a JSON file using the supplied promise.
     *
     * @param aManifest A manifest to serialize
     * @param aJsonFile A JSON manifest file
     * @param aPromise A promise to use in writing
     */
    public void write(final Manifest aManifest, final File aJsonFile, final Promise<Void> aPromise) {
        myVertx.fileSystem().writeFile(aJsonFile.getAbsolutePath(), aManifest.toJSON().toBuffer(), write -> {
            if (write.succeeded()) {
                aPromise.complete();
            } else {
                aPromise.fail(write.cause());
            }
        });
    }
}
