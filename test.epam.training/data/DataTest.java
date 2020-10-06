package data;

import by.epam.homework3.entity.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class DataTest {
    private static Set<Plane> planes;

    private static final Logger logger = LogManager.getLogger(DataTest.class);

    static {
        planes = new HashSet<>();
    }

    private DataTest() {
    }

    public static Set<Plane> getPlanes() {
        try {
            OrdinaryPlane plane1 = new OrdinaryPlane();
            plane1.setId("ID1");
            plane1.setModel("S18");
            plane1.setOrigin("Germany");
            Chars chars1 = new Chars();
            chars1.setType(Chars.Type.PASSENGER);
            chars1.setPlaces(300);
            chars1.setRadar(true);
            plane1.setChars(chars1);
            Parameters parameters1 = new Parameters();
            parameters1.setLength(new BigDecimal("50.65"));
            parameters1.setWidth(new BigDecimal("10.50"));
            parameters1.setWeight(new BigDecimal("4700"));
            plane1.setParameters(parameters1);
            plane1.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar("2020-05-18"));
            plane1.setPrice(new BigDecimal("2000"));

            MilitaryPlane plane2 = new MilitaryPlane();
            plane2.setId("ID2");
            plane2.setModel("G34");
            plane2.setOrigin("Spain");
            Chars chars2 = new Chars();
            chars2.setType(Chars.Type.CARGO);
            chars2.setPlaces(15);
            chars2.setRadar(true);
            plane2.setChars(chars2);
            Parameters parameters2 = new Parameters();
            parameters2.setLength(new BigDecimal("100"));
            parameters2.setWidth(new BigDecimal("30"));
            parameters2.setWeight(new BigDecimal("10000"));
            plane2.setParameters(parameters2);
            plane2.setWeapon(true);
            plane2.setInvisibility(true);

            planes.add(plane2);
            planes.add(plane1);
        } catch (DatatypeConfigurationException exp) {
            logger.error("Error while datatype factory creating...");
        }
        return planes;
    }
}
