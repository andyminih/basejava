package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }

    @Override
    protected void removeResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    protected void insertResume(int index, Resume r) {
        final int insIndex = -index - 1;
        System.arraycopy(storage, insIndex, storage, insIndex + 1, size - insIndex);
        storage[insIndex] = r;
    }

}
