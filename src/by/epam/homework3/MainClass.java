package by.epam.homework3;

import by.epam.homework3.builder.impl.dom.PlaneDOMBuilder;
import by.epam.homework3.builder.impl.sax.PlaneSAXBuilder;
import by.epam.homework3.exception.ProjectException;

public class MainClass {
    public static void main(String[] args) throws ProjectException {
        PlaneSAXBuilder builder = new PlaneSAXBuilder();
        builder.buildPlanes("data/plane.xml");
        System.out.println(builder.getPlanes().toString());
    }
}
