
package info.freelibrary.iiif.presentation.v3.utils.csv;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.warnings.JDK;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A mapper for reading CSV data and mapping it into a local database.
 */
public class Mapper {

    /** The logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Mapper.class, MessageCodes.BUNDLE);

    /** The data map for this class. */
    private final HTreeMap<String, String> myData;

    /** Index of ParentID -> Set of ItemIDs (children). */
    private final HTreeMap<String, Set<String>> myParentIndex;

    /** Index of ItemID -> ObjectType. */
    private final HTreeMap<String, String> myObjTypeIndex;

    /**
     * Creates a new CSV mapper.
     *
     * @param aCsvFile A CSV to read in and map
     * @throws IOException If there is trouble reading the CSV file
     */
    @SuppressWarnings({ JDK.UNCHECKED }) // Warnings for the Serializer.JAVA
    public Mapper(final Path aCsvFile) throws IOException {
        final ObjectReader reader = initializeCsvReader(CsvSchema.emptySchema().withHeader());
        final ObjectMapper mapper = new ObjectMapper().registerModule(new Jdk8Module());

        // Build the database and its indices
        try (MappingIterator<Row> iterator = reader.readValues(Files.newBufferedReader(aCsvFile, UTF_8));
                DB db = getDataMap(aCsvFile)) {

            // We build a simple little `Row` database so we can do more than just iterate over the data
            myData = db.hashMap("csv_data", Serializer.STRING, Serializer.STRING).createOrOpen();
            myParentIndex = db.hashMap("by_parent", Serializer.STRING, Serializer.JAVA).createOrOpen();
            myObjTypeIndex = db.hashMap("by_obj_type", Serializer.STRING, Serializer.STRING).createOrOpen();

            while (iterator.hasNext()) {
                final Row row = iterator.next();
                final String rowID = row.getItemID().orElse(UUID.randomUUID().toString());
                final String rowValue = mapper.writeValueAsString(row);

                // Populate the item ID map
                myData.put(rowID, rowValue);

                // Populate the parent ID map
                row.getParentID().ifPresent(parentId -> {
                    myParentIndex.computeIfAbsent(parentId, key -> new LinkedHashSet<>()).add(rowID);
                });

                row.getObjectType().ifPresent(objectType -> {
                    myObjTypeIndex.put(rowID, objectType);
                });
            }
        } // auto-closes db and iterator

        LOGGER.info("Done!");
    }

    /**
     * Gets the result of the mapping.
     *
     * @return The result of the mapping
     */
    public int result() {
        return 0;
    }

    /**
     * Gets a data map for the CSV file.
     *
     * @param aCsvFile A CSV file with the data we need
     * @return A data map for the CSV file
     * @throws IOException If there is trouble creating the data map
     */
    private DB getDataMap(final Path aCsvFile) throws IOException {
        final long dbSize = aCsvFile.toFile().length() * 2; // Ballpark db size, based on CSV file
        final long allocationSize = 256 * 1024 * 1024;

        return DBMaker.tempFileDB().fileMmapEnableIfSupported().fileMmapPreclearDisable().cleanerHackEnable()
                .fileDeleteAfterClose().closeOnJvmShutdown().allocateIncrement(allocationSize).allocateStartSize(dbSize)
                .make();
    }

    /**
     * Initializes and configures an ObjectReader for reading CSV data based on the specified schema.
     *
     * @param aSchema the CsvSchema defining the structure and rules for parsing the CSV data
     * @return an ObjectReader configured to read CSV data into Row objects using the provided schema
     */
    private ObjectReader initializeCsvReader(final CsvSchema aSchema) {
        return new CsvMapper().enable(CsvParser.Feature.TRIM_SPACES).readerFor(Row.class).with(aSchema);
    }
}
