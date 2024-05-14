package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Company {
    private final String name;
    private final String website;

    private final List<Period> list;

    public Company(String name, String website, List<Period> list) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(list, "list must not be null");
        this.name = name;
        this.website = website;
        this.list = list;
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
}
