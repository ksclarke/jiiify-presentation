
package info.freelibrary.iiif.presentation.v3;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.util.warnings.JDK;

import info.freelibrary.iiif.presentation.v3.exts.geo.NavPlace;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.NavDate;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.json.ContextListDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.ContextListSerializer;

/**
 * A navigable resource.
 */
class NavigableResource<T extends NavigableResource<T>> extends AbstractResource<T> {

    /** The resource's contexts. */
    @JsonProperty(JsonKeys.CONTEXT)
    @JsonSerialize(using = ContextListSerializer.class)
    @JsonDeserialize(using = ContextListDeserializer.class)
    private ContextList myContexts;

    /** The date of the navigable resource. */
    private NavDate myNavDate;

    /** The place of a navigable resource. */
    private NavPlace myNavPlace;

    /**
     * Creates a navigable resource.
     *
     * @param aType A resource type
     * @param aBehaviorClass A behavior class for this resource
     */
    protected NavigableResource(final String aType, final Class<? extends Behavior> aBehaviorClass) {
        super(aType, aBehaviorClass);
    }

    /**
     * Creates a navigable resource.
     *
     * @param aType A resource type
     * @param aID An ID
     * @param aBehaviorClass A behavior class for this resource
     */
    protected NavigableResource(final String aType, final String aID, final Class<? extends Behavior> aBehaviorClass) {
        super(aType, aID, aBehaviorClass);
    }

    /**
     * Creates a navigable resource.
     *
     * @param aType A resource type
     * @param aID An ID
     * @param aLabel A descriptive label
     * @param aBehaviorClass A behavior class for this resource
     */
    protected NavigableResource(final String aType, final String aID, final Label aLabel,
            final Class<? extends Behavior> aBehaviorClass) {
        super(aType, aID, aLabel, aBehaviorClass);
    }

    @Override
    @SuppressWarnings(JDK.UNCHECKED)
    public boolean equals(final Object aObject) {
        final NavigableResource<T> other;

        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        other = (NavigableResource<T>) aObject;

        return Objects.equals(myNavDate, other.myNavDate) && Objects.equals(myNavPlace, other.myNavPlace) &&
                super.equals(other);
    }

    /**
     * Gets the resource's contexts.
     *
     * @return The contexts
     */
    @JsonIgnore
    public List<URI> getContexts() {
        return getContextList();
    }

    /**
     * Gets a navigation date.
     *
     * @return The navigation date
     */
    @JsonGetter(JsonKeys.NAV_DATE)
    public Optional<NavDate> getNavDate() {
        return Optional.ofNullable(myNavDate);
    }

    /**
     * Gets the navigation place.
     *
     * @return The navigation place
     */
    @JsonGetter(JsonKeys.NAV_PLACE)
    public Optional<NavPlace> getNavPlace() {
        return Optional.ofNullable(myNavPlace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myNavDate, myNavPlace);
    }

    /**
     * Sets the manifest's contexts from a list that Jackson builds.
     *
     * @param aContextList A list of contexts
     * @return This resource
     */
    @JsonIgnore
    @SuppressWarnings({ JDK.UNCHECKED })
    public T setContexts(final List<URI> aContextList) {
        if (aContextList instanceof final ContextList contextList) {
            myContexts = contextList;
        } else {
            myContexts = new ContextList(aContextList);
        }

        return (T) this;
    }

    /**
     * Sets a navigation date.
     *
     * @param aNavDate The navigation date
     * @return This resource
     */
    @JsonSetter(JsonKeys.NAV_DATE)
    @SuppressWarnings({ JDK.UNCHECKED })
    public T setNavDate(final NavDate aNavDate) {
        myNavDate = aNavDate;
        return (T) this;
    }

    /**
     * Sets the navigation place.
     *
     * @param aNavPlace The navigation place
     * @return This resource
     */
    @JsonSetter(JsonKeys.NAV_PLACE)
    @SuppressWarnings({ JDK.UNCHECKED })
    public T setNavPlace(final NavPlace aNavPlace) {
        myNavPlace = aNavPlace;
        return (T) this;
    }

    /**
     * Gets the resource's contexts.
     *
     * @return The contexts
     */
    @JsonIgnore
    protected final List<URI> getContextList() {
        if (myContexts == null) {
            myContexts = new ContextList();
        }

        return myContexts;
    }
}
