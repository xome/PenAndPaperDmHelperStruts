package de.mayer.penandpaperdmhelper.adventures.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Objects;

public class BackgroundMusic extends RecordInChapter{

    private final String name;
    private final byte[] music;

    public BackgroundMusic(int index, String name, byte[] music) {
        super(index);
        this.name = name;
        this.music = music;
    }

    public byte[] getMusic() {
        return music;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("music", music)
                .append("index", getIndex())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BackgroundMusic that = (BackgroundMusic) o;
        return Objects.equals(name, that.name) && Objects.deepEquals(music, that.music);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, Arrays.hashCode(music));
    }

    public String getName() {
        return name;
    }
}
