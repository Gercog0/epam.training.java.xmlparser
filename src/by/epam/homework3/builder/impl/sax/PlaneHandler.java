package by.epam.homework3.builder.impl.sax;

import by.epam.homework3.builder.PlaneBuilderTag;
import by.epam.homework3.entity.*;
import by.epam.homework3.exception.ProjectException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class PlaneHandler extends DefaultHandler {
    private Set<Plane> planes;
    private Plane current;
    private PlaneBuilderTag currentTag;
    private DatatypeFactory datatypeFactory;
    private static final int MAX_ATTRIBUTES_NUMBER = 2;

    private static final Logger logger = LogManager.getLogger(PlaneHandler.class);

    public PlaneHandler() throws ProjectException {
        planes = new HashSet<>();
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException exp) {
            logger.error(exp);
            throw new ProjectException("Datatype configuration error", exp);
        }
    }

    public Set<Plane> getPlanes() {
        return planes;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (PlaneBuilderTag.ORDINARY_PLANE.getTag().equals(qName) || PlaneBuilderTag.MILITARY_PLANE.getTag().equals(qName)) {
            if (PlaneBuilderTag.ORDINARY_PLANE.getTag().equals(qName)) {
                current = new OrdinaryPlane();
            } else {
                current = new MilitaryPlane();
            }
            current.setId(attributes.getValue(PlaneBuilderTag.ID.getTag()));
            if (attributes.getLength() == MAX_ATTRIBUTES_NUMBER) {
                current.setDescription(attributes.getValue(PlaneBuilderTag.DESCRIPTION.getTag()));
            }
        } else {
            Optional<PlaneBuilderTag> currentTagOptional = PlaneBuilderTag.getTagByValue(qName);
            currentTagOptional.ifPresent(planeBuilderTag -> currentTag = planeBuilderTag);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (PlaneBuilderTag.MILITARY_PLANE.getTag().equals(qName)
                || PlaneBuilderTag.ORDINARY_PLANE.getTag().equals(qName)) {
            planes.add(current);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String value = new String(ch, start, length).trim();
        if (currentTag != null) {
            switch (currentTag) {
                case MILITARY_PLANE:
                case ORDINARY_PLANE:
                    break;
                case MODEL:
                    current.setModel(value);
                    break;
                case ORIGIN:
                    current.setOrigin(value);
                    break;
                case CHARS:
                    current.setChars(new Chars());
                    break;
                case TYPE:
                    current.getChars().setType(Chars.Type.getTypeByValue(value).get());
                    break;
                case PLACES:
                    current.getChars().setPlaces(Integer.parseInt(value));
                case RADAR:
                    current.getChars().setRadar(Boolean.parseBoolean(value));
                    break;
                case PARAMETERS:
                    current.setParameters(new Parameters());
                    break;
                case LENGTH:
                    current.getParameters().setLength(new BigDecimal(value));
                    break;
                case WIDTH:
                    current.getParameters().setWidth(new BigDecimal(value));
                    break;
                case WEIGHT:
                    current.getParameters().setWeight(new BigDecimal(value));
                    break;
                case DATE:
                    ((OrdinaryPlane) current).setDate(datatypeFactory.newXMLGregorianCalendar(value));
                    break;
                case PRICE:
                    ((OrdinaryPlane) current).setPrice(new BigDecimal(value));
                    break;
                case WEAPON:
                    ((MilitaryPlane) current).setWeapon(Boolean.parseBoolean(value));
                    break;
                case INVISIBILITY:
                    ((MilitaryPlane) current).setInvisibility(Boolean.parseBoolean(value));
                    break;
                default:
                    logger.warn("Unknown tag");
            }
            currentTag = null;
        }
    }
}
