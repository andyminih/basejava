package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage {

    private final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Невозможно добавлить резюме. Хранилище переполнено.");
        }
        else if (findIndex(r.getUuid())>=0) {
            System.out.println("Невозможно добавлить резюме. Резюме " + r.getUuid() + " уже существует в хранилище.");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index<0) {
            System.out.println("Резюме не найдено.");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index>=0) {
            size--;
            storage[size] = null;
        }
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index<0) {
            System.out.println("Резюме не найдено.");
        } else {
            storage[index] = resume;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

}
