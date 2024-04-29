package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {

    @Override
    protected final Object getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected final Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected final void doUpdate(Object searchKey, Resume resume) {
        storage.put(((Resume) searchKey).getUuid(), resume);
    }

    @Override
    protected final void doDelete(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }
}
