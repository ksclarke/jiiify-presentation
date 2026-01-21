
package info.freelibrary.iiif.presentation.v3.utils.csv;

import static info.freelibrary.iiif.presentation.v3.ResourceTypes.IMAGE;
import static info.freelibrary.iiif.presentation.v3.ResourceTypes.IMAGE_SERVICE_2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.content.ImageContent;
import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.id.MinterFactory;
import info.freelibrary.iiif.presentation.v3.services.ImageService;
import info.freelibrary.iiif.presentation.v3.services.ImageService2;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.ThrowingConsumer;
import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * A mapper for reading CSV data and mapping it into a local database.
 */
@SuppressWarnings({ PMD.EXCESSIVE_IMPORTS, PMD.COUPLING_BETWEEN_OBJECTS })
public class Mapper {

    /** The logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Mapper.class, MessageCodes.BUNDLE);

    /** File extension for IIIF (International Image Interoperability Framework) JSON files. */
    private static final String JSON_EXT = ".json";

    /** The data map for this class. */
    private final HTreeMap<String, String> myCsvData;

    /** Index of ParentID -> Set of ItemIDs (children). */
    private final HTreeMap<String, Set<String>> myParents;

    /** Index of ItemID -> ObjectType. */
    private final HTreeMap<String, Set<String>> myObjTypes;

    /** Index of child IDs. */
    private final Set<String> myChildren;

    /** An instance of ObjectMapper used for handling JSON object mapping operations. */
    private final ObjectMapper myMapper;

    /** The output ZIP file for the mapped data. */
    private final Path myOutputFile;

    /** The builder for the IIIF Presentation API v3 resources. */
    private final Builder myBuilder;

    /** The database for the CSV data. */
    private final DB myDatabase;

    /** The ZipWriter for writing the mapped data to a ZIP file. */
    private final ZipWriter myZipWriter;

    /**
     * Creates a new CSV mapper.
     *
     * @param aCsvFile A CSV to read in and map
     * @param aOutputFile A file to write the mapped data to
     * @throws MappingException If there is trouble mapping the CSV data
     * @throws IOException If there is trouble reading the CSV file
     */
    @SuppressWarnings({ JDK.UNCHECKED }) // Warnings for the Serializer.JAVA
    public Mapper(final Stream<Path> aCsvFile, final Path aOutputFile) throws MappingException, IOException {
        final Reader reader = new Reader();

        // Auto-close the database via the db's config options, but also provide a manual close() method below
        myDatabase = createDatabase(Files.createTempFile("jpv3-", ".db"));

        // Initialize our internal JSON mapper
        myMapper = new ObjectMapper().registerModule(new Jdk8Module());
        myOutputFile = Objects.requireNonNull(aOutputFile, LOGGER.getMessage(MessageCodes.JPA_171));
        myZipWriter = new ZipWriter(myOutputFile);

        // Initialize our internal mapping database
        myCsvData = myDatabase.hashMap("csv_data", Serializer.STRING, Serializer.STRING).createOrOpen();
        myParents = myDatabase.hashMap("parents", Serializer.STRING, Serializer.JAVA).createOrOpen();
        myObjTypes = myDatabase.hashMap("obj_types", Serializer.STRING, Serializer.JAVA).createOrOpen();
        myChildren = myDatabase.hashSet("children", Serializer.STRING).createOrOpen();

        // Read the source file(s) and populate the database's indices
        reader.rows(aCsvFile).forEach(ThrowingConsumer.sneaky(row -> {
            final String rowID = row.getItemID().orElse(UUID.randomUUID().toString());
            final String rowValue = myMapper.writeValueAsString(row);

            // Create the main data index, which uses ID as the index's key
            myCsvData.put(rowID, rowValue);

            // Create an index that supports child lookup by parent ID
            row.getParentID().ifPresent(parentID -> {
                final Set<String> idSet = myParents.compute(parentID,
                        (key, existing) -> Objects.requireNonNullElseGet(existing, LinkedHashSet::new));

                LOGGER.trace(MessageCodes.JPA_165, rowID,
                        row.getObjectType().isPresent() ? row.getObjectType().get() : "UNKNOWN", parentID);

                idSet.add(rowID);
                myParents.put(parentID, idSet);

                // Record that this ID is a child so we can know this without knowing its parent ID
                myChildren.add(rowID);
            });

            // Create an index that supports lookup by object type
            row.getObjectType().ifPresent(objectType -> {
                final Set<String> idSet = myObjTypes.compute(objectType,
                        (key, existing) -> Objects.requireNonNullElseGet(existing, LinkedHashSet::new));

                LOGGER.trace(MessageCodes.JPA_166, rowID, objectType);

                idSet.add(rowID);
                myObjTypes.put(objectType, idSet);
            });
        }));

        myBuilder = new Builder();
    }

