
package info.freelibrary.iiif.presentation.v3.exts.geo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.properties.Property;

/**
 * Tests of Geo {@code Properties}.
 */
public class PropertiesTest {

    /** A property name to use in testing. */
    private static final String PROPERTY_NAME = "name";

    /** A property to use in testing. */
    private Property my1stProperty;

    /** Another property to use in testing. */
    private Property my2ndProperty;

    /** The properties being tested. */
    private Properties myProperties;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myProperties = new Properties();
        my1stProperty = new Property(PROPERTY_NAME, UUID.randomUUID().toString());
        my2ndProperty = new Property("title", "A secret history of secret histories");
    }

    /**
     * Test method for {@link Properties#add(Property)}.
     */
    @Test
    public void testAdd() {
        assertEquals(0, myProperties.size());
        myProperties.add(my1stProperty);
        assertEquals(1, myProperties.size());
    }

    /**
     * Test method for {@link Properties#addAll(Collection)}.
     */
    @Test
    public void testAddAll() {
        assertEquals(0, myProperties.size());
        myProperties.addAll(List.of(my1stProperty, my2ndProperty));
        assertEquals(2, myProperties.size());
    }

    /**
     * Test method for {@link Properties#clear()}.
     */
    @Test
    public void testClear() {
        myProperties.addAll(List.of(my1stProperty, my2ndProperty));
        assertEquals(2, myProperties.size());
        myProperties.clear();
        assertEquals(0, myProperties.size());
    }

    /**
     * Test method for {@link Properties#contains(Property)}.
     */
    @Test
    public void testContains() {
        myProperties.add(my1stProperty);
        assertTrue(myProperties.contains(my1stProperty));
    }

    /**
     * Test method for {@link Properties#containsAll(Collection)}.
     */
    @Test
    public void testContainsAll() {
        myProperties.add(my1stProperty);
        assertTrue(myProperties.containsAll(List.of(my1stProperty)));
    }

    /**
     * Test method for {@link Properties#equals(Object)}.
     */
    @Test
    public void testEqualsObject() {
        final Properties properties = new Properties();

        properties.add(my1stProperty);
        myProperties.add(my1stProperty);
        assertTrue(myProperties.equals((Object) properties));
    }

    /**
     * Test method for {@link Properties#equals(Properties)}.
     */
    @Test
    public void testEqualsProperties() {
        final Properties properties = new Properties();

        properties.add(my1stProperty);
        myProperties.add(my1stProperty);
        assertTrue(myProperties.equals(properties));
    }

    /**
     * Test method for {@link Properties#forEach(Consumer)}.
     */
    @Test
    public void testForEach() {
        final AtomicInteger counter = new AtomicInteger();

        myProperties.addAll(List.of(my1stProperty, my2ndProperty));
        myProperties.forEach(property -> counter.getAndIncrement());

        assertEquals(2, counter.get());
    }

    /**
     * Test method for {@link Properties#hashCode()}.
     */
    @Test
    public void testHashCode() {
        final Properties properties = new Properties();

        properties.add(my1stProperty);
        myProperties.add(my1stProperty);

        assertEquals(properties.hashCode(), myProperties.hashCode());
    }

    /**
     * Test method for {@link Properties#isEmpty()}.
     */
    @Test
    public void testIsEmpty() {
        assertTrue(myProperties.isEmpty());
    }

    /**
     * Test method for {@link Properties#iterator()}.
     */
    @Test
    public void testIterator() {
        final Iterator<Property> iterator;

        myProperties.addAll(List.of(my1stProperty, my2ndProperty));
        iterator = myProperties.iterator();

        int count = 0;

        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }

        assertEquals(2, count);
    }

    /**
     * Test method for {@link Properties#remove(Property)}.
     */
    @Test
    public void testRemove() {
        myProperties.add(my1stProperty);
        assertTrue(myProperties.remove(my1stProperty));
    }

    /**
     * Test method for {@link Properties#removeAll(Collection)}.
     */
    @Test
    public void testRemoveAll() {
        final List<Property> properties = List.of(my1stProperty, my2ndProperty);

        myProperties.addAll(properties);
        assertEquals(2, myProperties.size());
        myProperties.removeAll(properties);
        assertEquals(0, myProperties.size());

    }

    /**
     * Test method for {@link Properties#removeIf(Predicate)}.
     */
    @Test
    public void testRemoveIf() {
        myProperties.add(my1stProperty);
        assertEquals(1, myProperties.size());
        myProperties.removeIf(property -> PROPERTY_NAME.equals(property.getName()));
        assertEquals(0, myProperties.size());
    }

    /**
     * Test method for {@link Properties#retainAll(Collection)}.
     */
    @Test
    public void testRetainAll() {
        myProperties.add(my1stProperty);
        myProperties.add(my2ndProperty);

        assertEquals(2, myProperties.size());
        myProperties.retainAll(List.of(my1stProperty));
        assertEquals(1, myProperties.size());
    }

    /**
     * Test method for {@link Properties#size()}.
     */
    @Test
    public void testSize() {
        myProperties.add(my1stProperty);
        assertEquals(1, myProperties.size());
    }

    /**
     * Test method for {@link Properties#spliterator()}.
     */
    @Test
    public void testSpliterator() {
        final Spliterator<Property> spliterator;

        myProperties.addAll(List.of(my1stProperty, my2ndProperty));
        spliterator = myProperties.spliterator();
        assertEquals(2, spliterator.estimateSize());
    }

    /**
     * Test method for {@link Properties#stream()}.
     */
    @Test
    public void testStream() {
        myProperties.addAll(List.of(my1stProperty, my2ndProperty));
        assertEquals(2, myProperties.stream().count());
    }

}
