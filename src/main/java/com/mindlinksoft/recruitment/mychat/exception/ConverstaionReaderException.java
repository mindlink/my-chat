package com.mindlinksoft.recruitment.mychat.exception;

import java.io.IOException;

/**
 *
 * @author Gabor
 */
public class ConverstaionReaderException extends IOException {

    public ConverstaionReaderException(final String filePath, final Throwable cause) {
        super("Error occurred while reading conversation from \"" + filePath + "\"", cause);
    }
}
