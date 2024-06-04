package com.urise.webapp.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume>, Serializable {
    @Serial
    private final static long serialVersionUID = 1L;
    private final String uuid;
    private final String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume() {
        this.fullName = "";
        this.uuid = UUID.randomUUID().toString();
    }

    public Resume(String fullName) {
        this.fullName = fullName;
        this.uuid = UUID.randomUUID().toString();
    }

    public Resume(String uuid, String fullName) {
        this.fullName = fullName;
        this.uuid = uuid;
    }


    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void putContact(ContactType contactType, String contact) {
        contacts.put(contactType, contact);
    }

    public String getContact(ContactType contactType) {
        return contacts.get(contactType);
    }

    public Section getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    public void putSection(SectionType sectionType, Section section) {
        sections.put(sectionType, section);
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