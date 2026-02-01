package gippy.exception;

/**
 * Specific exception class for Gippy-related errors.
 */
public class GippyException extends Exception {
    /**
     * Constructor for GippyException.
     * @param message The error message.
     */
    public GippyException(String message) {
        super(message);
    }
}
