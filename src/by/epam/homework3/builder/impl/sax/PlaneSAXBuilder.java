package by.epam.homework3.builder.impl.sax;

import by.epam.homework3.builder.AbstractPlaneBuilder;
import by.epam.homework3.entity.Plane;
import by.epam.homework3.exception.ProjectException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashSet;

public class PlaneSAXBuilder extends AbstractPlaneBuilder {
    private PlaneHandler handler;
    private XMLReader reader;

    private static final Logger logger = LogManager.getLogger(PlaneSAXBuilder.class);

    public PlaneSAXBuilder() throws ProjectException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
         handler =  new PlaneHandler();
         planes = new HashSet<>();
         try {
             SAXParser saxParser = saxParserFactory.newSAXParser();
             reader = saxParser.getXMLReader();
         } catch (SAXException | ParserConfigurationException exp){
             logger.error("Error while parsing " + exp);
         }
         reader.setContentHandler(handler);
    }

    public PlaneSAXBuilder(HashSet<Plane> planes) throws ProjectException {
        super(planes);
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        handler = new PlaneHandler();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            reader = saxParser.getXMLReader();
        } catch (SAXException | ParserConfigurationException exp){
            logger.error("Parsing error " + exp);
        }
        reader.setContentHandler(handler);
    }

    @Override
    public void buildPlanes(String fileLink) throws ProjectException {
        try {
            reader.parse(fileLink);
        } catch (SAXException | IOException exp) {
            logger.error("Parsing error " + exp);
        }
        planes = handler.getPlanes();
    }
}
