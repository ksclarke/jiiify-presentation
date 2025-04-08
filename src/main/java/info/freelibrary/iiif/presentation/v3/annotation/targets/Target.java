
package info.freelibrary.iiif.presentation.v3.annotation.targets;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.iiif.presentation.v3.properties.PartOf;
import info.freelibrary.iiif.presentation.v3.utils.json.AnnotationTargetDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.AnnotationTargetSerializer;

/**
 * An annotation target. There are several types of extensions of this sealed class: {@code SpecificResource},
 * {@code ManifestTarget}, and {@code CanvasTarget}.
 */
@JsonSerialize(using = AnnotationTargetSerializer.class)
@JsonDeserialize(using = AnnotationTargetDeserializer.class)
public sealed class Target extends AbstractTarget permits SpecificResource, ManifestTarget, CanvasTarget, RangeTarget {

    /**
     * Creates a new target from the supplied ID, which must be a valid URI. A generic target is used when the target
     * doesn't need to be serialized with a type property.
     *
     * @param aID An annotation target ID
     */
    public Target(final String aID) {
        super(aID, false);
    }

    /**
     * Creates a new target for the JSON deserializer.
     */
    protected Target() {
        super();
    }

    /**
     * Creates a new annotation target from the supplied ID.
     *
     * @param aID An ID for the annotation target
     * @param aHttpsReq Whether the ID must use HTTPS
     */
    protected Target(final String aID, final boolean aHttpsReq) {
        super(aID, aHttpsReq);
    }

    /**
     * Creates a new target of an annotation from the supplied ID and list of partOf(s).
     *
     * @param aID A ID of a canvas to target
     * @param aHttpsReq Whether the ID must use HTTPS
     * @param aPartOfList A list of partOf(s)
     */
    protected Target(final String aID, final boolean aHttpsReq, final List<PartOf> aPartOfList) {
        super(aID, aHttpsReq, aPartOfList);
    }

    /**
     * Creates a new annotation target from the supplied ID and array of partOf(s).
     *
     * @param aID An ID for the annotation target
     * @param aHttpsReq Whether the ID must use HTTPS
     * @param aPartOfArray An array of partOf(s).
     */
    protected Target(final String aID, final boolean aHttpsReq, final PartOf... aPartOfArray) {
        super(aID, aHttpsReq, aPartOfArray);
    }

    @Override
    protected Target setID(final String aID) {
        return (Target) super.setID(aID);
    }

    @Override
    protected Target setType(final String aType) {
        throw new IllegalArgumentException();
    }
}
