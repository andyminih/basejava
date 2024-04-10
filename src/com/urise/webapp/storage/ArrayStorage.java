package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage {

    private final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int count = 0;

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    public void save(Resume r) {
        if (count == STORAGE_LIMIT) {
            System.out.println("Невозможно добавлить резюме. Хранилище переполнено.");
        }
        else if (findIndex(r.getUuid())>=0) {
            System.out.println("Невозможно добавлить резюме. Резюме " + r.getUuid() + " уже существует в хранилище.");
        } else {
            storage[count] = r;
            count++;
        }
    }

    public Resume get(String uuid) {
        int i = findIndex(uuid);
        if (i<0) {
            System.out.println("Резюме не найдено.");
        }
        return storage[i];
    }

    public void delete(String uuid) {
        int i = findIndex(uuid);
        if (i>=0) {
            if (i<(count-1)) {
                storage[i] = storage[count-1];
            }
            storage[count-1] = null;
            count--;
        }
    }

    public void update(Resume resume) {
        int i = findIndex(resume.getUuid());
        if (i<0) {
            System.out.println("Резюме не найдено.");
        } else {
            storage[i] = resume;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    public int size() {
        return count;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < count; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

}
