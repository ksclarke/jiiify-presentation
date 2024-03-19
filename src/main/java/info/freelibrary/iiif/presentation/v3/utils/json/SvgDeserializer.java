
package info.freelibrary.iiif.presentation.v3.utils.json;

import static info.freelibrary.util.Constants.EMPTY;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * A deserializer for <code>SVGDiagram</code>s.
 */
public class SvgDeserializer extends StdDeserializer<Document> {

    /** The <code>serialVersionUID</code> for the <code>SvgDeserializer</code>. */
    private static final long serialVersionUID = -1442744976023815570L;

    /**
     * Creates a new {@code SVGDiagram} deserializer.
     */
    SvgDeserializer() {
        this(Document.class);
    }

    /**
     * Creates a new {@code SVGDiagram} deserializer.
     *
     * @param aClass A class to be deserialized
     */
    SvgDeserializer(final Class<?> aClass) {
        super(aClass);
    }

    @Override
    public Document deserialize(final JsonParser aParser, final DeserializationContext aContext)
            throws IOException, JacksonException {
        final String svg = aParser.getText();
        return svg == null ? null : Jsoup.parse(svg, EMPTY, Parser.xmlParser());
    }

}
