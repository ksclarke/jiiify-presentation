
package info.freelibrary.iiif.presentation.utils;

import java.io.File;

import info.freelibrary.iiif.presentation.Collection;
import info.freelibrary.iiif.presentation.Manifest;

import io.vertx.core.Promise;
import io.vertx.core.Vertx;

/**
 * The manifestor reads and writes manifests and collection documents.
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
    public Manifest readManifest(final File aJsonFile) {
        return Manifest.fromJSON(myVertx.fileSystem().readFileBlocking(aJsonFile.getAbsolutePath()).toJsonObject());
    }

    /**
     * Reads a JSON collection manifest file into a Collection object.
     *
     * @param aJsonFile A JSON collection manifest file
     * @return A Collection object
     */
    public Collection readCollection(final File aJsonFile) {
        return Collection.fromJSON(myVertx.fileSystem().readFileBlocking(aJsonFile.getAbsolutePath()).toJsonObject());
    }

    /**
     * Reads a manifest JSON file using the supplied promise.
     *
     * @param aJsonFile A JSON manifest file
     * @param aPromise A Manifest object
     */
    public void readManifest(final File aJsonFile, final Promise<Manifest> aPromise) {
        myVertx.fileSystem().readFile(aJsonFile.getAbsolutePath(), read -> {
            if (read.succeeded()) {
                aPromise.complete(Manifest.fromJSON(read.result().toJsonObject()));
            } else {
                aPromise.fail(read.cause());
            }
        });
    }

    /**
     * Reads a collection manifest JSON file using the supplied promise.
     *
     * @param aJsonFile A JSON collection manifest file
     * @param aPromise A Collection object
     */
    public void readCollection(final File aJsonFile, final Promise<Collection> aPromise) {
        myVertx.fileSystem().readFile(aJsonFile.getAbsolutePath(), read -> {
            if (read.succeeded()) {
                aPromise.complete(Collection.fromJSON(read.result().toJsonObject()));
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
     * Writes a collection manifest to a JSON file.
     *
     * @param aCollection A collection to serialize
     * @param aJsonFile A JSON collection manifest file
     */
    public void write(final Collection aCollection, final File aJsonFile) {
        myVertx.fileSystem().writeFileBlocking(aJsonFile.getAbsolutePath(), aCollection.toJSON().toBuffer());
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

    /**
     * Writes a manifest to a collection manifest JSON file using the supplied promise.
     *
     * @param aCollection A collection to serialize
     * @param aJsonFile A JSON collection manifest file
     * @param aPromise A promise to use in writing
     */
    public void write(final Collection aCollection, final File aJsonFile, final Promise<Void> aPromise) {
        myVertx.fileSystem().writeFile(aJsonFile.getAbsolutePath(), aCollection.toJSON().toBuffer(), write -> {
            if (write.succeeded()) {
                aPromise.complete();
            } else {
                aPromise.fail(write.cause());
            }
        });
    }
}
