package com.fairycompany.xml.parser;

import com.fairycompany.xml.entity.AbstractPaper;
import com.fairycompany.xml.entity.Magazine;
import com.fairycompany.xml.entity.Newspaper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.print.Paper;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PapersDomBuilder {
    private static Logger logger = LogManager.getLogger();
    private Set<AbstractPaper> papers;
    private DocumentBuilder documentBuilder;

    public PapersDomBuilder() {
        papers = new HashSet<AbstractPaper>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.log(Level.ERROR, "Шеф, всё пропало!!");  //fixme
        }
    }

    public void buildSetPapers(String fileName) {
        Document document;
        try {
            document = documentBuilder.parse(fileName);
            Element root = document.getDocumentElement();
            NodeList newspapersList = root.getElementsByTagName("newspaper");
            for (int i = 0; i < newspapersList.getLength(); i++) {
                Element newspaperElement = (Element) newspapersList.item(i);
                AbstractPaper newspaper = buildPaper(newspaperElement);
                papers.add(newspaper);
            }
            NodeList magazineList = root.getElementsByTagName("magazine");      //todo сократить код
            for (int i = 0; i < magazineList.getLength(); i++) {
                Element magazineElement = (Element) magazineList.item(i);
                AbstractPaper magazine = buildPaper(magazineElement);
                papers.add(magazine);
            }
        } catch (IOException | SAXException e) {
            logger.log(Level.ERROR, "И тут засада");        //fixme
        }
    }

    private AbstractPaper buildPaper(Element paperElement) {
        AbstractPaper paper;
        if(paperElement.getTagName().equals("newspaper")) {
            paper = new Newspaper();
            ((Newspaper) paper).setFrequency(getElementTextContent(paperElement, "frequency"));
        } else {
            paper = new Magazine();

        }
        return paper;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
        return text;
    }
}
