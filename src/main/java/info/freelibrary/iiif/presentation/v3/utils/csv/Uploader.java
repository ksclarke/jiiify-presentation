
package info.freelibrary.iiif.presentation.v3.utils.csv;

import info.freelibrary.iiif.presentation.v3.properties.MediaType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Test of the {@link Uploader} class.
 */
public class Uploader {

    /** The URL to which to upload the IIIF resources. */
    private final URL myHost;

    /** The username for authentication at the host. */
    private final String myUsername;

    /** The password for authentication at the host. */
    private final String myPassword;

    /**
     * Creates a new IIIF resource uploader.
     *
     * @param aHost A host to which to upload the ZIP file
     * @param aUsername A username for authentication
     * @param aPassword A password for authentication
     */
    public Uploader(final URL aHost, final String aUsername, final String aPassword) {
        myHost = aHost;
        myUsername = aUsername;
        myPassword = aPassword;
    }

    /**
     * Gets the host to which the IIIF resources are being uploaded.
     *
     * @return The host to which the IIIF resources are being uploaded
     */
    public URL getHost() {
        return myHost;
    }

    /**
     * Gets the username for authentication at the host.
     *
     * @return The username for authentication at the host
     */
    public String getUsername() {
        return myUsername;
    }

    /**
     * Gets the password for authentication at the host.
     *
     * @return The password for authentication at the host
     */
    public String getPassword() {
        return myPassword;
    }

    /**
     * Uploads the ZIP file to the host.
     *
     * @param aFilePath A path to the ZIP file to upload
     * @throws FileNotFoundException If the ZIP file doesn't exist at the supplied path
     * @throws IOException If there is trouble uploading the CSV file to the host
     * @return The response code from the upload
     */
    public int upload(final String aFilePath) throws IOException {
        final HttpURLConnection connection;
        final Path filePath = Path.of(aFilePath);

        // Pre-check that our file to upload exists
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException(aFilePath);
        }

        connection = (HttpURLConnection) myHost.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", MediaType.APPLICATION_ZIP.toString());
        connection.setFixedLengthStreamingMode(Files.size(filePath));

        try (OutputStream outStream = connection.getOutputStream();
                InputStream inStream = Files.newInputStream(filePath)) {
            final byte[] buffer = new byte[8192];

            int count;

            while ((count = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, count);
            }
        }

        return connection.getResponseCode();
    }
}
