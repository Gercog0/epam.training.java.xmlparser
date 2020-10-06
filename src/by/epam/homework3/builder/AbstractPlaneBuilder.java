package by.epam.homework3.builder;

import by.epam.homework3.entity.Plane;
import by.epam.homework3.exception.ProjectException;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractPlaneBuilder {
    protected Set<Plane> planes;

    public AbstractPlaneBuilder() {
        this.planes = new HashSet<>();
    }

    public AbstractPlaneBuilder(Set<Plane> planes) {
        this.planes = planes;
    }

    public Set<Plane> getPlanes() {
        return planes;
    }

    public void setPlanes(Set<Plane> planes) {
        this.planes = planes;
    }

    public abstract void buildPlanes(String fileLink) throws ProjectException;
}
