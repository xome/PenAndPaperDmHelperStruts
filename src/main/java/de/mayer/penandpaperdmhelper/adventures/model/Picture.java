package de.mayer.penandpaperdmhelper.adventures.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class Picture extends RecordInChapter{
    private final String base64;
    private final String fileFormat;
    private final boolean isShareableWithGroup;

    public Picture(int index, String base64, String fileFormat, boolean isShareableWithGroup) {
        super(index);
        this.base64 = base64;
        this.fileFormat = fileFormat;
        this.isShareableWithGroup = isShareableWithGroup;
    }

    public String getBase64() {
        return base64;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("base64", base64.substring(0, Math.min(50, base64.length())))
                .append("fileFormat", fileFormat)
                .append("isShareableWithGroup", isShareableWithGroup)
                .append("index", getIndex())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Picture picture = (Picture) o;
        return isShareableWithGroup == picture.isShareableWithGroup && Objects.equals(base64, picture.base64) && Objects.equals(fileFormat, picture.fileFormat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), base64, fileFormat, isShareableWithGroup);
    }

    public boolean isShareableWithGroup() {
        return isShareableWithGroup;
    }
}
