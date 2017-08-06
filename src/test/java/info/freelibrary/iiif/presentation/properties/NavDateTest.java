
package info.freelibrary.iiif.presentation.properties;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;

import org.junit.Test;

public class NavDateTest {

    @Test
    public void test() {
        final NavDate navDate = NavDate.now();
        final ZonedDateTime zdt1 = ZonedDateTime.parse(navDate.getString());
        final ZonedDateTime zdt2 = navDate.getZonedDateTime();

        assertEquals(zdt1, zdt2);
    }

}
