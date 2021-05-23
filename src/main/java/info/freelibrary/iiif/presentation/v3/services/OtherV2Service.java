
package info.freelibrary.iiif.presentation.v3.services;

import java.net.URI;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;

import info.freelibrary.iiif.presentation.v3.Service;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A generic service class for older service implementations that use @id and @type.
 */
@JsonPropertyOrder({ JsonKeys.ID, JsonKeys.TYPE, JsonKeys.PROFILE, JsonKeys.FORMAT })
public class OtherV2Service extends OtherService implements Service {

    /**
     * Creates a service for the supplied URI.
     *
     * @param aServiceID A service ID
     * @param aType A service type
     */
    public OtherV2Service(final URI aServiceID, final String aType) {
        super(aServiceID, aType);
    }

    /**
     * Creates a service for the supplied ID.
     *
     * @param aServiceID A service ID in string form
     * @param aType A service type
     */
    public OtherV2Service(final String aServiceID, final String aType) {
        super(aServiceID, aType);
    }

    @Override
    @JsonValue
    protected Object toJsonValue() {
        if (myID != null && myType != null) {
            final LinkedHashMap<String, Object> map = new LinkedHashMap<>();

            if (myID != null) {
                map.put(JsonKeys.V2_ID, myID);
            }

            if (myType != null) {
                map.put(JsonKeys.V2_TYPE, myType);
            }

            if (myProfile != null) {
                map.put(JsonKeys.PROFILE, myProfile);
            }

            if (myFormat != null) {
                map.put(JsonKeys.FORMAT, getFormat());
            }

            if (myServices != null && myServices.size() > 0) {
                map.put(JsonKeys.SERVICE, myServices);
            }

            return ImmutableMap.copyOf(map);
        } else {
            return null;
        }
    }
}
