
package info.freelibrary.iiif.presentation.v3.annotations;

import java.util.Optional;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.SpecificResource;
import info.freelibrary.iiif.presentation.v3.ids.UriUtils;
import info.freelibrary.iiif.presentation.v3.utils.json.AnnotationTargetSerializer;

/**
 * An annotation target.
 */
@JsonSerialize(using = AnnotationTargetSerializer.class)
public class Target {

    /** The specific resource target, if applicable. */
    private SpecificResource mySpecificResource;

    /** The URI of the annotation target. */
    private String myURI;

    /**
     * Creates a new <code>WebAnnotation</code> target from the supplied specific resource.
     *
     * @param aSpecificResource A specific resource target
     */
    public Target(final SpecificResource aSpecificResource) {
        mySpecificResource = aSpecificResource;
    }

    /**
     * Creates a new <code>WebAnnotation</code> target from the supplied URI.
     *
     * @param aURI A target URI
     */
    public Target(final String aURI) {
        myURI = UriUtils.checkID(aURI, false);
    }

    /**
     * Gets the optional specific resources if there is one, else an empty optional.
     *
     * @return An optional for the target's specific resource, if present; else, an empty optional
     */
    public Optional<SpecificResource> getSpecificResource() {
        return Optional.ofNullable(mySpecificResource);
    }

    /**
     * Gets the URI for this target, regardless of its type.
     *
     * @return The target's URI
     */
    public String getURI() {
        return mySpecificResource != null
                ? StringUtils.format("{}#{}", mySpecificResource.getSource(), mySpecificResource.getSelector()) : myURI;
    }
}
