package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapUuidStorage extends AbstractMapStorage {

    protected Object getSearchKey(String uuid) {
        return storage.get(uuid) != null ? uuid : null;
    }

    protected final Resume doGet(Object searchKey) {
        return (Resume) storage.get((String) searchKey);
    }

    protected final void doUpdate(Object searchKey, Resume resume) {
        storage.put(((String) searchKey), resume);
    }

    protected final void doDelete(Object searchKey) {
        storage.remove(((String) searchKey));
    }
}
