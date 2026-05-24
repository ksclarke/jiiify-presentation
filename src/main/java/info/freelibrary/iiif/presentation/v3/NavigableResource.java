
package info.freelibrary.iiif.presentation.v3;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import info.freelibrary.iiif.presentation.v3.exts.geo.NavPlace;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.NavDate;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.BehaviorList;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.iiif.presentation.v3.utils.json.ContextFilterProvider;
import info.freelibrary.iiif.presentation.v3.utils.json.ContextListDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.ContextListSerializer;
import info.freelibrary.util.IllegalArgumentI18nException;
import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A navigable resource.
 *
 * @param <T> The type of navigable resource
 */
@JsonFilter(ContextFilterProvider.FILTER_NAME)
public class NavigableResource<T extends NavigableResource<T>> extends AbstractResource<T> {

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
        super(aType, aID, true, aBehaviorClass);
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
        super(aType, aID, true, aLabel, aBehaviorClass);
    }

    /**
     * Creates a navigable resource from another navigable resource.
     *
     * @param aNavigableResource The navigable resource to copy
     */
    @SuppressWarnings("CopyConstructorMissesField")
    protected NavigableResource(final NavigableResource<T> aNavigableResource) {
        this(getRequiredType(aNavigableResource), getBehaviorType(aNavigableResource));
        aNavigableResource.copyTo(this);
    }

    /**
     * Retrieves the supplied navigable resource's type.
     *
     * @param aNavigableResource A navigable resource from which the type is retrieved
     * @return The required type of the navigable resource
     * @throws IllegalArgumentI18nException If the type is not present in the supplied navigable resource
     */
    private static String getRequiredType(final NavigableResource<?> aNavigableResource) {
        return aNavigableResource.getType().orElseThrow(() -> new IllegalArgumentI18nException(MessageCodes.JPA_193));
    }

    /**
     * Retrieves the type of behavior associated with a navigable resource.
     *
     * @param aNavigableResource A navigable resource from which the behavior type is retrieved
     * @return The type of the behavior associated with the navigable resource
     * @throws IllegalArgumentI18nException If the behavior type is not present in the supplied navigable resource
     */
    private static Class<? extends Behavior> getBehaviorType(final NavigableResource<?> aNavigableResource) {
        if (aNavigableResource.getBehaviors() instanceof final BehaviorList behaviorList) {
            return behaviorList.getBehaviorType();
        }

        throw new IllegalArgumentI18nException(MessageCodes.BUNDLE, MessageCodes.JPA_194);
    }

    @Override
    @SuppressWarnings({ JDK.UNCHECKED })
    public T copy() {
        return (T) new NavigableResource<T>(this);
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
     * Gets a navigation date.
     *
     * @return The navigation date
     */
    @JsonGetter(JsonKeys.NAV_DATE)
    public Optional<NavDate> getNavDate() {
        return Optional.ofNullable(myNavDate);
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
     * Clears the navigation date of the resource.
     *
     * @return The current instance of the resource
     */
    @SuppressWarnings({ JDK.UNCHECKED, PMD.NULL_ASSIGNMENT })
    public T clearNavDate() {
        myNavDate = null;
        return (T) this;
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
     * Clears the navigation place of the resource.
     *
     * @return The current instance of the resource
     */
    @SuppressWarnings({ JDK.UNCHECKED, PMD.NULL_ASSIGNMENT })
    public T clearNavPlace() {
        myNavPlace = null;
        return (T) this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myNavDate, myNavPlace);
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

    @Override
    protected void copyTo(final AbstractResource<T> aResource) {
        super.copyTo(aResource);

        if (aResource instanceof final NavigableResource<T> navResource) {
            navResource.myNavDate = myNavDate;
            navResource.myNavPlace = myNavPlace;

            if (myContexts != null) {
                navResource.myContexts = myContexts.copy();
            }
        }
    }
}
