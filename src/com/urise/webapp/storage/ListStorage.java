package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ListStorage extends AbstractStorage {
    private final Collection<Resume> storage = new ArrayList<>();

    public final void clear() {
        storage.clear();
    }

    protected final Resume getResume(String uuid) {
        for (Resume r : storage) {
            if (r.getUuid().equals(uuid)) {
                return r;
            }
        }
        return null;
    }

    protected final boolean updateResume(Resume resume) {
        Iterator<Resume> iterator = storage.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (r.getUuid().equals(resume.getUuid())) {
                iterator.remove();
                storage.add(resume);
                return true;
            }
        }
        return false;
    }

    public final Resume[] getAll() {
        Resume[] resume = new Resume[storage.size()];
        if (!storage.isEmpty()) {
            storage.toArray(resume);
        }
        return resume;
    }

    public final int size() {
        return storage.size();
    }

    protected final boolean deleteResume(String uuid) {
        return storage.remove(new Resume(uuid));
    }

    protected final SaveResumeResult saveResume(Resume resume) {
        if (storage.contains(resume)) {
            return SaveResumeResult.ALREADY_EXISTS;
        }
        storage.add(resume);
        return SaveResumeResult.OK;
    }
}
