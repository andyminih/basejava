package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private final static long serialVersionUID = 1L;
    private final List<String> list;

    public ListSection(List<String> list) {
        Objects.requireNonNull(list, "list must not be null");
        this.list = list;
    }

    public ListSection(String ... strings) {
        this(Arrays.asList(strings));
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : list) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append("\n\t");
            }
            stringBuilder.append("* ");
            stringBuilder.append(string);
        }

        return stringBuilder.toString();
    }
}
