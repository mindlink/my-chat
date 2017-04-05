package com.mindlinksoft.recruitment.mychat.conversation.serialization;

/**
 * Serializer that converts an object to a string.
 *
 */
public interface ISerializer {

	/**
	 * Serializes an {@code Object} to a {@code String}
	 * 
	 * @param object
	 * @return Serialized string.
	 */
	String serialize(Object object);
	
}
