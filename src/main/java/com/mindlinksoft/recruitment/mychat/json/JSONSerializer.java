package com.mindlinksoft.recruitment.mychat.json;

import java.time.Instant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONSerializer {
	
	private final Gson gson; 

    public JSONSerializer()
    {
    	GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
        gsonBuilder.disableHtmlEscaping();
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }
    public String toJSON(Object obj)
    {
    	return gson.toJson(obj);
    }
}
