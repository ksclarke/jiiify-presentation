
package info.freelibrary.iiif.presentation.v3.graalvm;

import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.word.Pointer;

/**
 * BEGIN GENERATED CODE
 */
public final class NativeImpl { // NOPMD

    @CEntryPoint(name = "Java_info_freelibrary_iiif_presentation_v3_graalvm_Native_createIsolate",
            builtin = CEntryPoint.Builtin.CREATE_ISOLATE)
    public static native IsolateThread createIsolate(); // NOPMD

    @CEntryPoint(name = "Java_info_freelibrary_iiif_presentation_v3_graalvm_Native_add")
    public static int add(final Pointer aJniEnv, final Pointer aClass, // NOPMD
            @CEntryPoint.IsolateThreadContext final long aIsolateID, final int aFirstInt, final int aSecondInt) {
        return aFirstInt + aSecondInt;
    }

}
