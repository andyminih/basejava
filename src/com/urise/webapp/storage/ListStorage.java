package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collection;

public class ListStorage extends AbstractStorage {
    private final Collection<Resume> storage = new ArrayList<>();

    public final void clear() {
        storage.clear();
    }

    protected Object getSearchKey(String uuid) {
        for (Resume r : storage) {
            if (r.getUuid().equals(uuid)) {
                return r;
            }
        }
        return null;
    }

    protected final Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    protected final void doUpdate(Object searchKey, Resume resume) {
        storage.remove((Resume) searchKey);
        storage.add((resume));
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

    protected final void doDelete(Object searchKey) {
        storage.remove((Resume) searchKey);
    }

    protected final void doSave(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    protected boolean isExisting(Object searchKey) {
        return searchKey != null;
    }
}
