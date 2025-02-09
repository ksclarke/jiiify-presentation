
package info.freelibrary.iiif.presentation.v3.utils.json;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A filter provider for a filter that excludes a context list during serialization.
 */
public class ContextFilterProvider extends SimpleFilterProvider {

    /** The name of the context filter provided by this provider. */
    public static final String FILTER_NAME = "contextFilter";

    /** The <code>serialVersionUID</code> for {@code ContextFilterProvider}. */
    private static final long serialVersionUID = -9098052112188227394L;

    /**
     * Creates a new {@code ContextFilterProvider}.
     */
    public ContextFilterProvider() {
        this(false);
    }

    /**
     * Creates a new {@code ContextFilterProvider} with a boolean flag that determines whether it should filter
     * contexts.
     *
     * @param aIgnoreFlag A boolean flag indicating whether or not the filter should be ignored
     */
    public ContextFilterProvider(final boolean aIgnoreFlag) {
        final String name;

        if (aIgnoreFlag) {
            name = "IGNORE_NOTHING_PROCESS_ALL_THE_THINGS";
        } else {
            name = JsonKeys.CONTEXT;
        }

        super.addFilter(FILTER_NAME, SimpleBeanPropertyFilter.serializeAllExcept(name));
    }
}
