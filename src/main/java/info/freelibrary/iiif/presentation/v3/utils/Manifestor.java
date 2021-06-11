
package info.freelibrary.iiif.presentation.v3.utils;

import java.io.File;

import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;

import io.vertx.core.Promise;
import io.vertx.core.Vertx;

/**
 * The manifestor serializes and deserializes {@link Manifest}s and {@link Collection}s to and from files.
 */
public class Manifestor {

    /**
     * A Vert.x instance used by Manifestor.
     */
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
     * Deserializes a {@link Manifest} from a file.
     *
     * @param aJsonFile A JSON file representing a manifest
     * @return A Manifest object
     */
    public Manifest readManifest(final File aJsonFile) {
        return Manifest.fromJSON(myVertx.fileSystem().readFileBlocking(aJsonFile.getAbsolutePath()).toJsonObject());
    }

    /**
     * Deserializes a {@link Collection} from a file.
     *
     * @param aJsonFile A JSON file representing a collection
     * @return A Collection object
     */
    public Collection readCollection(final File aJsonFile) {
        return Collection.fromJSON(myVertx.fileSystem().readFileBlocking(aJsonFile.getAbsolutePath()).toJsonObject());
    }

    /**
     * Deserializes a {@link Manifest} from a file asynchronously.
     *
     * @param aJsonFile A JSON file representing a manifest
     * @param aPromise A Promise that will complete if the operation succeeds
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
     * Deserializes a {@link Collection} from a file asynchronously.
     *
     * @param aJsonFile A JSON file representing a collection
     * @param aPromise A Promise that will complete if the operation succeeds
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
     * Serializes a {@link Manifest} to a file.
     *
     * @param aManifest A Manifest object to serialize
     * @param aJsonFile The file to write to
     */
    public void write(final Manifest aManifest, final File aJsonFile) {
        myVertx.fileSystem().writeFileBlocking(aJsonFile.getAbsolutePath(), aManifest.toJSON().toBuffer());
    }

    /**
     * Serializes a {@link Collection} to a file.
     *
     * @param aCollection A Collection object to serialize
     * @param aJsonFile The file to write to
     */
    public void write(final Collection aCollection, final File aJsonFile) {
        myVertx.fileSystem().writeFileBlocking(aJsonFile.getAbsolutePath(), aCollection.toJSON().toBuffer());
    }

    /**
     * Serializes a {@link Manifest} to a file asynchronously.
     *
     * @param aManifest A Manifest object to serialize
     * @param aJsonFile The file to write to
     * @param aPromise A Promise that will complete if the operation succeeds
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
     * Serializes a {@link Collection} to a file asynchronously.
     *
     * @param aCollection A Collection object to serialize
     * @param aJsonFile The file to write to
     * @param aPromise A Promise that will complete if the operation succeeds
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
