package com.fairycompany.xml.parser;

import com.fairycompany.xml.entity.AbstractPaper;
import com.fairycompany.xml.handler.PaperErrorHandler;
import com.fairycompany.xml.handler.PaperHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Set;

public class PapersSaxBuilder {
    private static Logger logger = LogManager.getLogger();
    private Set<AbstractPaper> papers;
    private PaperHandler handler = new PaperHandler();
    private XMLReader reader;

    public PapersSaxBuilder() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            reader =saxParser.getXMLReader();
        } catch (ParserConfigurationException | SAXException e) {
            logger.log(Level.ERROR, "Шеф, всё пропало");    //fixme
        }
        reader.setErrorHandler(new PaperErrorHandler());
        reader.setContentHandler(handler);
    }

    public Set<AbstractPaper> getPapers() {
        return papers;
    }

    public void buildSetPapers(String filename) {
        try {
            reader.parse(filename);
        } catch (IOException | SAXException e) {
            logger.log(Level.ERROR, "И тут шеф всё не очень"); //fixme
        }
        papers = handler.getPapers();
    }
}
