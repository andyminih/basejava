package com.urise.webapp.storage.serialization;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerialization implements SerializationStrategy {
    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            final Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            final int resumeCount = dis.readInt();
            for (int i = 0; i < resumeCount; i++) {
                resume.putContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            final int sectionCount = dis.readInt();
            for (int i = 0; i < sectionCount; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL:
                        resume.putSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENTS, QUALIFICATIONS:
                        final List<String> stringList = new ArrayList<String>();
                        int listCount = dis.readInt();
                        for (int l = 0; l < listCount; l++) {
                            stringList.add(dis.readUTF());
                        }
                        resume.putSection(sectionType, new ListSection(stringList));
                        break;
                    case EDUCATION, EXPERIENCE:
                        final List<Company> companyList = new ArrayList<Company>();
                        int companyCount = dis.readInt();
                        for (int c = 0; c < companyCount; c++) {
                            String name = dis.readUTF();
                            String website = (dis.readInt() == 0) ? null : dis.readUTF();
                            final List<Company.Period> periodList = new ArrayList<Company.Period>();
                            int periodCount = dis.readInt();
                            for (int p = 0; p < periodCount; p++) {
                                periodList.add(new Company.Period(dis.readUTF(), (dis.readInt() == 0) ? null : dis.readUTF(), LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF())));
                            }
                            companyList.add(new Company(name, website, periodList));
                        }
                        resume.putSection(sectionType, new CompanySection(companyList));
                        break;
                    default:
                        break;
                }
            }
            return resume;
        }
    }

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            writeWithException(contacts.entrySet(), (FunctionWriter<Map.Entry<ContactType, String>>) (entry) -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            writeWithException(sections.entrySet(), (FunctionWriter<Map.Entry<SectionType, Section>>) (entry) -> {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case OBJECTIVE, PERSONAL:
                        dos.writeUTF(((TextSection) entry.getValue()).getText());
                        break;
                    case ACHIEVEMENTS, QUALIFICATIONS:
                        List<String> list = ((ListSection) entry.getValue()).getList();
                        dos.writeInt(list.size());
                        writeWithException(list, (FunctionWriter<String>) dos::writeUTF);
                        break;
                    case EDUCATION, EXPERIENCE:
                        List<Company> companies = ((CompanySection) entry.getValue()).getList();
                        dos.writeInt(companies.size());
                        writeWithException(companies, (FunctionWriter<Company>) (company) -> {
                            dos.writeUTF(company.getName());
                            if (company.getWebsite() == null) {
                                dos.writeInt(0);
                            } else {
                                dos.writeInt(1);
                                dos.writeUTF(company.getWebsite());
                            }

                            List<Company.Period> periods = (company.getList());
                            dos.writeInt(periods.size());
                            writeWithException(periods, (FunctionWriter<Company.Period>) (period) -> {
                                dos.writeUTF(period.getTitle());
                                if (period.getDescription() == null) {
                                    dos.writeInt(0);
                                } else {
                                    dos.writeInt(1);
                                    dos.writeUTF(period.getDescription());
                                }
                                dos.writeUTF(period.getStart().toString());
                                dos.writeUTF(period.getEnd().toString());
                            });
                        });
                        break;
                    default:
                        break;
                }
            });
        }
    }

    private <T> void writeWithException(Collection<T> collection, FunctionWriter action) throws IOException {
        Objects.requireNonNull(action);
        for (T t : collection) {
            action.accept(t);
        }
    }
}
