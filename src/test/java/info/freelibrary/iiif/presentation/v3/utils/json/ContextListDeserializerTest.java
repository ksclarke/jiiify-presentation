package info.freelibrary.iiif.presentation.v3.utils.json;

import static info.freelibrary.iiif.presentation.v3.ContextList.PRESENTATION_CONTEXT_URI;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import info.freelibrary.iiif.presentation.v3.ContextList;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.util.StringUtils;
import org.junit.Test;

import java.net.URI;
import java.util.List;

/**
 * Tests of {@link ContextListDeserializer}.
 */
public class ContextListDeserializerTest {

    /** A mapper for context list test data. */
    private final ObjectMapper myMapper = new ObjectMapper();

    /**
     * Tests deserializing the required context from a single string.
     *
     * @throws Exception If there is trouble reading the test data
     */
    @Test
    public void testDeserializeDefaultContext() throws Exception {
        final ContextListHolder holder = myMapper.readValue(
          StringUtils.format("{\"{}\":\"{}\"}", JsonKeys.CONTEXT, PRESENTATION_CONTEXT_URI), ContextListHolder.class);

        assertEquals(List.of(PRESENTATION_CONTEXT_URI), holder.myContexts);
    }

    /**
     * Tests deserializing extension contexts before the required context and serializing them again.
     *
     * @throws Exception If there is trouble reading or writing the test data
     */
    @Test
    public void testDeserializeAndSerializeContextOrder() throws Exception {
        final URI firstExtension = URI.create("https://example.org/context/first");
        final URI secondExtension = URI.create("https://example.org/context/second");
        final String json = StringUtils.format("{\"{}\":[\"{}\",\"{}\",\"{}\",\"{}\"]}", JsonKeys.CONTEXT,
          firstExtension, PRESENTATION_CONTEXT_URI, secondExtension, PRESENTATION_CONTEXT_URI);
        final ContextListHolder holder = myMapper.readValue(json, ContextListHolder.class);

        assertEquals(List.of(firstExtension, secondExtension, PRESENTATION_CONTEXT_URI), holder.myContexts);
        assertEquals(
          StringUtils.format("[\"{}\",\"{}\",\"{}\"]", firstExtension, secondExtension, PRESENTATION_CONTEXT_URI),
          myMapper.readTree(myMapper.writeValueAsString(holder)).get(JsonKeys.CONTEXT).toString());
    }

    /** A holder that exercises the same Jackson mapping used by navigable resources. */
    public static class ContextListHolder {

        /** The test contexts. */
        @JsonProperty(JsonKeys.CONTEXT)
        @JsonSerialize(using = ContextListSerializer.class)
        @JsonDeserialize(using = ContextListDeserializer.class)
        public ContextList myContexts;

        /** Creates a context list holder. */
        public ContextListHolder() {
            // This intentionally empty
        }
    }
}
