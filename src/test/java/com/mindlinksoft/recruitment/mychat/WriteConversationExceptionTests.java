package com.mindlinksoft.recruitment.mychat;

import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.exceptions.WriteConversationException;

/**
 * Tests for the {@link WriteConversatioException}.
 */
public class WriteConversationExceptionTests {

	/**
     * Tests that the {@link WriteConversationException} gets thrown.
     */
    @Test (expected = WriteConversationException.class)
    public void testWriteConversationExceptionThrown() {
    	_throwWriteConversationException(null);
    }
    
    /**
     * Method that will throw a {@link WriteConversationException}.
     */
    private void _throwWriteConversationException(String string) throws WriteConversationException {
    	if (string == null) {
    		throw new WriteConversationException();
    	}
    }
}
