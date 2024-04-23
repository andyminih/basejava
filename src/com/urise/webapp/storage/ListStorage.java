package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    public final void clear() {
        storage.clear();
    }

    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    protected final Resume doGet(Object searchKey) {
        return storage.get((int) searchKey);
    }

    protected final void doUpdate(Object searchKey, Resume resume) {
        storage.set((int) searchKey, resume);
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
//        storage.remove((Resume) searchKey);
        storage.remove((int) searchKey);
    }

    protected final void doSave(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    protected boolean isExisting(Object searchKey) {
        return searchKey != null;
    }
}
