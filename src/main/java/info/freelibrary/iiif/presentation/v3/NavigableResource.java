
package info.freelibrary.iiif.presentation.v3;

import static info.freelibrary.util.Constants.SINGLE_INSTANCE;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.JDK;

import info.freelibrary.iiif.presentation.v3.exts.geo.NavPlace;
import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.NavDate;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A navigable resource.
 */
class NavigableResource<T extends NavigableResource<T>> extends AbstractResource<T> {

    /** The navigable resource's logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Manifest.class, MessageCodes.BUNDLE);

    /** The manifest's contexts. */
    private final List<URI> myContexts = Stream.of(PRESENTATION_CONTEXT_URI).collect(Collectors.toList());

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

    /**
     * Adds an array of new context URIs to the navigable resource.
     *
     * @param aContextArray Context URIs(s)
     * @return The navigable resource
     */
    @SuppressWarnings({ JDK.UNCHECKED })
    public T addContexts(final URI... aContextArray) {
        Objects.requireNonNull(aContextArray, MessageCodes.JPA_007);

        for (final URI uri : aContextArray) {
            Objects.requireNonNull(uri, MessageCodes.JPA_007);

            if (!PRESENTATION_CONTEXT_URI.equals(uri)) {
                myContexts.add(uri);
            }
        }

        Collections.sort(myContexts, new ContextListComparator<>());
        return (T) this;
    }

    /**
     * Clears all contexts, but the required one.
     *
     * @return The navigable resource
     */
    @SuppressWarnings({ JDK.UNCHECKED })
    public T clearContexts() {
        myContexts.clear();
        myContexts.add(PRESENTATION_CONTEXT_URI);

        return (T) this;
    }

    @Override
    public boolean equals(final Object aObject) {
        if (this == aObject) {
            return true;
        }

        if (aObject == null || getClass() != aObject.getClass()) {
            return false;
        }

        if (aObject instanceof final NavigableResource<?> other) {
            return Objects.equals(myNavDate, other.myNavDate) && Objects.equals(myNavPlace, other.myNavPlace) &&
                    super.equals(other);
        }

        return false;
    }

    /**
     * Gets the primary context.
     *
     * @return The primary context
     */
    @JsonIgnore
    public URI getContext() {
        return PRESENTATION_CONTEXT_URI;
    }

    /**
     * Gets an unmodifiable list of contexts. To remove contexts, use {@link #removeContext(URI) removeContext} or
     * {@link #clearContexts() clearContexts}.
     *
     * @return The context
     */
    @JsonIgnore
    public List<URI> getContexts() {
        if (myContexts.isEmpty()) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(myContexts);
    }

    /**
     * Gets a navigation date.
     *
     * @return The navigation date
     */
    @JsonGetter(JsonKeys.NAV_DATE)
    public NavDate getNavDate() {
        return myNavDate;
    }

    /**
     * Gets the navigation place.
     *
     * @return The navigation place
     */
    @JsonGetter(JsonKeys.NAV_PLACE)
    public NavPlace getNavPlace() {
        return myNavPlace;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myNavDate, myNavPlace);
    }

    /**
     * Remove the supplied context. This will not remove the default required context though. If that's supplied, an
     * {@link UnsupportedOperationException} will be thrown.
     *
     * @param aContextURI A context to be removed from the contexts list
     * @return True if the context was removed; else, false
     * @throws UnsupportedOperationException If the required context is supplied to be removed
     */
    public boolean removeContext(final URI aContextURI) {
        if (PRESENTATION_CONTEXT_URI.equals(aContextURI)) {
            throw new UnsupportedOperationException(LOGGER.getMessage(MessageCodes.JPA_039, PRESENTATION_CONTEXT_URI));
        }

        return myContexts.remove(aContextURI);
    }

    /**
     * Sets a navigation date.
     *
     * @param aNavDate The navigation date
     * @return The navigable resource
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
     * @return The navigable resource
     */
    @JsonSetter(JsonKeys.NAV_PLACE)
    @SuppressWarnings({ JDK.UNCHECKED })
    public T setNavPlace(final NavPlace aNavPlace) {
        myNavPlace = aNavPlace;
        return (T) this;
    }

    /**
     * Method used internally to set context from JSON.
     *
     * @param aObject A Jackson deserialization object
     */
    @JsonSetter(JsonKeys.CONTEXT)
    protected void deserializeContexts(final Object aObject) {
        if (aObject instanceof final String context) {
            deserializeContexts(List.of(context));
        } else if (aObject instanceof final List<?> genericList) {
            validateAndSetContexts(genericList);
        } else {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_113));
        }
    }

    /**
     * Gets the manifest context. The manifest can either have a single context or an array of contexts (Cf.
     * https://iiif.io/api/presentation/3.0/#46-linked-data-context-and-extensions)
     *
     * @return The manifest context
     */
    @JsonGetter(JsonKeys.CONTEXT)
    protected Object getJsonContext() {
        if (myContexts.size() == SINGLE_INSTANCE) {
            return myContexts.get(0);
        }

        if (!myContexts.isEmpty()) {
            return myContexts;
        }

        return null;
    }

    /**
     * Sets the manifest's contexts from a list that Jackson builds.
     *
     * @param aContextList A list of contexts
     */
    @JsonIgnore
    private void setContexts(final List<?> aContextList) {
        final List<Integer> indices = new ArrayList<>();
        final List<URI> contextList = new ArrayList<>();

        for (int index = 0; index < aContextList.size(); index++) {
            final URI context = URI.create((String) aContextList.get(index));

            if (PRESENTATION_CONTEXT_URI.equals(context)) {
                indices.add(index); // We may have more than one required context in supplied list

                if (indices.size() == SINGLE_INSTANCE) { // Only keep one if this is the case
                    contextList.add(context);
                }
            } else {
                contextList.add(context);
            }
        }

        // Remove required context; we'll add it back at the end
        if (!indices.isEmpty()) {
            contextList.remove((int) indices.get(0));
        }

        myContexts.clear();
        myContexts.addAll(contextList);
        myContexts.add(PRESENTATION_CONTEXT_URI); // Add required context at end
    }

    /**
     * Sets the contexts after confirming their validity.
     *
     * @param aContextList A list of contexts
     * @throws IllegalArgumentException if a supplied context isn't a string
     */
    @SuppressWarnings({ JDK.UNCHECKED })
    private void validateAndSetContexts(final List<?> aContextList) {
        if (aContextList.isEmpty() || aContextList.stream().anyMatch(item -> !(item instanceof String))) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.JPA_113));
        }

        setContexts(aContextList);
    }

    /**
     * A context list comparator that makes sure the required context is always last in the list.
     * <p>
     * Cf. https://iiif.io/api/presentation/3.0/#46-linked-data-context-and-extensions
     * </p>
     */
    static class ContextListComparator<U> implements Comparator<U> {

        @Override
        public int compare(final U aFirstURI, final U aSecondURI) {
            int result = 0;

            if (!PRESENTATION_CONTEXT_URI.equals(aFirstURI) || !PRESENTATION_CONTEXT_URI.equals(aSecondURI)) {
                if (PRESENTATION_CONTEXT_URI.equals(aFirstURI)) {
                    result = 1;
                } else if (PRESENTATION_CONTEXT_URI.equals(aSecondURI)) {
                    result = -1;
                }
            }

            return result;
        }

    }
}
