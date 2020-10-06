package builder;

import by.epam.homework3.builder.AbstractPlaneBuilder;
import by.epam.homework3.builder.impl.dom.PlaneDOMBuilder;
import by.epam.homework3.entity.Plane;
import by.epam.homework3.exception.ProjectException;
import data.DataTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import java.util.Set;

public class PlaneDomBuilderTest {
    @Test
    public void buildPlanesTest() throws ProjectException {
        AbstractPlaneBuilder builder = new PlaneDOMBuilder();
        Set<Plane> expected = DataTest.getPlanes();
        builder.buildPlanes("data/planesTest.xml");
        Set<Plane> actual = builder.getPlanes();
        assertEquals(actual, expected);
    }
}
