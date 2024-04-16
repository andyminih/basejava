package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistsStorageException;
import com.urise.webapp.exception.NotExistsStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage implements Storage {

    protected final static int STORAGE_LIMIT = 3;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final Resume get(String uuid) {
        final int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistsStorageException(uuid);
        }
        return storage[index];
    }

    public final void update(Resume resume) {
        final int index = findIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistsStorageException(resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public final int size() {
        return size;
    }

    public final void delete(String uuid) {
        final int index = findIndex(uuid);
        if (index >= 0) {
            removeResume(index);
            size--;
            storage[size] = null;
        }
    }

    public final void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException(r.getUuid(), "Невозможно добавить резюме. Хранилище переполнено.");
        }

        int index = findIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistsStorageException(r.getUuid());
        } else {
            insertResume(index, r);
            size++;
        }
    }

    protected abstract int findIndex(String uuid);

    protected abstract void removeResume(int index);

    protected abstract void insertResume(int index, Resume r);

}
