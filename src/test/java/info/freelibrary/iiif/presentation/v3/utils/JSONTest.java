
package info.freelibrary.iiif.presentation.v3.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;

import info.freelibrary.util.StringUtils;

import info.freelibrary.iiif.presentation.v3.AnnotationPage;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.annotation.BookmarkingAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.Purpose;
import info.freelibrary.iiif.presentation.v3.annotation.WebAnnotation;
import info.freelibrary.iiif.presentation.v3.annotation.targets.Target;
import info.freelibrary.iiif.presentation.v3.content.DatasetContent;

/**
 * Tests of the {@link JSON} utility class.
 */
public class JSONTest {

    /** A test JSON object. */
    private static final String TEST_JSON = """
        {
          "id" : "{}",
          "type" : "Dataset"
        }
        """;

    /** An ID used in testing. */
    private String myID;

    /**
     * Sets up the testing environment.
     */
    @Before
    public final void setUp() {
        myID = "https://" + UUID.randomUUID().toString();
    }

    /**
     * Test method for {@link JSON#convertValue(Object, Class)}.
     */
    @Test
    public final void testConvertValueObjectClassOfT() {
        final DatasetContent datasetContent = new DatasetContent(myID);
        final ObjectNode objNode = JSON.createObjectNode();

        objNode.put(JsonKeys.ID, myID);
        objNode.put(JsonKeys.TYPE, ResourceTypes.DATASET);

        assertEquals(datasetContent, JSON.convertValue(objNode, DatasetContent.class));
    }

    /**
     * Test method for {@link JSON#convertValue(Object, JavaType)}.
     */
    @Test
    public final void testConvertValueObjectJavaType() {
        final JavaType type = JSON.getTypeFactory().constructParametricType(AnnotationPage.class, WebAnnotation.class);
        final BookmarkingAnnotation annotation = new BookmarkingAnnotation(myID, new Target(myID));
        final AnnotationPage<WebAnnotation> page = new AnnotationPage<>(myID);
        final ObjectNode annoNode = JSON.createObjectNode();
        final ObjectNode rootNode = JSON.createObjectNode();

        page.addAnnotations(annotation);

        rootNode.put(JsonKeys.ID, myID);
        rootNode.put(JsonKeys.TYPE, ResourceTypes.ANNOTATION_PAGE);
        annoNode.put(JsonKeys.ID, myID);
        annoNode.put(JsonKeys.TYPE, ResourceTypes.ANNOTATION);
        annoNode.put(JsonKeys.MOTIVATION, Purpose.BOOKMARKING.toString());
        annoNode.put(JsonKeys.TARGET, myID);
        rootNode.putArray(JsonKeys.ITEMS).add(annoNode);

        assertEquals(page, JSON.convertValue(rootNode, type));
    }

    /**
     * Test method for {@link JSON#convertValue(Object, TypeReference)}.
     */
    @Test
    public final void testConvertValueObjectTypeReferenceOfT() {
        final TypeReference<AnnotationPage<WebAnnotation>> typeRef = new TypeReference<>() {};
        final BookmarkingAnnotation annotation = new BookmarkingAnnotation(myID, new Target(myID));
        final AnnotationPage<WebAnnotation> page = new AnnotationPage<>(myID);
        final ObjectNode annoNode = JSON.createObjectNode();
        final ObjectNode rootNode = JSON.createObjectNode();

        page.addAnnotations(annotation);

        rootNode.put(JsonKeys.ID, myID);
        rootNode.put(JsonKeys.TYPE, ResourceTypes.ANNOTATION_PAGE);
        annoNode.put(JsonKeys.ID, myID);
        annoNode.put(JsonKeys.TYPE, ResourceTypes.ANNOTATION);
        annoNode.put(JsonKeys.MOTIVATION, Purpose.BOOKMARKING.toString());
        annoNode.put(JsonKeys.TARGET, myID);
        rootNode.putArray(JsonKeys.ITEMS).add(annoNode);

        assertEquals(page, JSON.convertValue(rootNode, typeRef));
    }

    /**
     * Test method for {@link JSON#createArrayNode()}.
     */
    @Test
    public final void testCreateArrayNode() {
        assertTrue(JSON.createArrayNode() instanceof ArrayNode);
    }

    /**
     * Test method for {@link JSON#createObjectNode()}.
     */
    @Test
    public final void testCreateObjectNode() {
        assertTrue(JSON.createObjectNode() instanceof ObjectNode);
    }

    /**
     * Test method for {@link JSON#getPrettyWriter()}.
     */
    @Test
    public final void testGetPrettyWriter() throws JsonProcessingException {
        final ObjectWriter writer = JSON.getPrettyWriter();
        final String json = StringUtils.format(TEST_JSON, myID);

        assertEquals(json.trim(), writer.writeValueAsString(JSON.readValue(json, DatasetContent.class)));
    }

    /**
     * Test method for {@link JSON#getReader()}.
     */
    @Test
    public final void testGetReader() throws IOException {
        final ObjectWriter writer = JSON.getPrettyWriter();
        final String json = StringUtils.format(TEST_JSON, myID);

        assertEquals(json.trim(), writer.writeValueAsString(JSON.getReader().readValue(json, DatasetContent.class)));
    }

    /**
     * Test method for {@link JSON#getReader(Class)}.
     */
    @Test
    public final void testGetReaderClassOfT() throws JsonProcessingException {
        final ObjectWriter writer = JSON.getPrettyWriter();
        final String json = StringUtils.format(TEST_JSON, myID);

        assertEquals(json.trim(), writer.writeValueAsString(JSON.getReader(DatasetContent.class).readValue(json)));
    }

