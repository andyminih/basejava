package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

    public final void clear() {
        storage.clear();
    }

    public final int size() {
        return storage.size();
    }

    @Override
    protected final Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected final Resume doGet(Integer searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected final void doUpdate(Integer searchKey, Resume resume) {
        storage.set((int) searchKey, resume);
    }

    @Override
    protected final List<Resume> doGetAll() {
        if (storage.isEmpty()) {
            return new ArrayList<Resume>();
        }
        final Resume[] resumeArray = new Resume[storage.size()];
        storage.toArray(resumeArray);
        return Arrays.asList(resumeArray);
    }

    @Override
    protected final void doDelete(Integer searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected final void doSave(Integer searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected final boolean isExisting(Integer searchKey) {
        return searchKey != null;
    }
}
