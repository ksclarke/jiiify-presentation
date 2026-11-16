package info.freelibrary.iiif.presentation.v3.utils.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * Tests the {@code Mapping} class.
 */
public class MappingTest {

    /** The CSV mapping being tested. */
    private Mapping myMapping;

    /**
     * Sets up the testing environment.
     *
     * @throws Exception If there is trouble setting up the testing environment
     */
    @Before
    public void setUp() throws Exception {
        myMapping = new Mapping(Path.of("src/test/resources/yaml/mapping.yaml"));
    }

    /**
     * Tests finding a mapping by key.
     */
    @Test
    public void findByKey() {
        final List<String> aliases = myMapping.findByKey(Keys.PARENT_ID);

        assertNotNull(aliases);
        assertEquals(2, aliases.size());
    }

    /**
     * Tests finding a mapping by key when the supplied key doesn't exist.
     */
    @Test
    public void findByKeyUnknown() {
        final List<String> aliases = myMapping.findByKey(UUID.randomUUID().toString());

        // We should get an empty list
        assertNotNull(aliases);
        assertEquals(0, aliases.size());
    }

    /**
     * Tests finding a mapping by alias.
     */
    @Test
    public void findByAlias() {
        myMapping.findByAlias("Parent ID").ifPresentOrElse(key -> assertEquals(Keys.PARENT_ID, key), Assert::fail);
        myMapping.findByAlias("Parent ARK").ifPresentOrElse(key -> assertEquals(Keys.PARENT_ID, key), Assert::fail);
    }

    /**
     * Tests finding a mapping by an alias that doesn't exist.
     */
    @Test
    public void findByAliasUnknown() {
        myMapping.findByAlias(UUID.randomUUID().toString()).ifPresent(Assert::fail);
    }

    /**
     * Tests checking the presence of a key.
     */
    @Test
    public void checkKeyPresence() {
        assertTrue(myMapping.containsKey(Keys.TITLE));
        assertFalse(myMapping.containsKey(UUID.randomUUID().toString()));
    }
}
