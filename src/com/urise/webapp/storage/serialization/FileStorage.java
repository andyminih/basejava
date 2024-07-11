package com.urise.webapp.storage.serialization;

import com.urise.webapp.storage.AbstractStorage;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private final SerializationStrategy serializationStrategy;
    private final File directory;

    public FileStorage(File directory, SerializationStrategy serializationStrategy) {
        this.serializationStrategy = serializationStrategy;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable|writable");
        }
        this.directory = directory;
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return serializationStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            serializationStrategy.doWrite(new BufferedOutputStream(new FileOutputStream(file)), resume);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("IO error", file.getName());
        }
    }

    @Override
    protected boolean isExisting(File file) {
        return file.exists();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected List<Resume> doGetAll() {
        final List<Resume> list = new ArrayList<>();

        for (File file : getCheckedListFiles()) {
            if (file.isFile()) {
                list.add(doGet(file));
            }
        }
        return list;
    }

    @Override
    public void clear() {
        for (File file : getCheckedListFiles()) {
            if (file.isFile()) {
                doDelete(file);
            }
        }
    }

    @Override
    public int size() {
        return getCheckedListFiles().length;
    }

    private File[] getCheckedListFiles() {
        final File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("IO error", directory.getName());
        }
        return files;
    }

    @Override
    protected void doSave(File file, Resume resume) {
        try {
            if (!file.createNewFile()) {
                throw new StorageException("IO error", file.getName());
            }
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }

        try {
            serializationStrategy.doWrite(new BufferedOutputStream(new FileOutputStream(file)), resume);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

}
