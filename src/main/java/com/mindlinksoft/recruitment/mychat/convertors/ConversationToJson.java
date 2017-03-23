package com.mindlinksoft.recruitment.mychat.convertors;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.model.Conversation;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by dpana on 3/22/2017.
 */
public class ConversationToJson {

    public static String convertConversationToJson(Conversation conversation) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
        Map<String, Long> usersMap =
                conversation.messages.stream().collect(Collectors.groupingBy(e -> e.senderId, Collectors.counting()));
        Map<String, Long> sortedUsersMap =
                usersMap.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new
                        ));

        Gson g = gsonBuilder.setPrettyPrinting().create(); // Use pretty print to make json output a bit readable
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("Conversation", g.toJsonTree(conversation));
        jsonObject.add("Activity Report", g.toJsonTree(sortedUsersMap));
        String json = g.toJson(jsonObject);
        return json;
    }

    static class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }

}
