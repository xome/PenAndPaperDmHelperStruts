package de.mayer.penandpaperdmhelper.adventures.model;

import java.util.Objects;

public abstract class RecordInChapter {

    private final int index;

    public RecordInChapter(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordInChapter that = (RecordInChapter) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(index);
    }
}
