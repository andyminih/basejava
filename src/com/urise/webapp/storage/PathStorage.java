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
import java.util.function.Consumer;

public class PathStorage extends AbstractStorage<Path> {

    private final Path directory;
    private SerializationStrategy ss;

    public PathStorage(String dir, SerializationStrategy ss) {
        this.ss = ss;
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
        doWrite(path, resume);
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
        } catch (IOException e) {
            throw new StorageException("IO error", path.toAbsolutePath().toString(), e);
        }
        doWrite(path, resume);
    }

    @Override
    protected boolean isExisting(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    protected void forEachFile(Consumer<Path> action) {
        try {
            Files.list(directory).forEach(action);
        } catch (IOException e) {
            throw new StorageException("IO error", directory.toAbsolutePath().toString(), e);
        }
    }

    @Override
    public int size() {
        final int[] size = {0};
        forEachFile(path -> {
            if (!Files.isDirectory(path)) {
                size[0]++;
            }
        });
        return size[0];
    }

    @Override
    public void clear() {
        forEachFile(PathStorage.this::doDelete);
    }

    @Override
    protected List<Resume> doGetAll() {
        final List<Resume> list = new ArrayList<>();
        forEachFile(path -> {
            if (!Files.isDirectory(path)) {
                list.add(doGet(path));
            }
        });
        return list;
    }

    private Resume doRead(InputStream is) throws IOException {
        return ss.doRead(is);
    }

    private void doWrite(Path path, Resume resume) {
        try {
            ss.doWrite(new BufferedOutputStream(new FileOutputStream(path.toFile())), resume);
        } catch (IOException e) {
            throw new StorageException("IO error", directory.toAbsolutePath().toString(), e);
        }
    }
}
