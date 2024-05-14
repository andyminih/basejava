package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {
    private final List<Company> list;

    public CompanySection(List<Company> list) {
        Objects.requireNonNull(list, "list must not be null");
        this.list = list;
    }

    public List<Company> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySection that = (CompanySection) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Company company : list) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append("\n\n\t");
            }
            stringBuilder.append(company.toString());
        }

        return stringBuilder.toString();
    }
}
