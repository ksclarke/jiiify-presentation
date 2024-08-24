
package info.freelibrary.iiif.presentation.v3.properties;

import static info.freelibrary.util.Constants.EMPTY;
import static info.freelibrary.util.Constants.SLASH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.presentation.v3.ResourceTypes;

/**
 * Tests of {@code AbstractLinkProperty}.
 */
public class AbstractLinkPropertyTest {

    /** The test ID. */
    private String myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public void setUp() {
        myID = "https://" + UUID.randomUUID().toString();
    }

    /**
     * Test method for {@link AbstractLinkProperty#AbstractLinkProperty()}.
     */
    @Test
    public final void testAbstractLinkProperty() {
        final AbstractLinkProperty<TestProperty> property = new TestProperty();

        assertNull(property.getID());
        assertNull(property.getType());
    }

    /**
     * Test method for {@link AbstractLinkProperty#AbstractLinkProperty(String)}.
     */
    @Test
    public final void testAbstractLinkPropertyString() {
        final AbstractLinkProperty<TestProperty> property = new TestProperty(ResourceTypes.TEXT);

        assertNull(property.getID());
        assertEquals(ResourceTypes.TEXT, property.getType());
    }

    /**
     * Test method for {@link AbstractLinkProperty#AbstractLinkProperty(String, String)}.
     */
    @Test
    public final void testAbstractLinkPropertyStringString() {
        final AbstractLinkProperty<TestProperty> property = new TestProperty(myID, ResourceTypes.TEXT);

        assertEquals(myID, property.getID());
        assertEquals(ResourceTypes.TEXT, property.getType());
    }

    /**
     * Test method for {@link AbstractLinkProperty#AbstractLinkProperty(String, String, Label)}.
     */
    @Test
    public final void testAbstractLinkPropertyStringStringLabel() {
        final Label label = new Label(UUID.randomUUID().toString());
        final AbstractLinkProperty<TestProperty> property = new TestProperty(myID, ResourceTypes.TEXT, label);

        assertEquals(myID, property.getID());
        assertEquals(ResourceTypes.TEXT, property.getType());
    }

    /**
     * Tests {@link AbstractLinkProperty#equals(Object)}.
     */
    @Test
    public final void testEqualsDifferentObject() {
        assertNotEquals(new TestProperty(myID, ResourceTypes.TEXT), EMPTY);
    }

    /**
     * Test method for {@link AbstractLinkProperty#equals(Object)}.
     */
    @Test
    public final void testEqualsObject() {
        final AbstractLinkProperty<TestProperty> property1 = new TestProperty(myID, ResourceTypes.TEXT);
        final AbstractLinkProperty<TestProperty> property2 = new TestProperty(myID, ResourceTypes.TEXT);
        final Label label = new Label(UUID.randomUUID().toString());
        final String[] langs = { "gr", "jp" };

        property1.setLabel(label).setLanguages(langs);
        property2.setLabel(label).setLanguages(langs);

        assertEquals(property1, Object.class.cast(property2));
    }

    /**
     * Test method for {@link AbstractLinkProperty#getFormat()}.
     */
    @Test
    public final void testGetSetFormat() {
        final AbstractLinkProperty<TestProperty> property = new TestProperty(myID, ResourceTypes.TEXT);

        property.setFormat(MediaType.APPLICATION_ATOM_PLUS_XML);
        assertTrue(property.getFormat().isPresent());
        assertEquals(MediaType.APPLICATION_ATOM_PLUS_XML, property.getFormat().get());
        assertEquals(myID, property.getID());
    }

    /**
     * Test method for {@link AbstractLinkProperty#getID()}.
     */
    @Test
    public final void testGetSetID() {
        assertEquals(myID, new TestProperty(myID, ResourceTypes.TEXT).getID());
        assertEquals(myID, new TestProperty().setID(myID).getID());
    }

    /**
     * Test method for {@link AbstractLinkProperty#getLanguages()}.
     */
    @Test
    public final void testGetSetLanguages() {
        final AbstractLinkProperty<TestProperty> property = new TestProperty(myID, ResourceTypes.TEXT);
        final String[] langs = { "en", "fr" };

        property.setLanguages(langs);
        assertEquals(List.of(langs), property.getLanguages());
    }

