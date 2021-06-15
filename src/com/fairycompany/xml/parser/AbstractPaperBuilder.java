package com.fairycompany.xml.parser;

import com.fairycompany.xml.entity.AbstractPaper;
import com.fairycompany.xml.exception.XmlTaskException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractPaperBuilder {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    protected Set<AbstractPaper> papers;

    public AbstractPaperBuilder() {
        papers = new HashSet<>();
    }

    public AbstractPaperBuilder (Set<AbstractPaper> papers) {
        this.papers = papers;
    }

    public abstract void buildSetPapers(String filename) throws XmlTaskException;

    public Set<AbstractPaper> getPapers() {
        return papers;
    }

    protected LocalDate parseStringToLocalDate(String data) {
        DateTimeFormatter dateTimeFormatter;
        LocalDate date;

        dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        date = LocalDate.parse(data, dateTimeFormatter);

        return date;
    }
}