    /**
     * Gets the result of the mapping.
     *
     * @return The result of the mapping
     * @throws MappingException If there is trouble mapping the CSV data
     * @throws IOException If there is trouble reading the CSV file
     */
    public int map() throws MappingException, IOException {
        final List<String> collections = myObjTypes.getOrDefault(Keys.COLLECTION, Set.of()).stream()
                .filter(id -> !myChildren.contains(id)).toList();
        final int result;

        // Check to see if our CSV data has any collections, our highest level in the hierarchy
        if (!collections.isEmpty()) {
            result = mapCollections(collections).toFile().exists() ? 0 : -1;
        } else {
            final List<String> works = myObjTypes.getOrDefault(Keys.WORK, Set.of()).stream().toList();
            result = works.size(); // FIXME: placeholder to return an int
        }

        close();
        return result;
    }

    /**
     * Determines if the provided row represents a collection by checking its object type or resource type against a
     * defined collection key.
     *
     * @param aRow The row to evaluate, containing potential object and resource type data
     * @return {@code true} if the row represents a collection, otherwise {@code false}
     */
    protected boolean isCollection(final Row aRow) {
        final Optional<String> objectType = aRow.getObjectType();
        final Optional<String> resourceType;

        if (objectType.isPresent() && Keys.COLLECTION.equalsIgnoreCase(objectType.get())) {
            return true;
        }

        // We include IIIF Collections (which may be considered a "work" in another context: e.g., a periodical issue).
        resourceType = aRow.getResourceType();
        return resourceType.isPresent() && Keys.COLLECTION.equalsIgnoreCase(resourceType.get());
    }

    /**
     * Determines if the provided row represents a manifest by checking its object type against a defined manifest key.
     *
     * @param aRow The row to evaluate, containing potential object type data
     * @return {@code true} if the row represents a manifest, otherwise {@code false}
     */
    protected boolean isManifest(final Row aRow) {
        final AtomicBoolean isManifest = new AtomicBoolean(false);

        aRow.getObjectType().ifPresent(objectType -> {
            if (Keys.WORK.equalsIgnoreCase(objectType)) {
                isManifest.set(true);

                // We allow for the work designation to be overridden by a resource type of "collection".
                aRow.getResourceType().ifPresent(resourceType -> {
                    if (Keys.COLLECTION.equalsIgnoreCase(resourceType)) {
                        isManifest.set(false);
                    }
                });
            }
        });

        return isManifest.get();
    }

