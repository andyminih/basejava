package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private final String title;
    private final String description;
    private final LocalDate start;
    private final LocalDate end;

    public Period(String title, String description, LocalDate start, LocalDate end) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(end, "end must not be null");
        Objects.requireNonNull(start, "start must not be null");
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
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
