package de.mayer.penandpaperdmhelper.adventures.model;

import java.util.Objects;

public class Adventure {

    private final String name;

    public Adventure(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Adventure{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adventure adventure = (Adventure) o;
        return Objects.equals(name, adventure.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public String getName() {
        return name;
    }

}
