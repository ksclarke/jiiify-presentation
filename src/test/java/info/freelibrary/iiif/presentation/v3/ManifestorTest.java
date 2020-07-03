
package info.freelibrary.iiif.presentation.v3;

import static org.junit.Assert.*;

import java.io.File;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import info.freelibrary.iiif.presentation.v3.utils.Manifestor;
import info.freelibrary.iiif.presentation.v3.utils.TestUtils;

import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

/**
 * Tests of the Manifestor.
 */
@RunWith(VertxUnitRunner.class)
public class ManifestorTest {

    private static final File MANIFEST = new File(TestUtils.TEST_DIR, "z1960050.json");

    private static final File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    private static final String JSON_EXT = ".json";

    private Vertx myVertx;

    /**
     * Sets up the testing environment.
     *
     * @param aContext The testing context
     */
    @Before
    public void setUp(final TestContext aContext) {
        myVertx = Vertx.vertx();
    }

    /**
     * Tests reading a file using a supplied Vert.x instance.
     *
     * @param aContext The testing context
     */
    @Test
    public final void testReadFileNoVertx(final TestContext aContext) {
        testManifest(new Manifestor().readManifest(MANIFEST), aContext);
    }

    /**
     * Tests reading a file using a supplied Vert.x instance.
     *
     * @param aContext The testing context
     */
    @Test
    public final void testReadFile(final TestContext aContext) {
        testManifest(new Manifestor(myVertx).readManifest(MANIFEST), aContext);
    }

    /**
     * Tests reading a file.
     *
     * @param aContext The testing context
     */
    @Test
    public final void testReadFileHandlerOfPromiseOfManifestNoVertx(final TestContext aContext) {
        final Promise<Manifest> promise = Promise.<Manifest>promise();

        promise.future().setHandler(handler -> {
            if (handler.succeeded()) {
                testManifest(handler.result(), aContext);
            } else {
                fail(handler.cause().getMessage());
            }
        });

        new Manifestor().readManifest(MANIFEST, promise);
    }

    /**
     * Tests reading a file using a supplied Vert.x instance.
     *
     * @param aContext The testing context
     */
    @Test
    public final void testReadFileHandlerOfPromiseOfManifest(final TestContext aContext) {
        final Promise<Manifest> promise = Promise.<Manifest>promise();

        promise.future().setHandler(handler -> {
            if (handler.succeeded()) {
                testManifest(handler.result(), aContext);
            } else {
                fail(handler.cause().getMessage());
            }
        });

        new Manifestor(myVertx).readManifest(MANIFEST, promise);
    }

    /**
     * Tests writing a supplied manifest to a file synchronously.
     *
     * @param aContext A testing context
     */
    @Test
    public final void testWriteFileNoVertx(final TestContext aContext) {
        final JsonObject json = new JsonObject(myVertx.fileSystem().readFileBlocking(MANIFEST.getAbsolutePath()));
        final File tmpJsonFile = new File(TMP_DIR, UUID.randomUUID().toString() + JSON_EXT);

        new Manifestor().write(Manifest.fromJSON(json), tmpJsonFile);

        assertEquals(json, new JsonObject(myVertx.fileSystem().readFileBlocking(tmpJsonFile.getAbsolutePath())));
    }

    /**
     * Tests writing a supplied manifest to a file synchronously.
     *
     * @param aContext A testing context
     */
    @Test
    public final void testWriteFile(final TestContext aContext) {
        final JsonObject json = new JsonObject(myVertx.fileSystem().readFileBlocking(MANIFEST.getAbsolutePath()));
        final File tmpJsonFile = new File(TMP_DIR, UUID.randomUUID().toString() + JSON_EXT);

        new Manifestor(myVertx).write(Manifest.fromJSON(json), tmpJsonFile);

        assertEquals(json, new JsonObject(myVertx.fileSystem().readFileBlocking(tmpJsonFile.getAbsolutePath())));
    }

    /**
     * Tests writing a supplied manifest to a file.
     *
     * @param aContext A testing context
     */
    @Test
    public final void testWriteFileWithPromiseNoVertx(final TestContext aContext) {
        final JsonObject json = new JsonObject(myVertx.fileSystem().readFileBlocking(MANIFEST.getAbsolutePath()));
        final File tmpJsonFile = new File(TMP_DIR, UUID.randomUUID().toString() + JSON_EXT);
        final Promise<Void> promise = Promise.<Void>promise();

        promise.future().setHandler(handler -> {
            if (handler.failed()) {
                fail(handler.cause().getMessage());
            } else {
                final Buffer buffer = myVertx.fileSystem().readFileBlocking(tmpJsonFile.getAbsolutePath());

                assertEquals(json, new JsonObject(buffer));
            }
        });

        new Manifestor().write(Manifest.fromJSON(json), tmpJsonFile, promise);
    }

    /**
     * Tests writing a supplied manifest to a file.
     *
     * @param aContext A testing context
     */
    @Test
    public final void testWriteFileWithPromise(final TestContext aContext) {
        final JsonObject json = new JsonObject(myVertx.fileSystem().readFileBlocking(MANIFEST.getAbsolutePath()));
        final File tmpJsonFile = new File(TMP_DIR, UUID.randomUUID().toString() + JSON_EXT);
        final Promise<Void> promise = Promise.<Void>promise();

        promise.future().setHandler(handler -> {
            if (handler.failed()) {
                fail(handler.cause().getMessage());
            } else {
                final Buffer buffer = myVertx.fileSystem().readFileBlocking(tmpJsonFile.getAbsolutePath());

                assertEquals(json, new JsonObject(buffer));
            }
        });

        new Manifestor(myVertx).write(Manifest.fromJSON(json), tmpJsonFile, promise);
    }

    /**
     * Tests the manifest's contents.
     *
     * @param aManifest A manifest to test
     * @param aContext A testing context
     */
    private void testManifest(final Manifest aManifest, final TestContext aContext) {
        final JsonObject expected = new JsonObject(myVertx.fileSystem().readFileBlocking(MANIFEST.getAbsolutePath()));
        aContext.assertEquals(expected, aManifest.toJSON());
    }
}
