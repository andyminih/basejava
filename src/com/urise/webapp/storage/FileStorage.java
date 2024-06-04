package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStorage extends AbstractFileStorage {

    final SerializationStrategy ss;

    public FileStorage(File directory, SerializationStrategy ss) {
        super(directory);
        this.ss = ss;
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        return ss.doRead(is);
    }

    @Override
    protected void doWrite(OutputStream os, Resume resume) throws IOException {
        ss.doWrite(os, resume);
    }
}
