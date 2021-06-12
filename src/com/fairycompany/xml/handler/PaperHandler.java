package com.fairycompany.xml.handler;

import com.fairycompany.xml.entity.AbstractPaper;
import com.fairycompany.xml.entity.Magazine;
import com.fairycompany.xml.entity.Newspaper;
import com.fairycompany.xml.exception.XmlTaskException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class PaperHandler extends DefaultHandler {
    private static Logger logger = LogManager.getLogger();
    private Set<AbstractPaper> papers;
    private AbstractPaper currentPaper;
    private PaperXmlTag currentXmlTag;
    private EnumSet<PaperXmlTag> withText;
    private static final String ELEMENT_NEWSPAPER = "newspaper";
    private static final String ELEMENT_MAGAZINE = "magazine";
    private static final String TRUE = "true";

    public PaperHandler() {
        papers = new HashSet<AbstractPaper>();
        withText = EnumSet.range(PaperXmlTag.NAME, PaperXmlTag.DIRECTION);
    }

    public Set<AbstractPaper> getPapers() {
        return papers;
    }

    @Override
    public void startDocument() throws SAXException {
        logger.log(Level.INFO, "Parsing started");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (ELEMENT_NEWSPAPER.equals(qName) || ELEMENT_MAGAZINE.equals(qName)) {
            switch (qName) {
                case ELEMENT_NEWSPAPER:
                    currentPaper = new Newspaper();
                    break;
                case ELEMENT_MAGAZINE:
                    currentPaper = new Magazine();
                    break;
            }
            currentPaper.setAgeCategory(attributes.getValue("age-category"));
            if (attributes.getLength() == 2) {
                currentPaper.setWebsite(attributes.getValue("website"));
            }
        } else {
            PaperXmlTag temp = PaperXmlTag.valueOf(qName.toUpperCase().replaceAll("-", "_"));
            if (withText.contains(temp)) {
                currentXmlTag = temp;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String data = new String(ch, start, length).trim();
        if (currentXmlTag != null) {
            switch(currentXmlTag) {
                case NAME:
                    currentPaper.setName(data);
                    break;
                case SUBSCRIPTION_INDEX:
                    currentPaper.setSubscriptionIndex(data);
                    break;
                case ISSUE:
                    currentPaper.getPaperProperties().setIssue(Integer.parseInt(data));
                    break;
                case PAGES:
                    currentPaper.getPaperProperties().setPages(Integer.parseInt(data));
                    break;
                case PRICE:
                    currentPaper.getPaperProperties().setPrice(Double.parseDouble(data));
                    break;
                case ISSUE_DATE:
                    currentPaper.getPaperProperties().setIssueDate(parseStringToLocalDate(data));
                    break;
                case CIRCULATION:
                    currentPaper.setCirculation(Integer.parseInt(data));
                    break;
                case COLOR:
                    if (TRUE.equals(data)) {
                        ((Newspaper) currentPaper).setColor(true);
                    } else {
                        ((Newspaper) currentPaper).setColor(false);
                    }
                    break;
                case FREQUENCY:
                    ((Newspaper) currentPaper).setFrequency(data);
                    break;
                case DIRECTION:
                    ((Magazine) currentPaper).setDirection(data);
                    break;
                default:
                    throw new EnumConstantNotPresentException(currentXmlTag.getDeclaringClass(), currentXmlTag.name());
            }
        }
        currentXmlTag = null;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (ELEMENT_NEWSPAPER.equals(qName) || ELEMENT_MAGAZINE.equals(qName)) {
            papers.add(currentPaper);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        logger.log(Level.INFO, "Parsing ended");
    }

    private LocalDate parseStringToLocalDate(String data) {
        DateTimeFormatter dateTimeFormatter;
        LocalDate date;

        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date = LocalDate.parse(data, dateTimeFormatter);

        return date;
    }
}
