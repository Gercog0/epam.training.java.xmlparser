package builder;

import by.epam.homework3.builder.AbstractPlaneBuilder;
import by.epam.homework3.builder.impl.stax.PlaneStAXBuilder;
import by.epam.homework3.entity.Plane;
import by.epam.homework3.exception.ProjectException;
import data.DataTest;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertEquals;

public class PlaneStAXBuilderTest {
    @Test
    public void buildPlanesTest() throws ProjectException {
        AbstractPlaneBuilder builder = new PlaneStAXBuilder();
        Set<Plane> expected = DataTest.getPlanes();
        builder.buildPlanes("data/planesTest.xml");
        Set<Plane> actual = builder.getPlanes();
        assertEquals(actual, expected);
    }
}
