
package info.freelibrary.iiif.webrepl;

import static info.freelibrary.util.Constants.INADDR_ANY;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import info.freelibrary.util.StringUtils;
import info.freelibrary.util.ThrowingConsumer;

/**
 * A test of the server's endpoints.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServerIT {

    /** The location of the test server. */
    private static final String BASE_URL =
            StringUtils.format("http://{}:{}/", INADDR_ANY, System.getProperty("server.port", "8888"));

    /** The test code snippet. */
    private static final String BODY_CODE = """
        var manifest = new Manifest("https://iiif.io/api/cookbook/recipe/0001-mvm-image/manifest",
          new Label("en", "Single Image Example"));
        var canvas = new Canvas(MinterFactory.getMinter(manifest)).setWidthHeight(1200, 1800);
        var imageContent =
          new ImageContent("https://iiif.io/api/presentation/2.1/example/fixtures/resources/page1-full.png");

        canvas.paintWith(imageContent.setWidthHeight(1200, 1800));
        manifest.setCanvases(canvas);

        System.out.println(manifest);
        """;

    /** An HTTP response body publisher. */
    private static final BodyHandler<String> BODY_HANDLER = HttpResponse.BodyHandlers.ofString();

    /** An HTTP request body publisher. */
    private static final BodyPublisher BODY_PUBLISHER =
            HttpRequest.BodyPublishers.ofString("code=" + URLEncoder.encode(BODY_CODE, UTF_8));

    /** An HTTP client to use to test the server's endpoint. */
    private HttpClient myHttpClient;

    /**
     * Set up the testing environment.
     */
    @BeforeAll
    final void setupTestEnv() {
        myHttpClient = HttpClient.newHttpClient();
    }

    /**
     * Test sending an unsupported DELETE to the endpoint.
     */
    @Test
    final void testDeleteReq() {
        final HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(BASE_URL)).build();
        final CompletableFuture<HttpResponse<String>> future = myHttpClient.sendAsync(request, BODY_HANDLER);

        future.thenAccept(response -> {
            Assertions.assertEquals(Status.METHOD_NOT_ALLOWED.getCode(), response.statusCode());
            Assertions.assertEquals(Status.METHOD_NOT_ALLOWED.getMessage(), response.body());
        });

        // Block until the response arrives
        future.join();
    }

    /**
     * Test sending an invalid POST to the endpoint.
     */
    @Test
    final void testInvalidPOST() {
        final HttpRequest request = HttpRequest.newBuilder().POST(BODY_PUBLISHER).uri(URI.create(BASE_URL)).build();
        final CompletableFuture<HttpResponse<String>> future = myHttpClient.sendAsync(request, BODY_HANDLER);

        future.thenAccept(response -> {
            Assertions.assertEquals(Status.NOT_FOUND.getCode(), response.statusCode());
            Assertions.assertEquals(Status.NOT_FOUND.getMessage(), response.body());
        });

        // Block until the response arrives
        future.join();
    }

    /**
     * Test sending a valid POST to the endpoint.
     */
    @Test
    final void testValidPOST() {
        final Builder request = HttpRequest.newBuilder().POST(BODY_PUBLISHER).uri(URI.create(BASE_URL + "/submit"));
        final CompletableFuture<HttpResponse<String>> future = myHttpClient.sendAsync(request.build(), BODY_HANDLER);

        future.thenAccept((ThrowingConsumer<HttpResponse<String>>) response -> {
            assertEquals(Files.readString(Path.of("src/test/resources/manifest.json"), UTF_8).trim(), response.body());
        });

        // Block until the response arrives
        future.join();
    }
}
