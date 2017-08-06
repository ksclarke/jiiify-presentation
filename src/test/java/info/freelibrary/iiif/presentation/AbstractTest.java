
package info.freelibrary.iiif.presentation;

import org.junit.BeforeClass;

import io.vertx.core.json.Json;

public class AbstractTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
        // We need to register jackson-databind-jdk8 module, among possible others
        Json.mapper.findAndRegisterModules();
    }

}
