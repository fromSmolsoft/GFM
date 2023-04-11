package com.smol.gfm.exception;

/**
 * Custom exception for cases with invalid file extension.
 */
public class InvalidFileExtension extends Exception {
    private static final String ERROR_MESSAGE = "Invalid file extension.";

    public InvalidFileExtension() {
        super(ERROR_MESSAGE);
    }
}
