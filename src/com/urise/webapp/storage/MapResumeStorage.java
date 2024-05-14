package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapResumeStorage extends AbstractMapStorage<Resume> {

    @Override
    protected final Resume getSearchKey(String uuid) {
        return (Resume) storage.get(uuid);
    }

    @Override
    protected final Resume doGet(Resume searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected final void doUpdate(Resume searchKey, Resume resume) {
        storage.put(((Resume) searchKey).getUuid(), resume);
    }

    @Override
    protected final void doDelete(Resume searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }
}
