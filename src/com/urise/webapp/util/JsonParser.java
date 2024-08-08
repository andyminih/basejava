package com.urise.webapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.urise.webapp.model.Section;

import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;

public class JsonParser {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Section.class, new JsonSectionAdapter<Section>())
            .registerTypeAdapter(LocalDate.class, new JsonLocalDateAdapter<LocalDate>())
            .create();

    public static <T> T read(Reader reader, Class<T> tClass) {
        return GSON.fromJson(reader, tClass);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }

    public static <T> T read(String jsonString, Class<T> tClass) {
        return GSON.fromJson(jsonString, tClass);
    }

    public static <T> String write(T object) {
        return GSON.toJson(object, object.getClass());
    }
}
