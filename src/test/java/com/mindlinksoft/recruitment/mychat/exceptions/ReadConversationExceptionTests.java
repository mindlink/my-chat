package com.mindlinksoft.recruitment.mychat.exceptions;

import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.exceptions.ReadConversationException;

/**
 * Tests for the {@link ReadConversatioException}.
 */
public class ReadConversationExceptionTests {
	
	/**
     * Tests that the {@link ReadConversationException} gets thrown.
     */
    @Test (expected = ReadConversationException.class)
    public void testReadConversationExceptionThrown() {
    	_throwReadConversationException(null);
    }
    
    /**
     * Method that will throw a {@link ReadConversationException}.
     */
    private void _throwReadConversationException(String string) throws ReadConversationException {
    	if (string == null) {
    		throw new ReadConversationException();
    	}
    }
}
