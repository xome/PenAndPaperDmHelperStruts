package de.mayer.penandpaperdmhelper.presentAdventure.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Objects;

public class Chapter {

    private final String name;
    private final Double approximateDurationInMinutes;
    private final List<RecordInChapter> records;

    public Chapter(String name, Double approximateDurationInMinutes, List<RecordInChapter> records) {
        this.name = name;
        this.approximateDurationInMinutes = approximateDurationInMinutes;
        this.records = records;
    }

    public String getName() {
        return name;
    }

    public Double getApproximateDurationInMinutes() {
        return approximateDurationInMinutes;
    }

    public List<RecordInChapter> getRecords() {
        return records;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chapter chapter = (Chapter) o;
        return Objects.equals(name, chapter.name) && Objects.equals(approximateDurationInMinutes, chapter.approximateDurationInMinutes) && Objects.equals(records, chapter.records);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, approximateDurationInMinutes, records);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("approximateDurationInMinutes", approximateDurationInMinutes)
                .append("records", records)
                .toString();
    }
}
