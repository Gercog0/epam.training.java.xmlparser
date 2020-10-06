package by.epam.homework3.builder.impl.dom;

import by.epam.homework3.builder.AbstractPlaneBuilder;
import by.epam.homework3.builder.PlaneBuilderTag;
import by.epam.homework3.entity.*;
import by.epam.homework3.exception.ProjectException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class PlaneDOMBuilder extends AbstractPlaneBuilder {
    private DocumentBuilder documentBuilder;
    private DatatypeFactory datatypeFactory;
    private static final Logger logger = LogManager.getLogger(PlaneDOMBuilder.class);

    public PlaneDOMBuilder() throws ProjectException {
        this.planes = new HashSet<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = factory.newDocumentBuilder();
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (ParserConfigurationException | DatatypeConfigurationException exp) {
            logger.error(exp);
            throw new ProjectException("Datatype configuration error", exp);
        }
    }

    public PlaneDOMBuilder(Set<Plane> planes) throws ProjectException {
        super(planes);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = factory.newDocumentBuilder();
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (ParserConfigurationException | DatatypeConfigurationException exp) {
            logger.error(exp);
            throw new ProjectException("Datatype configuration error", exp);
        }
    }

    @Override
    public void buildPlanes(String fileLink) throws ProjectException {
        Document document;
        File file = new File(fileLink);
        try {
            document = documentBuilder.parse(file);
            Element root = document.getDocumentElement();
            NodeList planesList = root.getChildNodes();
            for (int i = 0; i < planesList.getLength(); i++) {
                if (planesList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) planesList.item(i);
                    Plane plane = buildPlane(element);
                    planes.add(plane);
                }
            }
        } catch (SAXException | IOException exp) {
            logger.error(exp);
            throw new ProjectException("DOM parsing error " + exp);
        }
    }

    private Plane buildPlane(Element element) throws ProjectException {
        Plane plane = null;
        if (element.getTagName().equals(PlaneBuilderTag.ORDINARY_PLANE.getTag())) {
            plane = buildOrdinaryPlane(element);
        }
        if (element.getTagName().equals(PlaneBuilderTag.MILITARY_PLANE.getTag())) {
            plane = buildMilitaryPlane(element);
        }
        if (plane == null) {
            throw new ProjectException("Error during build plane...");
        }
        plane.setId(element.getAttribute(PlaneBuilderTag.ID.getTag()));
        String description = element.getAttribute(PlaneBuilderTag.DESCRIPTION.getTag());
        if (!description.isEmpty()) {
            plane.setDescription(description);
        }
        plane.setModel(getElementTextContent(element, PlaneBuilderTag.MODEL.getTag()));
        plane.setOrigin(getElementTextContent(element, PlaneBuilderTag.ORIGIN.getTag()));
        plane.setChars(buildCharsPlane(element));
        plane.setParameters(buildParametersPlane(element));
        return plane;
    }

    private Chars buildCharsPlane(Element element) {
        Chars chars = new Chars();
        chars.setType((Chars.Type.getTypeByValue(
                getElementTextContent(element, PlaneBuilderTag.TYPE.getTag())).get()));
        chars.setPlaces(Integer.parseInt(getElementTextContent(element,
                PlaneBuilderTag.PLACES.getTag())));
        chars.setRadar(Boolean.parseBoolean(getElementTextContent(element,
                PlaneBuilderTag.RADAR.getTag())));
        return chars;
    }

    private Parameters buildParametersPlane(Element element) {
        Parameters parameters = new Parameters();
        parameters.setLength(new BigDecimal(getElementTextContent(element,
                PlaneBuilderTag.LENGTH.getTag())));
        parameters.setWidth(new BigDecimal(getElementTextContent(element,
                PlaneBuilderTag.WIDTH.getTag())));
        parameters.setWeight(new BigDecimal(getElementTextContent(element,
                PlaneBuilderTag.WEIGHT.getTag())));
        return parameters;
    }

    private OrdinaryPlane buildOrdinaryPlane(Element element) {
        OrdinaryPlane plane = new OrdinaryPlane();
        plane.setDate(datatypeFactory.newXMLGregorianCalendar(
                getElementTextContent(element, PlaneBuilderTag.DATE.getTag())));
        plane.setPrice(new BigDecimal(getElementTextContent(element,
                PlaneBuilderTag.PRICE.getTag())));

        return plane;
    }

    private Plane buildMilitaryPlane(Element element) {
        MilitaryPlane plane = new MilitaryPlane();
        plane.setWeapon(Boolean.parseBoolean(getElementTextContent(element,
                PlaneBuilderTag.WEAPON.getTag())));
        plane.setInvisibility(Boolean.parseBoolean(getElementTextContent(element,
                PlaneBuilderTag.INVISIBILITY.getTag())));
        return plane;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }
}
