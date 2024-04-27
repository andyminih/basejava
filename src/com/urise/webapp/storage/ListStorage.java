package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    public final void clear() {
        storage.clear();
    }

    protected final Object getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    protected final Resume doGet(Object searchKey) {
        return storage.get((int) searchKey);
    }

    protected final void doUpdate(Object searchKey, Resume resume) {
        storage.set((int) searchKey, resume);
    }

    public final List<Resume> getAllSorted() {
        if (storage.isEmpty()) {
            return new ArrayList<Resume>();
        }
        final Resume[] resumeArray = new Resume[storage.size()];
        storage.toArray(resumeArray);
        final List<Resume> resumeList = Arrays.asList(resumeArray);
        resumeList.sort(Resume.Comparator());
        return resumeList;
    }

    public final int size() {
        return storage.size();
    }

    protected final void doDelete(Object searchKey) {
        storage.remove((int) searchKey);
    }

    protected final void doSave(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    protected final boolean isExisting(Object searchKey) {
        return searchKey != null;
    }
}
