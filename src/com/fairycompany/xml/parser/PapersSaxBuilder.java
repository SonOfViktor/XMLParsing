package com.fairycompany.xml.parser;

import com.fairycompany.xml.exception.XmlTaskException;
import com.fairycompany.xml.handler.PaperHandler;
import com.fairycompany.xml.validator.PaperXmlValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class PapersSaxBuilder extends AbstractPaperBuilder {
    private static Logger logger = LogManager.getLogger();
    private PaperHandler handler = new PaperHandler();
    private XMLReader reader;

    public PapersSaxBuilder() {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            SAXParser saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();
        } catch (ParserConfigurationException e) {
            logger.log(Level.ERROR, "Parser cannot be created which satisfies the requested configuration");
        } catch (SAXException e) {
            logger.log(Level.ERROR, "any SAX errors occur during processing");
        }

        reader.setContentHandler(handler);
    }

    @Override
    public void buildSetPapers(String fileName) throws XmlTaskException {
        if (!PaperXmlValidator.validatePaperXml(fileName)) {
            throw new XmlTaskException(String.format("File %s hasn't passed validation!", fileName));
        }

        try {
            reader.parse(fileName);
        } catch (IOException | SAXException e) {
            logger.log(Level.ERROR, "Any SAX or IO Exception during parsing {}", fileName);
        }

        papers = handler.getPapers();
    }
}
