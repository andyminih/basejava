package com.urise.webapp.storage.serialization;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationStrategy {
    Resume doRead(InputStream is) throws IOException;

    void doWrite(OutputStream os, Resume resume) throws IOException;
}
