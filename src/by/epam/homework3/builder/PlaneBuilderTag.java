package by.epam.homework3.builder;

import by.epam.homework3.entity.Plane;

import java.util.Arrays;
import java.util.Optional;

public enum PlaneBuilderTag {
    ORDINARY_PLANE("ordinary-plane"),
    MILITARY_PLANE("military-plane"),
    ID("id"),
    DESCRIPTION("description"),
    MODEL("model"),
    ORIGIN("origin"),
    CHARS("chars"),
    PARAMETERS("parameters"),
    TYPE("type"),
    PLACES("places"),
    RADAR("radar"),
    LENGTH("length"),
    WIDTH("width"),
    WEIGHT("weight"),
    DATE("date"),
    PRICE("price"),
    WEAPON("weapon"),
    INVISIBILITY("invisibility");

    private String tag;

    PlaneBuilderTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public static Optional<PlaneBuilderTag> getTagByValue(String value) {
        return Arrays.stream(PlaneBuilderTag.values()).
                filter(o -> o.getTag().equals(value)).findAny();
    }
}
