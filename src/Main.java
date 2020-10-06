import by.epam.homework3.builder.impl.dom.PlaneDOMBuilder;
import by.epam.homework3.exception.ProjectException;

public class Main {
    public static void main(String[] args) throws ProjectException {
        PlaneDOMBuilder builder = new PlaneDOMBuilder();
        builder.buildPlanes("data/planesTest.xml");
        System.out.println(builder.getPlanes());
    }
}
