package by.epam.homework3.builder.impl.stax;

import by.epam.homework3.builder.AbstractPlaneBuilder;
import by.epam.homework3.builder.PlaneBuilderTag;
import by.epam.homework3.entity.*;
import by.epam.homework3.exception.ProjectException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PlaneStAXBuilder extends AbstractPlaneBuilder {
    private XMLInputFactory inputFactory;
    private DatatypeFactory datatypeFactory;

    private static final Logger logger = LogManager.getLogger(PlaneStAXBuilder.class);

    public PlaneStAXBuilder() throws ProjectException {
        inputFactory = XMLInputFactory.newInstance();
        planes = new HashSet<>();
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException exp) {
            logger.error("Datatype configuration error " + exp);
            throw new ProjectException("Datatype configuration error", exp);
        }
    }

    public PlaneStAXBuilder(Set<Plane> planes) throws ProjectException {
        super(planes);
        inputFactory = XMLInputFactory.newInstance();
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException exp) {
            logger.error("Datatype configuration error " + exp);
            throw new ProjectException("Datatype configuration error", exp);
        }
    }

    @Override
    public void buildPlanes(String fileLink) throws ProjectException {
        try (FileInputStream fileInputStream = new FileInputStream(new File(fileLink))) {
            XMLStreamReader reader = inputFactory.createXMLStreamReader(fileInputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    String name = reader.getLocalName();
                    Optional<PlaneBuilderTag> currentTag = PlaneBuilderTag.getTagByValue(name);
                    if (currentTag.isPresent()) {
                        if (PlaneBuilderTag.ORDINARY_PLANE.equals(currentTag.get())) {
                            Plane plane = buildPlane(reader, new OrdinaryPlane());
                            planes.add(plane);
                        }
                        if (PlaneBuilderTag.MILITARY_PLANE.equals(currentTag.get())) {
                            Plane plane = buildPlane(reader, new MilitaryPlane());
                            planes.add(plane);
                        }
                    }
                }
            }
        } catch (XMLStreamException | IOException exp) {
            logger.error(exp);
        }
    }

    private Plane buildPlane(XMLStreamReader reader, Plane plane) throws XMLStreamException, ProjectException {
        plane.setId(reader.getAttributeValue(null, PlaneBuilderTag.ID.getTag()));
        String description = reader.getAttributeValue(null, PlaneBuilderTag.DESCRIPTION.getTag());
        if (description != null) {
            plane.setDescription(description);
        }
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (PlaneBuilderTag.getTagByValue(name).get()) {
                        case MODEL:
                            plane.setModel(getXMLText(reader));
                            break;
                        case ORIGIN:
                            plane.setOrigin(getXMLText(reader));
                            break;
                        case CHARS:
                            plane.setChars(buildPlaneChars(reader));
                            break;
                        case PARAMETERS:
                            plane.setParameters(buildPlaneParameters(reader));
                            break;
                        case DATE:
                            ((OrdinaryPlane) plane).setDate(datatypeFactory.newXMLGregorianCalendar(getXMLText(reader)));
                            break;
                        case PRICE:
                            ((OrdinaryPlane) plane).setPrice(new BigDecimal(getXMLText(reader)));
                            break;
                        case WEAPON:
                            ((MilitaryPlane) plane).setWeapon(Boolean.parseBoolean(getXMLText(reader)));
                            break;
                        case INVISIBILITY:
                            ((MilitaryPlane) plane).setInvisibility(Boolean.parseBoolean(getXMLText(reader)));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    Optional<PlaneBuilderTag> currentTag = PlaneBuilderTag.getTagByValue(name);
                    if (PlaneBuilderTag.MILITARY_PLANE.equals(PlaneBuilderTag.getTagByValue(name).get())
                            || PlaneBuilderTag.ORDINARY_PLANE.equals(PlaneBuilderTag.getTagByValue(name).get())) {
                        return plane;
                    }

                    break;
            }
        }
        logger.warn("Unexpected tag during building plane chars");
        throw new ProjectException("Unexpected tag");
    }

    private Chars buildPlaneChars(XMLStreamReader reader) throws XMLStreamException, ProjectException {
        Chars chars = new Chars();

        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (PlaneBuilderTag.getTagByValue(name).get()) {
                        case TYPE:
                            chars.setType(Chars.Type.getTypeByValue(getXMLText(reader)).get());
                            break;
                        case PLACES:
                            chars.setPlaces(Integer.parseInt(getXMLText(reader)));
                            break;
                        case RADAR:
                            chars.setRadar(Boolean.parseBoolean(getXMLText(reader)));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (PlaneBuilderTag.CHARS.equals(PlaneBuilderTag.getTagByValue(name).get())) {
                        return chars;
                    }
                    break;
            }
        }
        logger.warn("Unexpected tag during building plane chars");
        throw new ProjectException("Unexpected tag");
    }

    private Parameters buildPlaneParameters(XMLStreamReader reader) throws XMLStreamException, ProjectException {
        Parameters parameters = new Parameters();

        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (PlaneBuilderTag.getTagByValue(name).get()) {
                        case LENGTH:
                            parameters.setLength(new BigDecimal(getXMLText(reader)));
                            break;
                        case WIDTH:
                            parameters.setWidth(new BigDecimal(getXMLText(reader)));
                            break;
                        case WEIGHT:
                            parameters.setWeight(new BigDecimal(getXMLText(reader)));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (PlaneBuilderTag.PARAMETERS.equals(PlaneBuilderTag.getTagByValue(name).get())) {
                        return parameters;
                    }
                    break;
            }
        }
        logger.warn("Unexpected tag during building plane chars");
        throw new ProjectException("Unexpected tag");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