    /**
     * Test method for {@link JSON#getReader(JavaType)}.
     */
    @Test
    public final void testGetReaderJavaType() throws IOException {
        final JavaType type = JSON.getTypeFactory().constructParametricType(AnnotationPage.class, WebAnnotation.class);
        final BookmarkingAnnotation annotation = new BookmarkingAnnotation(myID, new Target(myID));
        final AnnotationPage<WebAnnotation> page = new AnnotationPage<>(myID);
        final ObjectNode annoNode = JSON.createObjectNode();
        final ObjectNode rootNode = JSON.createObjectNode();

        page.addAnnotations(annotation);

        rootNode.put(JsonKeys.ID, myID);
        rootNode.put(JsonKeys.TYPE, ResourceTypes.ANNOTATION_PAGE);
        annoNode.put(JsonKeys.ID, myID);
        annoNode.put(JsonKeys.TYPE, ResourceTypes.ANNOTATION);
        annoNode.put(JsonKeys.MOTIVATION, Purpose.BOOKMARKING.toString());
        annoNode.put(JsonKeys.TARGET, myID);
        rootNode.putArray(JsonKeys.ITEMS).add(annoNode);

        assertEquals(page, JSON.getReader(type).readValue(rootNode));
    }

    /**
     * Test method for {@link JSON#getReader(TypeReference)}.
     */
    @Test
    public final void testGetReaderTypeReferenceOfT() throws IOException {
        final TypeReference<AnnotationPage<WebAnnotation>> typeRef = new TypeReference<>() {};
        final BookmarkingAnnotation annotation = new BookmarkingAnnotation(myID, new Target(myID));
        final AnnotationPage<WebAnnotation> page = new AnnotationPage<>(myID);
        final ObjectNode annoNode = JSON.createObjectNode();
        final ObjectNode rootNode = JSON.createObjectNode();

        page.addAnnotations(annotation);

        rootNode.put(JsonKeys.ID, myID);
        rootNode.put(JsonKeys.TYPE, ResourceTypes.ANNOTATION_PAGE);
        annoNode.put(JsonKeys.ID, myID);
        annoNode.put(JsonKeys.TYPE, ResourceTypes.ANNOTATION);
        annoNode.put(JsonKeys.MOTIVATION, Purpose.BOOKMARKING.toString());
        annoNode.put(JsonKeys.TARGET, myID);
        rootNode.putArray(JsonKeys.ITEMS).add(annoNode);

        assertEquals(page, JSON.getReader(typeRef).readValue(rootNode));
    }

    /**
     * Test method for {@link JSON#getTypeFactory()}.
     */
    @Test
    public final void testGetTypeFactory() {
        assertTrue(JSON.getTypeFactory() instanceof TypeFactory);
    }

    /**
     * Test method for {@link JSON#getWriter()}.
     */
    @Test
    public final void testGetWriter() {
        assertTrue(JSON.getWriter() instanceof ObjectWriter);
    }

    /**
     * Test method for {@link JSON#getWriter(java.lang.Class)}.
     */
    @Test
    public final void testGetWriterClassOfT() {
        assertTrue(JSON.getWriter(DatasetContent.class).canSerialize(DatasetContent.class));
    }

    /**
     * Test method for {@link JSON#getWriter(JavaType)}.
     */
    @Test
    public final void testGetWriterJavaType() {
        final JavaType type = JSON.getTypeFactory().constructParametricType(AnnotationPage.class, WebAnnotation.class);
        assertTrue(JSON.getWriter(type).canSerialize(AnnotationPage.class));
    }

    /**
     * Test method for {@link JSON#getWriter(TypeReference)}.
     */
    @Test
    public final void testGetWriterTypeReferenceOfT() {
        final TypeReference<AnnotationPage<WebAnnotation>> typeRef = new TypeReference<>() {};
        assertTrue(JSON.getWriter(typeRef).canSerialize(AnnotationPage.class));
    }

    /**
     * Test method for {@link JSON#readValue(String, Class)}.
     */
    @Test
    public final void testReadValueStringClassOfT() {
        final String json = StringUtils.format(TEST_JSON, myID).trim();
        assertEquals(json, JSON.readValue(json, DatasetContent.class).toString());
    }

    /**
     * Test method for {@link JSON#readValue(String, JavaType)}.
     */
    @Test
    public final void testReadValueStringJavaType() throws IOException {
        final JavaType type = JSON.getTypeFactory().constructType(DatasetContent.class);
        final String json = StringUtils.format(TEST_JSON, myID).trim();

        assertEquals(json, JSON.getReader(type).readValue(json).toString());
    }

    /**
     * Test method for {@link JSON#readValue(String, TypeReference)}.
     */
    @Test
    public final void testReadValueStringTypeReferenceOfT() throws IOException {
        final TypeReference<DatasetContent> typeRef = new TypeReference<>() {};
        final String json = StringUtils.format(TEST_JSON, myID).trim();

        assertEquals(json, JSON.getReader(typeRef).readValue(json).toString());
    }

    /**
     * Test method for {@link JSON#valueToTree(Object)}.
     */
    @Test
    public final void testValueToTree() {
        final JavaType type = JSON.getTypeFactory().constructType(DatasetContent.class);
        final String json = StringUtils.format(TEST_JSON, myID).trim();
        final JsonNode root = JSON.valueToTree(JSON.readValue(json, type));

        assertEquals(json, root.toPrettyString());
    }

}
