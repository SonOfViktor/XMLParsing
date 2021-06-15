package com.fairycompany.xml.parser;

import com.fairycompany.xml.entity.AbstractPaper;
import com.fairycompany.xml.entity.Direction;
import com.fairycompany.xml.entity.Magazine;
import com.fairycompany.xml.entity.Newspaper;
import com.fairycompany.xml.exception.XmlTaskException;
import com.fairycompany.xml.validator.PaperXmlValidator;
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
import java.io.IOException;

import static com.fairycompany.xml.handler.PaperXmlTag.*;

public class PapersDomBuilder extends AbstractPaperBuilder {
    private static Logger logger = LogManager.getLogger();
    private DocumentBuilder documentBuilder;

    public PapersDomBuilder() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.log(Level.ERROR, "DocumentBuilder cannot be created which satisfies the configuration requested");
        }
    }

    public void buildSetPapers(String fileName) throws XmlTaskException {
        if (!PaperXmlValidator.validatePaperXml(fileName)) {
            throw new XmlTaskException("File " + fileName + " hasn't passed validation!");
        }
        Document document;
        try {
            document = documentBuilder.parse(fileName);
            Element root = document.getDocumentElement();
            NodeList newspapersList = root.getElementsByTagName(NEWSPAPER.getValue());
            for (int i = 0; i < newspapersList.getLength(); i++) {
                Element newspaperElement = (Element) newspapersList.item(i);
                AbstractPaper newspaper = buildPaper(newspaperElement);
                papers.add(newspaper);
            }
            NodeList magazineList = root.getElementsByTagName(MAGAZINE.getValue());
            for (int i = 0; i < magazineList.getLength(); i++) {
                Element magazineElement = (Element) magazineList.item(i);
                AbstractPaper magazine = buildPaper(magazineElement);
                papers.add(magazine);
            }
        } catch (IOException | SAXException e) {
            logger.log(Level.ERROR, "Any SAX or IO Exception during parsing " + fileName);
        }
    }

    private AbstractPaper buildPaper(Element paperElement) throws XmlTaskException {
        String tempText;
        AbstractPaper paper;

        if (paperElement.getTagName().equals(NEWSPAPER.getValue())) {
            paper = new Newspaper();
            ((Newspaper) paper).setFrequency(getElementTextContent(paperElement, FREQUENCY.getValue()));
            tempText = getElementTextContent(paperElement, COLOR.getValue());
            ((Newspaper) paper).setColor(Boolean.parseBoolean(tempText));
        } else if (paperElement.getTagName().equals(MAGAZINE.getValue())) {
            paper = new Magazine();
            tempText = getElementTextContent(paperElement, DIRECTION.getValue());
            ((Magazine) paper).setDirection(Direction.valueOf(tempText.toUpperCase()));
        } else {
            throw new XmlTaskException("Unreachable exception");
        }

        paper.setAgeCategory(paperElement.getAttribute(AGE_CATEGORY.getValue()));

        tempText = paperElement.getAttribute(WEBSITE.getValue());
        if (!tempText.isEmpty()) {
            paper.setWebsite(tempText);
        }

        paper.setName(getElementTextContent(paperElement, NAME.getValue()));
        paper.setSubscriptionIndex(getElementTextContent(paperElement, SUBSCRIPTION_INDEX.getValue()));
        tempText = getElementTextContent(paperElement, ISSUE.getValue());
        paper.getPaperProperties().setIssue(Integer.parseInt(tempText));
        tempText = getElementTextContent(paperElement, PAGES.getValue());
        paper.getPaperProperties().setPages(Integer.parseInt(tempText));
        tempText = getElementTextContent(paperElement, PRICE.getValue());
        paper.getPaperProperties().setPrice(Double.parseDouble(tempText));
        tempText = getElementTextContent(paperElement, ISSUE_DATE.getValue());
        paper.getPaperProperties().setIssueDate(parseStringToLocalDate(tempText));
        tempText = getElementTextContent(paperElement, CIRCULATION.getValue());
        paper.setCirculation(Integer.parseInt(tempText));

        return paper;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
        return text;
    }
}
