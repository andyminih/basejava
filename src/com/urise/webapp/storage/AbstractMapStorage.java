package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public abstract class AbstractMapStorage extends AbstractStorage {
    protected final Map storage = new HashMap<String, Resume>();

    public final void clear() {
        storage.clear();
    }

    public final int size() {
        return storage.size();
    }

    @Override
    protected final void doSave(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExisting(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected final List<Resume> doGetAll() {
        if (storage.isEmpty()) {
            return new ArrayList<Resume>();
        }
        Resume[] resumeArray = new Resume[storage.size()];
        storage.values().toArray(resumeArray);
        return Arrays.asList(resumeArray);
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract void doDelete(Object searchKey);

}
