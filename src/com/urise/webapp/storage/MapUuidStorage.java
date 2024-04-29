package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapUuidStorage extends AbstractMapStorage {

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.get(uuid) != null ? uuid : null;
    }

    @Override
    protected final Resume doGet(Object searchKey) {
        return (Resume) storage.get((String) searchKey);
    }

    @Override
    protected final void doUpdate(Object searchKey, Resume resume) {
        storage.put(((String) searchKey), resume);
    }

    @Override
    protected final void doDelete(Object searchKey) {
        storage.remove(((String) searchKey));
    }
}
