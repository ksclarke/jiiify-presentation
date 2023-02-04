
package info.freelibrary.iiif.presentation.v3;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.presentation.v3.properties.Behavior;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.NavDate;
import info.freelibrary.iiif.presentation.v3.utils.JsonKeys;

/**
 * A navigable resource.
 */
class NavigableResource<T extends NavigableResource<T>> extends AbstractResource<NavigableResource<T>> {

    /**
     * The date of the navigable resource.
     */
    private NavDate myNavDate;

    /**
     * Creates a navigable resource.
     *
     * @param aType A resource type in string form
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
     * Gets a navigation date.
     *
     * @return The navigation date
     */
    @JsonGetter(JsonKeys.NAV_DATE)
    public NavDate getNavDate() {
        return myNavDate;
    }

    /**
     * Sets a navigation date.
     *
     * @param aNavDate The navigation date
     * @return The navigable resource
     */
    @JsonSetter(JsonKeys.NAV_DATE)
    protected NavigableResource<T> setNavDate(final NavDate aNavDate) {
        myNavDate = aNavDate;
        return this;
    }
}
