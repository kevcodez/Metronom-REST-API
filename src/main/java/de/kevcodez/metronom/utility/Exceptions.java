package de.kevcodez.metronom.utility;

/**
 * Helper class to throw unchecked exceptions.
 *
 * @author Kevin Grüneberg
 *
 */
public final class Exceptions {

    private Exceptions() {
        // Hidden constructor
    }

    /**
     * Rethrows a checked exception as unchecked exception. This method tricks the compiler into thinking the exception is
     * unchecked, rather than wrapping the given exception in a new {@code RuntimeException}.
     *
     * @param exc checked exception
     * @return syntactically, a runtime exception (but never actually returns)
     */
    public static RuntimeException unchecked(Exception exc) {
        Exceptions.adapt(exc);
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Exception> void adapt(Exception exc) throws T {
        throw (T) exc;
    }
}
