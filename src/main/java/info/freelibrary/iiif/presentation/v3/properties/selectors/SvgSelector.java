
package info.freelibrary.iiif.presentation.v3.properties.selectors;

import org.jsoup.nodes.Document;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.util.warnings.Eclipse;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.SvgDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.SvgSerializer;

/**
 * A selector for SVG document. For now, it just uses JSoup's {@code Document} to represent the SVG.
 */
public class SvgSelector implements Selector {

    /** The selector's SVG document. */
    private Document mySvgDocument;

    /**
     * Creates a new SvgSelector from the supplied SVG document.
     *
     * @param anSvgDocument An SVG document
     */
    public SvgSelector(final Document anSvgDocument) {
        mySvgDocument = anSvgDocument;
    }

    /**
     * Creates a new SvgSelector for Jackson's deserialization process.
     */
    @SuppressWarnings(Eclipse.UNUSED)
    private SvgSelector() {
        // This is intentionally left empty
    }

    /**
     * Sets the SVG value of the <code>SvgSelector</code>.
     *
     * @param anSvgDocument An SVG document
     * @return This selector
     */
    @JsonSetter(JsonKeys.VALUE)
    @JsonDeserialize(using = SvgDeserializer.class)
    public SvgSelector setValue(final Document anSvgDocument) {
        mySvgDocument = anSvgDocument;
        return this;
    }

    /**
     * Gets the SVG value of the <code>SvgSelector</code>.
     *
     * @return An SVG document
     */
    @JsonGetter(JsonKeys.VALUE)
    @JsonSerialize(using = SvgSerializer.class)
    public Document getValue() {
        return mySvgDocument;
    }

}
