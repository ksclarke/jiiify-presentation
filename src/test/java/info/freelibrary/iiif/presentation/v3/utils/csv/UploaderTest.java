
package info.freelibrary.iiif.presentation.v3.utils.csv;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.util.UUID;

/** Tests of the {@link Uploader} class. */
public class UploaderTest {

    /** The host to test. */
    private static final String HOST = "http://localhost/unzip";

    /** The username to test. */
    private static final String USERNAME = "username";

    /** The password to test. */
    private static final String PASSWORD = UUID.randomUUID().toString();

    /** Tests the {@link Uploader#Uploader(URL, String, String)} constructor. */
    @Test
    public void getHost() throws MalformedURLException {
        final Uploader uploader = new Uploader(URI.create(HOST).toURL(), USERNAME, PASSWORD);
        assertEquals(HOST, uploader.getHost().toString());
    }

    /** Tests the {@link Uploader#Uploader(URL, String, String)} constructor. */
    @Test
    public void getUsername() throws MalformedURLException {
        final Uploader uploader = new Uploader(URI.create(HOST).toURL(), USERNAME, PASSWORD);
        assertEquals(USERNAME, uploader.getUsername());
    }

    /** Tests the {@link Uploader#Uploader(URL, String, String)} constructor. */
    @Test
    public void getPassword() throws MalformedURLException {
        final Uploader uploader = new Uploader(URI.create(HOST).toURL(), USERNAME, PASSWORD);
        assertEquals(PASSWORD, uploader.getPassword());
    }

    /** Tests the {@link Uploader#upload(String)} method. */
    @Test
    public void upload() throws IOException {
        final File file = new File("src/test/resources/zip/jbu-collection.zip");
        final URL mockURL = mock(URL.class);
        final HttpURLConnection mockConn = mock(HttpURLConnection.class);
        final OutputStream mockOut = mock(OutputStream.class);
        final byte[] testData = new byte[(int) Files.size(file.toPath())];

        try (FileInputStream mockIn = mock(FileInputStream.class)) {
            final Uploader uploader = new Uploader(mockURL, USERNAME, PASSWORD);

            when(mockURL.toString()).thenReturn(HOST);
            when(mockURL.openConnection()).thenReturn(mockConn);
            when(mockConn.getOutputStream()).thenReturn(mockOut);
            when(mockConn.getResponseCode()).thenReturn(201);
            when(mockIn.read(any(byte[].class))).thenReturn(testData.length, -1);

            assertEquals(201, uploader.upload(file.getAbsolutePath()));

            verify(mockConn).setRequestMethod("POST");
            verify(mockConn).setRequestProperty("Content-Type", MediaType.APPLICATION_ZIP.toString());
            verify(mockConn).setDoOutput(true);
            verify(mockOut, atLeastOnce()).write(any(), anyInt(), anyInt());
        }
    }

    /** Tests the {@link Uploader#upload(String)} method. */
    @Test
    public void testUploadConnectionFailure() throws Exception {
        final URL mockUrl = mock(URL.class);
        final Uploader uploader = new Uploader(mockUrl, USERNAME, PASSWORD);

        when(mockUrl.openConnection()).thenThrow(new IOException("Mock connection error"));
        assertThrows(IOException.class, () -> uploader.upload("path/to/file.csv"));
    }

    /** Tests the {@link Uploader#upload(String)} method. */
    @Test
    public void testUploadFileNotFound() throws IOException {
        final URL mockURL = mock(URL.class);
        final Uploader uploader = new Uploader(mockURL, USERNAME, PASSWORD);

        assertThrows(FileNotFoundException.class, () -> uploader.upload("nonexistent.csv"));
    }
}
