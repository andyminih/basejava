package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistsStorageException;
import com.urise.webapp.exception.NotExistsStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(String uuid);

    private Object GetExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExisting(searchKey)) throw new NotExistsStorageException(uuid);
        return searchKey;
    }

    private Object GetNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExisting(searchKey)) throw new ExistsStorageException(uuid);
        return searchKey;
    }

    public final Resume get(String uuid) {
        Object searchKey = GetExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public final void update(Resume resume) {
        Object searchKey = GetExistingSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
    }

    public final void delete(String uuid) {
        Object searchKey = GetExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    public final void save(Resume resume) {
        Object searchKey = GetNotExistingSearchKey(resume.getUuid());
        doSave(searchKey, resume);
    }

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract void doDelete(Object searchKey);

    protected abstract void doSave(Object searchKey, Resume resume);

    protected abstract boolean isExisting(Object searchKey);
}
