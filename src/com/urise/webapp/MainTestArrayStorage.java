package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.MapResumeStorage;

import java.util.List;


/**
 * Test for your com.uraise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private final static MapResumeStorage ARRAY_STORAGE = new MapResumeStorage();

    public static void main(String[] args) throws NoSuchFieldException {
        final Resume r1 = new Resume("uuid1", "АбрамовА");
        final Resume r2 = new Resume("uuid2", "МишленБ");
        final Resume r3 = new Resume("uuid3", "АбрамовА");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        final Resume r4 = new Resume("uuid0", "КлимовР");
        //ARRAY_STORAGE.update(r4);
        ARRAY_STORAGE.save(r4);
        ARRAY_STORAGE.update(r4);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

//        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());


        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);


        List<Resume> resumeList = ARRAY_STORAGE.getAllSorted();
        System.out.println(resumeList);

    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
