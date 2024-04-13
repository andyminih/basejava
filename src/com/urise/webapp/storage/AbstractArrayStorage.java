package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage implements Storage {

    protected final static int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final Resume get(String uuid) {
        final int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("Резюме " + uuid + " не найдено.");
            return null;
        }
        return storage[index];
    }

    public final void update(Resume resume) {
        final int index = findIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Резюме не найдено.");
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
            remove(index);
            size--;
        }
    }

    public final void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Невозможно добавить резюме. Хранилище переполнено.");
            return;
        }

        int index = findIndex(r.getUuid());
        if (index >= 0) {
            System.out.println("Невозможно добавить резюме. Резюме " + r.getUuid() + " уже существует в хранилище.");
        } else {
            insert(index, r);
            size++;
        }
    }

    protected abstract int findIndex(String uuid);

    protected abstract void remove(int index);

    protected abstract void insert(int index, Resume r);

}
