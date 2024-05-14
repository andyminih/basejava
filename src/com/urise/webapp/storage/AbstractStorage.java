package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistsStorageException;
import com.urise.webapp.exception.NotExistsStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    protected static final Comparator<Resume> COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public final List<Resume> getAllSorted() {
        final List<Resume> resumeList = doGetAll();
        resumeList.sort(COMPARATOR);
        return resumeList;
    }

    public final Resume get(String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public final void update(Resume resume) {
        SK searchKey = getExistingSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
    }

    public final void delete(String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    public final void save(Resume resume) {
        SK searchKey = getNotExistingSearchKey(resume.getUuid());
        doSave(searchKey, resume);
    }

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doUpdate(SK searchKey, Resume resume);

    protected abstract void doDelete(SK searchKey);

    protected abstract void doSave(SK searchKey, Resume resume);

    protected abstract boolean isExisting(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract List<Resume> doGetAll();

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExisting(searchKey)) {
            throw new NotExistsStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExisting(searchKey)) {
            throw new ExistsStorageException(uuid);
        }
        return searchKey;
    }

}