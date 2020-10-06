package by.epam.homework3.entity;

import java.util.Arrays;
import java.util.Optional;

public class Chars {
    public enum Type {
        PASSENGER("passenger"),
        CARGO("cargo");

        private String type;

        Type(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public static Optional<Type> getTypeByValue(String value) {
            return Arrays.stream(Type.values()).
                    filter(o -> o.getType().equals(value)).findAny();
        }
    }

    private Type type;
    private int places;
    private boolean radar;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public boolean isRadar() {
        return radar;
    }

    public void setRadar(boolean radar) {
        this.radar = radar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Chars chars = (Chars) o;

        if (places != chars.places) {
            return false;
        }
        if (radar != chars.radar) {
            return false;
        }
        return type == chars.type;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + places;
        result = 31 * result + (radar ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Chars{");
        sb.append("type=").append(type);
        sb.append(", places=").append(places);
        sb.append(", radar=").append(radar);
        sb.append('}');
        return sb.toString();
    }
}