    /**
     * Maps the CSV data for collections.
     *
     * @param aCollectionList A set of collection IDs
     * @return A ZIP file containing the mapped collection data
     * @throws MappingException If there is trouble mapping the CSV data
     * @throws IOException If there is trouble reading the CSV file
     */
    @SuppressWarnings({ PMD.COGNITIVE_COMPLEXITY })
    protected Path mapCollections(final List<String> aCollectionList) throws MappingException, IOException {
        LOGGER.debug(MessageCodes.JPA_167, aCollectionList.size());

        // Descend through the CSV data, starting at the collections
        for (final String collectionID : aCollectionList) {
            final Row collectionRow = myMapper.readValue(myCsvData.get(collectionID), Row.class);
            LOGGER.debug(MessageCodes.JPA_168, collectionID);

            // Build the collection document from a CSV data row
            final Collection collection = myBuilder.build(collectionRow);
            final Set<String> childIDs;

            if ((childIDs = myParents.get(collectionID)) != null) {
                try {
                    LOGGER.debug(MessageCodes.JPA_169, childIDs.size(), collectionID);

                    // Cycle through the children (they may be child collections or manifests)
                    for (final String childID : childIDs) {
                        final Row childRow = myMapper.readValue(myCsvData.get(childID), Row.class);
                        LOGGER.debug(MessageCodes.JPA_170, childID);

                        // Check that there is an object type for the child row
                        childRow.getObjectType().orElseThrow(() -> new MappingException(MessageCodes.JPA_172));

                        if (isCollection(childRow)) {
                            final Collection childCollection = myBuilder.build(childRow);

                            // Add the child collection to its parent and fully map it, too
                            collection.getItems().add(new Collection.Item(childCollection));
                            mapCollections(List.of(childRow.getItemID().orElseThrow()));
                        } else if (isManifest(childRow)) {
                            final List<Manifest> manifests = mapManifests(List.of(childID));
                            final List<Collection.Item> items = collection.getItems();

                            manifests.forEach(manifest -> items.add(new Collection.Item(manifest)));
                        }
                    }
                } catch (final JsonProcessingException details) {
                    throw new MappingException(details);
                }
            }

            myZipWriter.writeFile(collectionID + JSON_EXT, collection.toString());
        }

        return myOutputFile;
    }

    /**
     * Maps a list of manifest identifiers into a list of {@code Manifest} objects by processing the input data and
     * constructing the corresponding manifest instances. Each manifest is generated by reading associated data, mapping
     * it to manifest properties, and attaching related canvases. The result includes JSON serialization of each
     * manifest.
     *
     * @param aManifestList the list of manifest identifiers to be processed and mapped
     * @return a list of {@code Manifest} objects corresponding to the provided identifiers
     * @throws MappingException if there is a problem during the mapping process
     * @throws IOException if there is a problem accessing the data for manifests
     */
    protected List<Manifest> mapManifests(final List<String> aManifestList) throws MappingException, IOException {
        final List<Manifest> manifests = new ArrayList<>();

        for (final String manifestID : aManifestList) {
            try {
                final Row row = myMapper.readValue(myCsvData.get(manifestID), Row.class);
                final Manifest manifest = myBuilder.build(row);
                final Minter minter = MinterFactory.getMinter(manifest);
                final List<Canvas> canvases = mapCanvases(manifestID, minter);

                manifest.setCanvases(canvases);
                manifests.add(manifest);

                myZipWriter.writeFile(manifestID + JSON_EXT, manifest.toString());
            } catch (final JsonProcessingException details) {
                throw new MappingException(details);
            }
        }

        return manifests;
    }

