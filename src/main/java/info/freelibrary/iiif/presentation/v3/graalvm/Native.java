
package info.freelibrary.iiif.presentation.v3.graalvm;

/**
 * BEGIN GENERATED CODE
 */
public class Native { // NOPMD

    /**
     * Creates an isolate.
     */
    private static native long createIsolate();

    /**
     * Performs the add.
     *
     * @param aIsolateThreadID An isolate thread
     * @param aFirstInt A first integer
     * @param aSecondInt A second integer
     * @return An integer result
     */
    private static native int add(long aIsolateThreadID, int aFirstInt, int aSecondInt);

    public Native() throws NoSuchFieldException, IllegalAccessException { // NOPMD
        System.loadLibrary("jpv3"); // NOPMD

        final long isolateThread = createIsolate();

        System.out.println("2 + 40 = " + add(isolateThread, 2, 40)); // NOPMD
        System.out.println("12 + 30 = " + add(isolateThread, 12, 30)); // NOPMD
        System.out.println("20 + 22 = " + add(isolateThread, 20, 22)); // NOPMD
    }

    public static void main(final String[] args) throws Exception { // NOPMD
        new Native();
    }

}
