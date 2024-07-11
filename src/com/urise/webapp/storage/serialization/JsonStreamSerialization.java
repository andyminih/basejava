package com.urise.webapp.storage.serialization;

import com.urise.webapp.util.JsonParser;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStreamSerialization implements SerializationStrategy {

    public JsonStreamSerialization() {
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return JsonParser.read(r, Resume.class);
        }
    }

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            JsonParser.write(resume, w);
        }
    }
}
