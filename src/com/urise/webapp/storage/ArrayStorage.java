package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage {

    private Resume[] storage = new Resume[10000];
    //количество резюме в массиве
    private int count = 0;

    public void clear() {
//        for (int i = 0; i < count; i++) {
//            storage[i] = null;
//        }
        Arrays.fill(storage, 0, count - 1, null);
        count = 0;
    }

    public void save(Resume r) {
        if (count < 10000) {
            storage[count] = r;
            count++;
        } else {
            System.out.println("Невозможно добавлить резюме. Хранилище переполнено.");
        }
    }

    public Resume get(String uuid) {
//     /*   for (int i = 0; i < count; i++) {
//            if (storage[i].getUuid().equals(uuid)) {
//                return storage[i];
//            }
//        }
//        return null;*/
        Resume r = find(uuid);
        if (r == null) {
            System.out.println("Резюме не найдено.");
        }
        return r;
    }

    public void delete(String uuid) {
        for (int i = 0; i < count; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                System.arraycopy(storage, i + 1, storage, i, count - i - 1);
                storage[count - 1] = null;
                count--;
                return;
            }
        }
    }

    public void update(Resume resume) {
        /*for (int i=0; i<count; i++) {
            if (storage[i].getUuid().equals((resume.getUuid()))) {
                storage[i] = resume;
                return;
            }
        }*/
        Resume r = find(resume.getUuid());
        if (r == null) {
            System.out.println("Резюме не найдено.");
        } else {
            r = resume;
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

    private Resume find(String uuid) {
        for (int i = 0; i < count; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

}
