package com.urise.webapp.model;

import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    private String name;
    private String website;

    private List<Period> list;

    public Company() {
    }

    public Company(String name, String website, List<Period> list) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(list, "list must not be null");
        this.name = name;
        this.website = website;
        this.list = list;

    }

    public Company(String name, String website, Period... periods) {
        this(name, website, Arrays.asList(periods));
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!name.equals(company.name)) return false;
        if (!Objects.equals(website, company.website)) return false;
        return list.equals(company.list);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + list.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(name);
        for (Period period : list) {
            stringBuilder.append("\n\t\t");
            stringBuilder.append(period.getStart());
            stringBuilder.append(" - ");
            stringBuilder.append(period.getEnd());
            stringBuilder.append("\t\t");
            stringBuilder.append(period.getTitle());
            if (period.getDescription() != null && !period.getDescription().isEmpty()) {
                stringBuilder.append("\n\t\t\t\t\t\t\t\t\t");
                stringBuilder.append(period.getDescription());
            }
        }
        return stringBuilder.toString();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable {
        private final static long serialVersionUID = 1L;
        private String title;
        private String description;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate start;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate end;

        public Period() {
        }

        public Period(String title, String description, LocalDate start, LocalDate end) {
            Objects.requireNonNull(title, "title must not be null");
            Objects.requireNonNull(end, "end must not be null");
            Objects.requireNonNull(start, "start must not be null");
            this.title = title;
            this.description = description;
            this.start = start;
            this.end = end;
        }

        public Period(String title, String description, LocalDate start) {
            this(title, description, start, NOW);
        }

        public String getTitle() {
            return title;
        }


        public String getDescription() {
            return description;
        }

        public LocalDate getStart() {
            return start;
        }

        public LocalDate getEnd() {
            return end;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Period period = (Period) o;

            if (!title.equals(period.title)) return false;
            if (!Objects.equals(description, period.description)) return false;
            if (!start.equals(period.start)) return false;
            return end.equals(period.end);
        }

        @Override
        public int hashCode() {
            int result = title.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            result = 31 * result + start.hashCode();
            result = 31 * result + end.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "Period{" +
                    "title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", start=" + start +
                    ", end=" + end +
                    '}';
        }
    }
}
