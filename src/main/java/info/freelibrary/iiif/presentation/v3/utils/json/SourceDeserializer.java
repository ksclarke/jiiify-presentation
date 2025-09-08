
package info.freelibrary.iiif.presentation.v3.utils.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import info.freelibrary.util.warnings.PMD;
import info.freelibrary.util.warnings.Sonar;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.annotation.SpecificResource.Source;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A deserializer for for {@code SpecificResource.Source}.
 */
public class SourceDeserializer extends StdDeserializer<Source> {

    /** The <code>serialVersionUID</code> for a <code>SourceDeserializer</code>. */
    private static final long serialVersionUID = 2493966189471740163L;

    /**
     * Creates a new {@code Source} deserializer.
     */
    SourceDeserializer() {
        this(Source.class);
    }

    /**
     * Creates a new {@code Source} deserializer.
     *
     * @param aClass A class to be deserialized
     */
    SourceDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    @SuppressWarnings({ PMD.CYCLOMATIC_COMPLEXITY, PMD.COGNITIVE_COMPLEXITY, Sonar.COGNITIVE_COMPLEXITY })
    public Source deserialize(final JsonParser aParser, final DeserializationContext aContext) throws IOException {
        final JsonNode currentNode = JSON.getReader().readTree(aParser);

        if (!currentNode.isTextual()) {
            final JsonNode idNode = currentNode.get(JsonKeys.ID);
            final JsonNode typeNode = currentNode.get(JsonKeys.TYPE);
            final JsonNode partOfNode = currentNode.get(JsonKeys.PART_OF);
            final JsonNode width = currentNode.get(JsonKeys.WIDTH);
            final JsonNode height = currentNode.get(JsonKeys.HEIGHT);
            final JsonNode mediaType = currentNode.get(JsonKeys.FORMAT);
            final JsonNode service = currentNode.get(JsonKeys.SERVICE);
            final List<PartOf> partOfs = new ArrayList<>(2);
            final List<Service> services = new ArrayList<>(2);
            final String id = idNode.textValue();
            final Source source = new Source(id);

            if (typeNode != null) {
                source.setType(typeNode.textValue());
            }

            if (partOfNode != null) {
                if (partOfNode.isArray()) {
                    partOfs.addAll(JSON.getReader(new TypeReference<List<PartOf>>() {}).readValue(partOfNode));
                } else {
                    partOfs.add(JSON.getReader(PartOf.class).readValue(partOfNode));
                }

                source.setPartOfs(partOfs);
            }

            if (width != null && height != null) {
                source.setWidthHeight(width.asInt(), height.asInt());
            }

            if (mediaType != null) {
                source.setFormat(JSON.readValue(mediaType.toPrettyString(), MediaType.class));
            }

            if (service != null && service.isArray()) {
                services.addAll(JSON.getReader(new TypeReference<List<Service>>() {}).readValue(service));
                source.setServices(services);
            }

            return source;
        }

        return new Source(currentNode.textValue());
    }

}
