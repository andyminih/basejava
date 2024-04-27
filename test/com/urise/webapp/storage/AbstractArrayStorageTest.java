package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistsStorageException;
import com.urise.webapp.exception.NotExistsStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;

public abstract class AbstractArrayStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_NOT_EXISTS = "uuid_not_exists";

    private static final String FULLNAME_1 = "FullName1";
    private static final String FULLNAME_2 = "FullName2";
    private static final String FULLNAME_3 = "FullName3";
    private static final String FULLNAME_4 = "FullName4";
    private static final String FULLNAME_NOT_EXISTS = "NotExists";

    private static final Resume resume1 = new Resume(UUID_1, FULLNAME_1);
    private static final Resume resume2 = new Resume(UUID_2, FULLNAME_2);
    private static final Resume resume3 = new Resume(UUID_3, FULLNAME_3);
    private static final Resume resume4 = new Resume(UUID_4, FULLNAME_4);
    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);

        Assertions.assertArrayEquals(new Resume[0], storage.getAllSorted().toArray(new Resume[0]));
    }

    @Test
    public void get() {
        assertGet(resume1);
        assertGet(resume2);
        assertGet(resume3);
    }

    @Test
    public void update() {
        storage.update(resume1);
        Assertions.assertSame(resume1, storage.get(resume1.getUuid()));
    }

    @Test
    public void getAllSorted() {
        final Resume[] expected = {resume1, resume2, resume3};
        Assertions.assertArrayEquals(expected, storage.getAllSorted().toArray(new Resume[0]));
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        Assertions.assertThrows(NotExistsStorageException.class, () -> storage.get(UUID_2));
    }

    @Test
    public void save() {
        storage.save(resume4);
        assertGet(resume4);
        assertSize(4);
    }

    @Test
    public void saveOverflow() throws NoSuchFieldException {
        if (storageIsArray()) {
            storage.clear();
            for (int i = 0; i < 5; i++) {
                storage.save(new Resume("uuid_" + i));
            }
            Assertions.assertThrows(StorageException.class, () -> storage.save(new Resume(UUID_NOT_EXISTS)));
        }
    }

    private boolean storageIsArray() throws NoSuchFieldException {
        final Field[] fields = storage.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("storage")) {
                return field.getType().isArray();
            }
        }
        return false;
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
        final Resume resume = new Resume(UUID_NOT_EXISTS, FULLNAME_NOT_EXISTS);
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