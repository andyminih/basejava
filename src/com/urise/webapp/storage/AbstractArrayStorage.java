package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected final static int STORAGE_LIMIT = 4;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final int size() {
        return size;
    }

    @Override
    protected final Resume doGet(Integer searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected final void doUpdate(Integer searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    protected final List<Resume> doGetAll() {
        final List<Resume> resumeList = Arrays.asList(storage);
        return resumeList.subList(0, size());
    }

    @Override
    protected final void doDelete(Integer searchKey) {
        deleteResume((int) searchKey);
        size--;
        storage[size] = null;
    }

    @Override
    protected final void doSave(Integer searchKey, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException(resume.getUuid(), "Невозможно добавить резюме. Хранилище переполнено.");
        }
        insertResume((int) searchKey, resume);
        size++;
    }

    @Override
    protected final boolean isExisting(Integer searchKey) {
        return (int) searchKey >= 0;
    }

    protected abstract void deleteResume(int index);

    protected abstract void insertResume(int index, Resume r);

}
