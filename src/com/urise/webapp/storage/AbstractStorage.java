package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistsStorageException;
import com.urise.webapp.exception.NotExistsStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public final Resume get(String uuid) {
        Resume resume = getResume(uuid);
        if (resume == null) {
            throw new NotExistsStorageException(uuid);
        }
        return resume;
    }

    public final void update(Resume resume) {
        if (!updateResume(resume)) {
            throw new NotExistsStorageException(resume.getUuid());
        }
    }

    public final void delete(String uuid) {
        if (!deleteResume(uuid)) {
            throw new NotExistsStorageException(uuid);
        }
    }

    public final void save(Resume resume) {
        switch (saveResume(resume)) {
            case ALREADY_EXISTS:
                throw new ExistsStorageException(resume.getUuid());
            case STORAGE_OVERFLOW:
                throw new StorageException(resume.getUuid(), "Невозможно добавить резюме. Хранилище переполнено.");
        }
    }

    protected abstract Resume getResume(String uuid);

    protected abstract boolean updateResume(Resume resume);

    protected abstract boolean deleteResume(String uuid);

    protected abstract SaveResumeResult saveResume(Resume resume);
}
