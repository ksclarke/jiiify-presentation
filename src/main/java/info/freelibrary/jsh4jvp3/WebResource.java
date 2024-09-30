
package info.freelibrary.jsh4jvp3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A {@code WebResource} represents a resource to return through HTTP request.
 */
public class WebResource {

    /** The relative location of the webapp directory. */
    private static final String WEBAPP_DIR = "src/main/resources";

    /** The requested Web resource as a byte array. */
    private final byte[] myResource;

    /**
     * Creates a new {@code WebResource}.
     *
     * @param aWebResource The name of our desired Web resource
     * @throws IOException If there is trouble reading our resource
     */
    public WebResource(final String aWebResource) throws IOException {
        final Path path = Path.of(WEBAPP_DIR, aWebResource);

        if (Files.exists(path)) {
            myResource = Files.readAllBytes(path);
        } else {
            final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            final byte[] byteBuffer = new byte[1024];

            try (InputStream inputStream = classLoader.getResourceAsStream("webroot/" + aWebResource);
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream()) {
                int bytesRead;

                while ((bytesRead = inputStream.read(byteBuffer)) != -1) {
                    byteStream.write(byteBuffer, 0, bytesRead);
                }

                myResource = byteStream.toByteArray();
            }
        }
    }

    /**
     * Gets the bytes of this {@code WebResource}.
     *
     * @return The bytes of this {@code WebResource}
     */
    public byte[] getBytes() {
        return myResource.clone();
    }
}
