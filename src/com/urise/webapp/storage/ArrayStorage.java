package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Невозможно добавить резюме. Хранилище переполнено.");
        } else if (findIndex(r.getUuid()) >= 0) {
            System.out.println("Невозможно добавить резюме. Резюме " + r.getUuid() + " уже существует в хранилище.");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void delete(String uuid) {
        final int index = findIndex(uuid);
        if (index >= 0) {
            size--;
            storage[index] = storage[size];
            storage[size] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

}
