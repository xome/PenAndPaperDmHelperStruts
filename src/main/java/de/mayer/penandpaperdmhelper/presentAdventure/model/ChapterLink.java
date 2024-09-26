package de.mayer.penandpaperdmhelper.presentAdventure.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class ChapterLink extends RecordInChapter{

    private final Chapter chapterTo;

    public ChapterLink(int index, Chapter chapterTo) {
        super(index);
        this.chapterTo = chapterTo;
    }


    public Chapter getChapterTo() {
        return chapterTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ChapterLink that = (ChapterLink) o;
        return Objects.equals(chapterTo, that.chapterTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), chapterTo);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("chapterTo", chapterTo)
                .append("index", getIndex())
                .toString();
    }
}
