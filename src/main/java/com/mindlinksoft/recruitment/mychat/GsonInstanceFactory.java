package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class GsonInstanceFactory {
	
	private static Gson gson;
	
	static Gson getGson() {
		if(null == gson) {

			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

			gson = gsonBuilder.create();
		}
		
		return gson;
	}
	
}
