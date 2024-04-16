package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistsStorageException;
import com.urise.webapp.exception.NotExistsStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

class AbstractArrayStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);
    private static final Resume resume4 = new Resume(UUID_4);
    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @org.junit.jupiter.api.Test
    void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @org.junit.jupiter.api.Test
    void get() {
        Assertions.assertEquals(resume1, storage.get(UUID_1));
        Exception exception = Assertions.assertThrows(NotExistsStorageException.class, () -> storage.get(UUID_4));
    }

    @org.junit.jupiter.api.Test
    void update() {
        storage.update(resume1);
        Assertions.assertEquals(resume1, storage.get(UUID_1));
        Exception exception = Assertions.assertThrows(NotExistsStorageException.class, () -> storage.update(resume4));
    }

    @org.junit.jupiter.api.Test
    void getAll() {
        Resume[] r = storage.getAll();
        Assertions.assertEquals(r.length, storage.size());
        Assertions.assertEquals(r[0], storage.get(UUID_1));
        Assertions.assertEquals(r[1], storage.get(UUID_2));
        Assertions.assertEquals(r[2], storage.get(UUID_3));
    }

    @org.junit.jupiter.api.Test
    void size() {
        Assertions.assertEquals(3, storage.size());
    }

    @org.junit.jupiter.api.Test
    void delete() {
        storage.delete(UUID_2);
        Assertions.assertEquals(2, storage.size());
        Assertions.assertEquals(resume1, storage.get(UUID_1));
        Assertions.assertEquals(resume3, storage.get(UUID_3));
    }

    @org.junit.jupiter.api.Test
    void save() {
        Exception exception = Assertions.assertThrows(StorageException.class, () -> storage.save(resume4));

        storage.delete(UUID_3);
        exception = Assertions.assertThrows(ExistsStorageException.class, () -> storage.save(resume1));

        storage.save(resume3);
        Assertions.assertEquals(3, storage.size());
        Assertions.assertEquals(resume1, storage.get(UUID_1));
    }

}