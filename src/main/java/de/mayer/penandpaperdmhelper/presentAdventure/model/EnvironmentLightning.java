package de.mayer.penandpaperdmhelper.presentAdventure.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Objects;

public class EnvironmentLightning extends RecordInChapter{
    private final int[] rgb;
    private final float brightness;

    public EnvironmentLightning(int index, int[] rgb, float brightness) {
        super(index);

        if (rgb == null) {throw new RuntimeException(new IllegalAccessException("RGB cannot be null!"));}
        if (rgb.length != 3) {throw new RuntimeException(new IllegalAccessException("RGB length must be 3!"));}
        if (Arrays.stream(rgb).anyMatch((i) -> i < 0 || i > 255)) {throw new RuntimeException(new IllegalAccessException("Each RGB value must be between 0 and 255!"));}
        this.rgb = rgb;

        if (brightness > 1.0 || brightness < 0.0) {throw new RuntimeException(new IllegalAccessException("Brightness must be between 0 and 1.0!"));}
        this.brightness = brightness;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("rgb", rgb)
                .append("brightness", brightness)
                .append("index", getIndex())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EnvironmentLightning that = (EnvironmentLightning) o;
        return Float.compare(brightness, that.brightness) == 0 && Objects.deepEquals(rgb, that.rgb);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Arrays.hashCode(rgb), brightness);
    }

    public int[] getRgb() {
        return rgb;
    }

    public float getBrightness() {
        return brightness;
    }
}
