package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Factory class returning the appropriate gson intance to serialize a 
 * {@link Conversation}*/
class GsonInstanceFactory {
	
	private static Gson gson;
	
	/**
	 * Returns an appropriate {@link Gson} instance to serialize a {@link Conversation}.*/
	static Gson getGson() {
		if(null == gson) {

			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

			gson = gsonBuilder.create();
		}
		
		return gson;
	}
	
}
