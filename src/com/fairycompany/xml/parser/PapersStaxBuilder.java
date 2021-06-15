package com.fairycompany.xml.parser;

import com.fairycompany.xml.entity.AbstractPaper;
import com.fairycompany.xml.entity.Direction;
import com.fairycompany.xml.entity.Magazine;
import com.fairycompany.xml.entity.Newspaper;
import com.fairycompany.xml.exception.XmlTaskException;
import com.fairycompany.xml.handler.PaperXmlTag;
import com.fairycompany.xml.validator.PaperXmlValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.fairycompany.xml.handler.PaperXmlTag.*;

public class PapersStaxBuilder extends AbstractPaperBuilder{
    private static Logger logger = LogManager.getLogger();
    private static final String HYPHEN = "-";
    private static final String UNDERSCORE = "_";
    private XMLInputFactory inputFactory;

    public PapersStaxBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    public void buildSetPapers(String fileName) throws XmlTaskException {
        AbstractPaper paper = null;
        if (!PaperXmlValidator.validatePaperXml(fileName)) {
            throw new XmlTaskException("File " + fileName + " hasn't passed validation!");
        }
        try {
            XMLEventReader reader = inputFactory.createXMLEventReader(new FileInputStream(fileName));
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String textElement = startElement.getName().getLocalPart();
                    PaperXmlTag currentXmlTag;
                    currentXmlTag = PaperXmlTag.valueOf(textElement.toUpperCase().replaceAll(HYPHEN, UNDERSCORE));
                    switch (currentXmlTag) {
                        case NEWSPAPER:
                            paper = new Newspaper();
                            Attribute ageCategory = startElement.getAttributeByName(new QName(AGE_CATEGORY.getValue()));
                            paper.setAgeCategory(ageCategory.getValue());
                            Attribute website = startElement.getAttributeByName(new QName(WEBSITE.getValue()));
                            if (website != null) {
                                paper.setWebsite(website.getValue());
                            }
                            break;
                        case MAGAZINE:
                            paper = new Magazine();
                            ageCategory = startElement.getAttributeByName(new QName(AGE_CATEGORY.getValue()));
                            paper.setAgeCategory(ageCategory.getValue());
                            website = startElement.getAttributeByName(new QName(WEBSITE.getValue()));
                            if (website != null) {
                                paper.setWebsite(website.getValue());
                            }
                            break;
                        case NAME:
                            event = reader.nextEvent();
                            paper.setName(event.asCharacters().getData());
                            break;
                        case SUBSCRIPTION_INDEX:
                            event = reader.nextEvent();
                            paper.setSubscriptionIndex(event.asCharacters().getData());
                            break;
                        case CIRCULATION:
                            event = reader.nextEvent();
                            paper.setCirculation(Integer.parseInt(event.asCharacters().getData()));
                            break;
                        case COLOR:
                            event = reader.nextEvent();
                            ((Newspaper) paper).setColor(Boolean.parseBoolean(event.asCharacters().getData()));
                            break;
                        case FREQUENCY:
                            event = reader.nextEvent();
                            ((Newspaper) paper).setFrequency(event.asCharacters().getData());
                            break;
                        case DIRECTION:
                            event = reader.nextEvent();
                            ((Magazine) paper).setDirection(Direction.valueOf(event.asCharacters().getData().toUpperCase()));
                            break;
                        default:
                            if (event.isStartElement()) {
                                StartElement startElementProperty = event.asStartElement();
                                textElement = startElementProperty.getName().getLocalPart();
                                currentXmlTag = PaperXmlTag.valueOf(textElement.toUpperCase()
                                                .replaceAll(HYPHEN, UNDERSCORE));
                                switch (currentXmlTag) {
                                    case ISSUE:
                                        event = reader.nextEvent();
                                        paper.getPaperProperties()
                                                .setIssue(Integer.parseInt(event.asCharacters().getData()));
                                        break;
                                    case PAGES:
                                        event = reader.nextEvent();
                                        paper.getPaperProperties()
                                                .setPages(Integer.parseInt(event.asCharacters().getData()));
                                        break;
                                    case PRICE:
                                        event = reader.nextEvent();
                                        paper.getPaperProperties()
                                                .setPrice(Double.parseDouble(event.asCharacters().getData()));
                                        break;
                                    case ISSUE_DATE:
                                        event = reader.nextEvent();
                                        paper.getPaperProperties()
                                                .setIssueDate(parseStringToLocalDate(event.asCharacters().getData()));
                                        break;
                                }
                            }

                    }
                }
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    String textElement = endElement.getName().getLocalPart();
                    if (textElement.equals(NEWSPAPER.getValue()) || textElement.equals(MAGAZINE.getValue())){
                        papers.add(paper);
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            logger.log(Level.ERROR, "Error with the underlying XML {}", fileName);
        }
    }
}
