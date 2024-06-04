package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected AbstractPathStorage(String dir) {
        Path directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + "is not directory");
        }
        if (!Files.isWritable(directory) || !Files.isReadable(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + "is not readable|writable");
        }
        this.directory = directory;
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("IO error", path.toAbsolutePath().toString(), e);
        }
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            doWrite(new BufferedOutputStream(new FileOutputStream(path.toFile())), resume);
        } catch (IOException e) {
            throw new StorageException("IO error", path.toAbsolutePath().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("IO error", path.toAbsolutePath().toString(), e);
        }
    }

    @Override
    protected void doSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
            doWrite(new BufferedOutputStream(new FileOutputStream(path.toFile())), resume);
        } catch (IOException e) {
            throw new StorageException("IO error", path.toAbsolutePath().toString(), e);
        }
    }

    @Override
    protected boolean isExisting(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toAbsolutePath().toString(), uuid);
    }

    @Override
    protected List<Resume> doGetAll() {
        final List<Resume> list = new ArrayList<>();

        try {
            Files.list(directory).forEach(path -> {
                if (!Files.isDirectory(path)) {
                    list.add(doGet(path));
                }
            });
        } catch (IOException e) {
            throw new StorageException("IO error", directory.toAbsolutePath().toString(), e);
        }


        return list;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("IO error", directory.toAbsolutePath().toString(), e);
        }
    }

    @Override
    public int size() {
        final int[] size = {0};
        try {
            Files.list(directory).forEach(path -> {
                if (!Files.isDirectory(path)) {
                    size[0]++;
                }
            });
        } catch (IOException e) {
            throw new StorageException("IO error", directory.toAbsolutePath().toString(), e);
        }
        return size[0];
    }

    protected abstract Resume doRead(InputStream is) throws IOException;

    protected abstract void doWrite(OutputStream os, Resume resume) throws IOException;

}
