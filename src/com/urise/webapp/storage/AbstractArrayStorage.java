package com.urise.webapp.storage;

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

    protected final Resume getResume(String uuid) {
        final int index = findIndex(uuid);
        if (index < 0) {
            return null;
        }
        return storage[index];
    }

    protected final boolean updateResume(Resume resume) {
        final int index = findIndex(resume.getUuid());
        if (index < 0) {
            return false;
        } else {
            storage[index] = resume;
            return true;
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

    protected final boolean deleteResume(String uuid) {
        final int index = findIndex(uuid);
        if (index < 0) {
            return false;
        } else {
            removeResume(index);
            size--;
            storage[size] = null;
            return true;
        }
    }

    protected final SaveResumeResult saveResume(Resume resume) {
        if (size == STORAGE_LIMIT) {
            return SaveResumeResult.STORAGE_OVERFLOW;
        }

        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            return SaveResumeResult.ALREADY_EXISTS;
        } else {
            insertResume(index, resume);
            size++;
            return SaveResumeResult.OK;
        }
    }

    protected abstract int findIndex(String uuid);

    protected abstract void removeResume(int index);

    protected abstract void insertResume(int index, Resume r);

}
