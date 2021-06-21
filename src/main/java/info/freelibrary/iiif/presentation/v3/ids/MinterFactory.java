
package info.freelibrary.iiif.presentation.v3.ids;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;

/**
 * A factory that returns a minter used in automatically creating IDs for resources related to a manifest.
 */
public final class MinterFactory {

    /**
     * The minter name property.
     */
    public static final String MINTER_NAME_PROPERTY = "jp.noid.minter";

    /**
     * The environmental property for the minter name.
     */
    public static final String ENV_MINTER_NAME = "JP_NOID_MINTER";

    /**
     * The default minter class' name.
     */
    private static final String DEFAULT_MINTER_NAME = DefaultMinter.class.getName();

    /**
     * A map of minters.
     */
    private static final Map<URI, Minter> MINTERS = new HashMap<>();

    /**
     * We keep an internal modifiable environmental map so we can modify it during testing.
     */
    private static final Map<String, String> ENV_PROPERTIES;

    /**
     * The static map initializations.
     */
    static {
        ENV_PROPERTIES = new HashMap<>(); // MinterFactory map is modifiable
        ENV_PROPERTIES.putAll(System.getenv()); // System map is not modifiable
    }

    /**
     * Private constructor for the factory.
     */
    private MinterFactory() {
        // This is intentionally empty
    }

    /**
     * Gets a minter for the supplied manifest.
     *
     * @param aManifest The manifest associated with the minter
     * @return The minter associated with the manifest
     */
    public static Minter getMinter(final Manifest aManifest) {
        return MINTERS.getOrDefault(aManifest.getID(), getNewMinter(aManifest));
    }

    /**
     * Gets a minter for the supplied manifest ID.
     *
     * @param aManifestID The ID of a manifest associated with the minter
     * @return The minter associated with the supplied manifest ID
     */
    public static Minter getMinter(final URI aManifestID) {
        return MINTERS.getOrDefault(aManifestID, getNewMinter(aManifestID));
    }

    /**
     * Allows setting a particular minter implementation that should be used.
     *
     * @param aClass A minter implementation class
     */
    public static void setMinter(final Class<?> aClass) {
        ENV_PROPERTIES.put(MinterFactory.ENV_MINTER_NAME, aClass.getName());
    }

    /**
     * Allows removing a particular minter implementation that has been set.
     *
     * @return The class name of the previously set minter implementation
     */
    public static String clearMinter() {
        return ENV_PROPERTIES.remove(MinterFactory.ENV_MINTER_NAME);
    }

    /**
     * Returns and remembers a minter created for use with the supplied manifest.
     *
     * @param aManifest The manifest associated with this minter
     * @return A new minter for manifest component IDs
     */
    private static Minter getNewMinter(final Manifest aManifest) {
        try {
            final Class<?> clath = Class.forName(getMinterName());
            final Class<?>[] args = { Manifest.class };

            return MINTERS.put(aManifest.getID(), (Minter) clath.getDeclaredConstructor(args).newInstance(aManifest));
        } catch (IllegalAccessException | ClassNotFoundException | InstantiationException | NoSuchMethodException |
                InvocationTargetException details) {
            throw new MintingException(details, MessageCodes.JPA_107, details.getMessage());
        }
    }

    /**
     * Returns and remembers a minter created for use with the supplied manifest ID.
     *
     * @param aManifestID The ID of the manifest associated with this minter
     * @return A new minter for manifest component IDs
     */
    private static Minter getNewMinter(final URI aManifestID) {
        try {
            final Class<?> clath = Class.forName(getMinterName());
            final Class<?>[] args = { URI.class };

            return MINTERS.put(aManifestID, (Minter) clath.getDeclaredConstructor(args).newInstance(aManifestID));
        } catch (IllegalAccessException | ClassNotFoundException | InstantiationException | NoSuchMethodException |
                InvocationTargetException details) {
            throw new MintingException(details, MessageCodes.JPA_107, details.getMessage());
        }
    }

    /**
     * Gets the name of the minter implementation.
     *
     * @return The name of the minter implementation
     */
    private static String getMinterName() {
        final String minterName = ENV_PROPERTIES.get(ENV_MINTER_NAME);

        if (minterName == null) {
            return System.getProperty(MINTER_NAME_PROPERTY, DEFAULT_MINTER_NAME);
        }

        return minterName;
    }
}
