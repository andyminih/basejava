package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected final static int STORAGE_LIMIT = 4;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected final Resume doGet(Object searchKey) {
        return storage[(int) searchKey];
    }

    protected final void doUpdate(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
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

    protected final void doDelete(Object searchKey) {
        deleteResume((int) searchKey);
        size--;
        storage[size] = null;
    }

    protected final void doSave(Object searchKey, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException(resume.getUuid(), "Невозможно добавить резюме. Хранилище переполнено.");
        }
        insertResume((int) searchKey, resume);
        size++;
    }

    protected abstract void deleteResume(int index);

    protected abstract void insertResume(int index, Resume r);

    @Override
    protected boolean isExisting(Object searchKey) {
        return (int) searchKey >= 0;
    }

}
