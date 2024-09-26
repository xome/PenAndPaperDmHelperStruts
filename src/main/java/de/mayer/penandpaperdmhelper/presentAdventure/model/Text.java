package de.mayer.penandpaperdmhelper.presentAdventure.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class Text extends RecordInChapter {
    private final String text;

    public Text(int index, String text) {
        super(index);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Text text1 = (Text) o;
        return Objects.equals(text, text1.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("text", text)
                .append("index", getIndex())
                .toString();
    }
}
