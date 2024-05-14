package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    final Resume resume = new Resume("Григорий Кислин");

    public ResumeTestData() {
        resume.putContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.putContact(ContactType.SKYPE, "skype:grigory.kislin");

        resume.putSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.putSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        final List<String> stringList = new ArrayList<String>();
        stringList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        stringList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        stringList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resume.putSection(SectionType.ACHIEVEMENTS, new ListSection(new ArrayList<>(stringList)));

        stringList.clear();
        stringList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        stringList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        stringList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        stringList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        resume.putSection(SectionType.QUALIFICATIONS, new ListSection(new ArrayList<>(stringList)));

        final List<Company> companyList = new ArrayList<Company>();
        final List<Period> periodList = new ArrayList<Period>();
        periodList.add(new Period("Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок.", LocalDate.of(2013, 10, 1), LocalDate.of(2013, 10, 1)));
        companyList.add(new Company("Java Online Projects", "https://javaops.ru/", new ArrayList<>(periodList)));
        periodList.clear();
        periodList.add(new Period("Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1)));
        companyList.add(new Company("Wrike", "https://www.wrike.com", new ArrayList<>(periodList)));
        resume.putSection(SectionType.EXPERIENCE, new CompanySection(new ArrayList<>(companyList)));


        companyList.clear();
        periodList.clear();
        periodList.add(new Period("6 месяцев обучения цифровым телефонным сетям (Москва)", "", LocalDate.of(1997, 9, 1), LocalDate.of(1998, 3, 1)));
        companyList.add(new Company("Alcatel", "http://www.alcatel.ru/", new ArrayList<>(periodList)));

        periodList.clear();
        periodList.add(new Period("Аспирантура (программист С, С++)", "", LocalDate.of(1993, 9, 1), LocalDate.of(1996, 7, 1)));
        periodList.add(new Period("Инженер (программист Fortran, C)", "", LocalDate.of(1987, 9, 1), LocalDate.of(1993, 7, 1)));
        companyList.add(new Company("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/", new ArrayList<>(periodList)));
        resume.putSection(SectionType.EDUCATION, new CompanySection(new ArrayList<>(companyList)));
    }

    public static void main(String[] args) {

        final ResumeTestData test = new ResumeTestData();

        System.out.println(test.toString());
    }

    private String getContacts(Resume resume) {
        String contact;
        final StringBuilder stringBuilder = new StringBuilder();
        for (ContactType contactType : ContactType.values()) {
            contact = resume.getContact(contactType);
            if (contact != null) {
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append("\n");
                }
                stringBuilder.append(contactType.getTitle());
                stringBuilder.append(": ");
                stringBuilder.append(contact);
            }
        }
        return stringBuilder.toString();
    }

    private String getSections(Resume resume) {
        Section section;
        final StringBuilder stringBuilder = new StringBuilder();
        for (SectionType sectionType : SectionType.values()) {
            section = resume.getSection(sectionType);
            if (section != null) {
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append("\n");
                }
                stringBuilder.append(sectionType.getTitle());
                stringBuilder.append("\n\t");
                stringBuilder.append(section.toString());
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(resume.getFullName());
        stringBuilder.append("\n\n");
        stringBuilder.append(getContacts(resume));
        stringBuilder.append("\n\n");
        stringBuilder.append(getSections(resume));

        return stringBuilder.toString();
    }
}
