package com.mindlinksoft.recruitment.mychat.exception;

import java.io.IOException;

/**
 *
 * @author Gabor
 */
public class ConverstaionWriterException extends IOException {

    public ConverstaionWriterException(final String filePath, final Throwable cause) {
        super("Error occurred while writing conversation to \"" + filePath + "\"", cause);
    }
}
