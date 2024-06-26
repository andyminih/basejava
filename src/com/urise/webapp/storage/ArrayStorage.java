package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected final Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected final void deleteResume(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected final void insertResume(int index, Resume r) {
        storage[size] = r;
    }

}