    /**
     * Test method for {@link AbstractLinkProperty#getProfile()}.
     */
    @Test
    public final void testGetSetProfile() {
        final AbstractLinkProperty<TestProperty> property = new TestProperty(myID, ResourceTypes.TEXT);

        assertTrue(property.getProfile().isEmpty());
        property.setProfile(myID);
        assertTrue(property.getProfile().isPresent());
        assertEquals(myID, property.getProfile().get());
    }

    /**
     * Test method for {@link AbstractLinkProperty#getType()}.
     */
    @Test
    public final void testGetSetType() {
        final AbstractLinkProperty<TestProperty> property = new TestProperty(myID, ResourceTypes.TEXT);

        assertEquals(ResourceTypes.TEXT, property.getType());
        property.setType(ResourceTypes.SOUND);
        assertEquals(ResourceTypes.SOUND, property.getType());
    }

    /**
     * Test method for {@link AbstractLinkProperty#hashCode()}.
     */
    @Test
    public void testHashCode() {
        final AbstractLinkProperty<TestProperty> property1 = new TestProperty(myID, ResourceTypes.TEXT);
        final AbstractLinkProperty<TestProperty> property2 = new TestProperty(myID, ResourceTypes.TEXT);
        final Label label = new Label(UUID.randomUUID().toString());

        property1.setLabel(label);
        property2.setLabel(label);

        assertEquals(property1.hashCode(), property2.hashCode());
    }

    /**
     * Tests {@link AbstractLinkProperty#equals(Object)}.
     */
    @Test
    public final void testPropertiesEqualsHashCodeNot() {
        final AbstractLinkProperty<TestProperty> property1 = new TestProperty(myID + SLASH, ResourceTypes.TEXT);
        final AbstractLinkProperty<TestProperty> property2 = new TestProperty(myID, ResourceTypes.TEXT);

        assertNotEquals(property1.hashCode(), property2.hashCode());
    }

    /**
     * Tests {@link AbstractLinkProperty#equals(Object)}.
     */
    @Test
    public final void testPropertiesEqualsNull() {
        assertNotEquals(new TestProperty(myID), null);
    }

    /**
     * Tests {@link AbstractLinkProperty#equals(Object)}.
     */
    @Test
    public final void testPropertiesEqualsSame() {
        final AbstractLinkProperty<TestProperty> property1 = new TestProperty(myID, ResourceTypes.TEXT);
        final AbstractLinkProperty<TestProperty> property2 = new TestProperty(myID, ResourceTypes.TEXT);

        assertEquals(property1, property2);
    }

    /**
     * Tests {@link AbstractLinkProperty#equals(Object)}.
     */
    @Test
    public final void testPropertiesEqualsSameNot() {
        final AbstractLinkProperty<TestProperty> property1 = new TestProperty(myID + SLASH, ResourceTypes.TEXT);
        final AbstractLinkProperty<TestProperty> property2 = new TestProperty(myID, ResourceTypes.TEXT);

        assertNotEquals(property1, property2);
    }

    /**
     * Tests {@link AbstractLinkProperty#equals(Object)}.
     */
    @Test
    public final void testPropertiesEqualsSameObject() {
        final AbstractLinkProperty<TestProperty> property = new TestProperty(myID, ResourceTypes.TEXT);
        assertEquals(property, property);
    }

    /**
     * Tests {@link AbstractLinkProperty#equals(Object)}.
     */
    @Test
    @SuppressWarnings("unlikely-arg-type")
    public final void testPropertiesEqualsString() {
        final AbstractLinkProperty<TestProperty> property = new TestProperty(myID, ResourceTypes.TEXT);
        assertNotEquals(property, EMPTY);
    }

    /**
     * A private test class that extends AbstractLinkProperty.
     */
    private static final class TestProperty extends AbstractLinkProperty<TestProperty> {

        /**
         * Creates a new testing instance.
         */
        private TestProperty() {
            super();
        }

        /**
         * Creates a new testing instance from the supplied type.
         *
         * @param aType A property type
         */
        private TestProperty(final String aType) {
            super(aType);
        }

        /**
         * Creates a new testing instance from the supplied type.
         *
         * @param aID A property ID
         * @param aType A property type
         */
        private TestProperty(final String aID, final String aType) {
            super(aID, aType);
        }

        /**
         * Creates a new testing instance from the supplied type.
         *
         * @param aID A property ID
         * @param aType A property type
         * @param aLabel A property label
         */
        private TestProperty(final String aID, final String aType, final Label aLabel) {
            super(aID, aType, aLabel);
        }
    }
}
