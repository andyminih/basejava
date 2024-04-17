package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistsStorageException;
import com.urise.webapp.exception.NotExistsStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbstractArrayStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_NOT_EXISTS = "uuid_not_exists";
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

    @Test
    void clear() {
        storage.clear();
        assertSize(0);

        Assertions.assertArrayEquals(new Resume[0], storage.getAll());
    }

    @Test
    void get() {
        assertGet(resume1);
        assertGet(resume2);
        assertGet(resume3);
    }

    @Test
    void update() {
        storage.update(resume1);
        Assertions.assertSame(resume1, storage.get(resume1.getUuid()));
    }

    @Test
    void getAll() {
        final Resume resume1 = new Resume(UUID_1);
        final Resume resume2 = new Resume(UUID_2);
        final Resume resume3 = new Resume(UUID_3);
        final Resume[] expected = {resume1, resume2, resume3};
        Assertions.assertArrayEquals(expected, storage.getAll());
    }

    @Test
    void size() {
        assertSize(3);
    }

    @Test
    void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        Assertions.assertThrows(NotExistsStorageException.class, () -> storage.get(UUID_2));
    }

    @Test
    void save() {
        storage.save(resume4);
        assertGet(resume4);
        assertSize(4);
    }

    @Test
    public void saveOverflow() {
        storage.clear();
        for (int i = 0; i < 4; i++) {
            storage.save(new Resume("uuid_" + i));
        }
        Assertions.assertThrows(StorageException.class, () -> storage.save(new Resume(UUID_NOT_EXISTS)));
    }

    @Test
    public void getNotExists() {
        Assertions.assertThrows(NotExistsStorageException.class, () -> storage.get(UUID_NOT_EXISTS));
    }

    @Test
    public void deleteNotExists() {
        Assertions.assertThrows(NotExistsStorageException.class, () -> storage.delete(UUID_NOT_EXISTS));
    }

    @Test
    public void updateNotExists() {
        final Resume resume = new Resume(UUID_NOT_EXISTS);
        Assertions.assertThrows(NotExistsStorageException.class, () -> storage.update(resume));
    }

    @Test
    public void saveExists() {
        final Resume resume = new Resume(UUID_3);
        Assertions.assertThrows(ExistsStorageException.class, () -> storage.save(resume));
    }

    private void assertSize(int i) {
        Assertions.assertEquals(i, storage.size());
    }

    private void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
    }

}