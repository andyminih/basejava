package com.urise.webapp.model;

import java.util.Comparator;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    private static final String NO_NAME = "No_name";
    private static final Comparator<Resume> COMPARATOR = (o1, o2) -> {
        if (o1 == null) return 1;
        if (o2 == null) return -1;
        int result = o1.fullName.compareTo(o2.fullName);
        if (result == 0) {
            result = o1.uuid.compareTo(o2.uuid);
        }
        return result;
    };
    private final String uuid;
    private final String fullName;

    public Resume() {
        this.fullName = NO_NAME;
        this.uuid = UUID.randomUUID().toString();
    }

    public Resume(String uuid) {
        this.fullName = NO_NAME;
        this.uuid = uuid;
    }

    public Resume(String uuid, String fullName) {
        this.fullName = fullName;
        this.uuid = uuid;
    }

    public static Comparator<Resume> Comparator() {
        return COMPARATOR;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return "(" + fullName + " : " + uuid + ")";
    }

    @Override
    public int compareTo(Resume o) {
        return this.uuid.compareTo(o.uuid);
    }
}
