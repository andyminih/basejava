package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map storage = new HashMap<String, Resume>();

    public final void clear() {
        storage.clear();
    }

    protected Object getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    protected final Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    protected final void doUpdate(Object searchKey, Resume resume) {
        storage.put(((Resume) searchKey).getUuid(), resume);
    }

    public final Resume[] getAll() {
        Resume[] resume = new Resume[storage.size()];
        if (!storage.isEmpty()) {
            storage.values().toArray(resume);
        }
        return resume;
    }

    public final int size() {
        return storage.size();
    }

    protected final void doDelete(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }

    protected final void doSave(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    protected boolean isExisting(Object searchKey) {
        return searchKey != null;
    }
}
