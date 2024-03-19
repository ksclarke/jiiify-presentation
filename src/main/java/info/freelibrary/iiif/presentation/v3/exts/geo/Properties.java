
package info.freelibrary.iiif.presentation.v3.exts.geo;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import info.freelibrary.iiif.presentation.v3.properties.Property;
import info.freelibrary.iiif.presentation.v3.utils.json.PropertiesDeserializer;
import info.freelibrary.iiif.presentation.v3.utils.json.PropertiesSerializer;

/**
 * A wrapper for I18n properties.
 */
@JsonSerialize(using = PropertiesSerializer.class)
@JsonDeserialize(using = PropertiesDeserializer.class)
public class Properties implements Iterable<Property> {

    /** The internal representation of the Properties object. */
    private final Set<Property> myProperties;

    /**
     * Creates a new Properties instance.
     */
    public Properties() {
        myProperties = new LinkedHashSet<>();
    }

    /**
     * Adds a property to the properties.
     *
     * @param aProperty A new property to add
     * @return True if the property was successfully added; else, false
     */
    public boolean add(final Property aProperty) {
        return myProperties.add(aProperty);
    }

    /**
     * Adds a collection of properties.
     *
     * @param aCollection The collection of properties to add
     * @return True if the properties were successfully added; else, false
     */
    public boolean addAll(final Collection<Property> aCollection) {
        return myProperties.addAll(aCollection);
    }

    /**
     * Tests whether the supplied property is contained with the properties.
     *
     * @param aProperty The property to test
     * @return True if the property was found; else, false
     */
    public boolean contains(final Property aProperty) {
        return myProperties.contains(aProperty);
    }

    /**
     * Tests whether a supplied collection of properties are contained within the existing <code>Properties</code>.
     *
     * @param aCollection A collection of properties to test
     * @return True if the supplied properties were all found; else, false
     */
    public boolean containsAll(final Collection<Property> aCollection) {
        return myProperties.containsAll(aCollection);
    }

    @Override
    public void forEach(final Consumer<? super Property> aAction) {
        myProperties.forEach(aAction);
    }

    @Override
    public Spliterator<Property> spliterator() {
        return myProperties.spliterator();
    }

    @Override
    public Iterator<Property> iterator() {
        return myProperties.iterator();
    }

    /**
     * Tests whether there are any properties in the <code>Properties</code> instance.
     *
     * @return True if there are no properties found; else, false
     */
    public boolean isEmpty() {
        return myProperties.isEmpty();
    }

    /**
     * Removes the supplied property from the properties.
     *
     * @param aProperty A property to remove
     * @return True if the property was successfully removed; else, false
     */
    public boolean remove(final Property aProperty) {
        return myProperties.remove(aProperty);
    }

    /**
     * Removes the properties in the supplied collection from the properties.
     *
     * @param aCollection A collection of properties to remove
     * @return True if the properties were successfully removed; else, false
     */
    public boolean removeAll(final Collection<Property> aCollection) {
        return myProperties.removeAll(aCollection);
    }

    /**
     * Removes all properties except those found in the supplied collection.
     *
     * @param aCollection A collection of properties to retain
     * @return True if the properties were successfully retained; else, false
     */
    public boolean retainAll(final Collection<Property> aCollection) {
        return myProperties.retainAll(aCollection);
    }

    /**
     * The number of properties.
     *
     * @return The number of properties
     */
    public int size() {
        return myProperties.size();
    }

    /**
     * Tests whether the supplied <code>Properties</code> are equal to these <code>Properties</code>.
     *
     * @param aProperties The properties to test
     * @return True if the properties are equal to these; else, false
     */
    @SuppressWarnings("PMD.SuspiciousEqualsMethodName")
    public boolean equals(final Properties aProperties) {
        return myProperties.equals(aProperties.myProperties);
    }

    @Override
    public boolean equals(final Object aObject) {
        return aObject instanceof Properties && myProperties.equals(aObject);
    }

    @Override
    public int hashCode() {
        return myProperties.hashCode();
    }

    /**
     * Removes all the properties.
     *
     * @return This <code>Properties</code>
     */
    public Properties clear() {
        myProperties.clear();
        return this;
    }

    /**
     * Streams the properties found in this instance.
     *
     * @return A stream of properties
     */
    public Stream<Property> stream() {
        return myProperties.stream();
    }

    /**
     * Remove properties if they match the supplied predicate filter.
     *
     * @param aFilter The predicate filter
     * @return True if the property was successfully removed; else, false
     */
    public boolean removeIf(final Predicate<? super Property> aFilter) {
        return myProperties.removeIf(aFilter);
    }
}
