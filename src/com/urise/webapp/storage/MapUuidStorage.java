package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapUuidStorage extends AbstractMapStorage<String> {

    @Override
    protected String getSearchKey(String uuid) {
        return storage.get(uuid) != null ? uuid : null;
    }

    @Override
    protected final Resume doGet(String searchKey) {
        return (Resume) storage.get((String) searchKey);
    }

    @Override
    protected final void doUpdate(String searchKey, Resume resume) {
        storage.put(((String) searchKey), resume);
    }

    @Override
    protected final void doDelete(String searchKey) {
        storage.remove(((String) searchKey));
    }
}
