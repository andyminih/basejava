package com.urise.webapp.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class JsonLocalDateAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return (T) LocalDate.parse(jsonElement.getAsString());
    }

    @Override
    public JsonElement serialize(T localDate, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(localDate.toString());
    }
}