    /**
     * Maps canvases for a given manifest by processing and constructing canvas objects using the provided manifest ID
     * and minter.
     *
     * @param aManifestID The manifest ID for which canvases are being mapped
     * @param aMinter A minter used for generating unique identifiers for the canvas objects
     * @return A list of canvases associated with the specified manifest
     */
    @SuppressWarnings({ PMD.COGNITIVE_COMPLEXITY })
    protected List<Canvas> mapCanvases(final String aManifestID, final Minter aMinter) {
        final List<Canvas> canvases = new ArrayList<>();

        myParents.getOrDefault(aManifestID, Set.of()).forEach(ThrowingConsumer.sneaky(canvasID -> {
            final Row canvasRow = myMapper.readValue(myCsvData.get(canvasID), Row.class);
            final Canvas canvas = myBuilder.build(canvasRow, aMinter);
            final int canvasWidth = canvasRow.getMediaWidth().orElse(0);
            final int canvasHeight = canvasRow.getMediaHeight().orElse(0);
            final String target = canvasRow.getTarget().orElse(null);
            final AtomicInteger width = new AtomicInteger(canvasWidth);
            final AtomicInteger height = new AtomicInteger(canvasHeight);
            final Set<String> children = myParents.get(canvasID);

            if (children == null || children.isEmpty()) {
                // If we don't have any children, we're probably dealing with a single image we can paint directly
                canvasRow.getFileName().ifPresent(ThrowingConsumer.sneaky(fileName -> {
                    final ImageContent imageContent = new ImageContent(myBuilder.checkID(canvasID, IMAGE));
                    final ImageService imageService = new ImageService2(myBuilder.checkID(canvasID, IMAGE_SERVICE_2));

                    imageContent.setServices(imageService);

                    if (target != null) {
                        canvas.paintWith(target, imageContent);
                    } else {
                        canvas.paintWith(imageContent);
                    }
                }));
            } else {
                final List<ContentResource> choiceResources = new ArrayList<>();
                final List<ContentResource> layerResources = new ArrayList<>();

                children.forEach(ThrowingConsumer.sneaky(paintedID -> {
                    final Row paintedRow = myMapper.readValue(myCsvData.get(paintedID), Row.class);
                    final int paintedWidth = paintedRow.getMediaWidth().orElse(0);
                    final int paintedHeight = paintedRow.getMediaHeight().orElse(0);

                    width.updateAndGet(w -> (canvasWidth == 0 && paintedWidth > w) ? paintedWidth : w);
                    height.updateAndGet(h -> (canvasHeight == 0 && paintedHeight > h) ? paintedHeight : h);

                    paintedRow.getObjectType().ifPresent(ThrowingConsumer.sneaky(objectType -> {
                        switch (objectType) {
                            case Keys.CHOICE -> choiceResources.add(myBuilder.build(paintedRow, aMinter));
                            case Keys.LAYER -> layerResources.add(myBuilder.build(paintedRow, aMinter));
                            default -> throw new MappingException();
                        }
                    }));
                }));

                if (!choiceResources.isEmpty()) {
                    if (target != null) {
                        canvas.paintWith(target, true, choiceResources);
                    } else {
                        canvas.paintWith(true, choiceResources);
                    }
                }

                layerResources.forEach(layerResource -> {
                    if (target != null) {
                        canvas.paintWith(target, false, layerResource);
                    } else {
                        canvas.paintWith(false, layerResource);
                    }
                });
            }

            // Only set width and height if the canvas doesn't have pre-existing dimensions
            if (canvasWidth == 0 && canvasHeight == 0 && width.get() > 0 && height.get() > 0) {
                canvas.setWidthHeight(width.get(), height.get());
            }

            canvases.add(canvas);
        }));

        return canvases;
    }

    /**
     * Closes the database.
     *
     * @throws IOException If there is trouble closing the database
     */
    public void close() throws IOException {
        myZipWriter.close();

        if (!myDatabase.isClosed()) {
            myDatabase.close();
        }
    }

    /**
     * Creates a database for the CSV file.
     *
     * @param aCsvFile A CSV file with the data we need
     * @return A data map for the CSV file
     * @throws IOException If there is trouble creating the data map
     */
    private DB createDatabase(final Path aCsvFile) throws IOException {
        final long dbSize = aCsvFile.toFile().length() * 2; // Ballpark db size, based on CSV file
        final long allocationSize = 64 * 1024 * 1024; // Was 256 * 1024 * 1024 before
        final DBMaker.Maker maker = DBMaker.tempFileDB();

        // We don't use cleanerHackEnable() because it accesses Unsafe Java code and newer JDKs warn about it
        maker.fileMmapEnableIfSupported().fileMmapPreclearDisable().fileDeleteAfterClose();
        return maker.closeOnJvmShutdown().allocateIncrement(allocationSize).allocateStartSize(dbSize).make();
    }
}
