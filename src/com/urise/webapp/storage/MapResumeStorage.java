package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {

    protected final Object getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    protected final Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    protected final void doUpdate(Object searchKey, Resume resume) {
        storage.put(((Resume) searchKey).getUuid(), resume);
    }

    protected final void doDelete(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }
}
