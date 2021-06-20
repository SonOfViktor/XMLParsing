package com.fairycompany.xml.parser;

import com.fairycompany.xml.entity.TypeParser;

public class PaperBuilderFactory {
    private PaperBuilderFactory() {

    }

    public static AbstractPaperBuilder createPaperBuilder(String typeParser) {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());

        switch (type) {
            case SAX:
                return new PapersSaxBuilder();
            case DOM:
                return new PapersDomBuilder();
            case STAX:
                return new PapersStaxBuilder();
            default:
                throw new EnumConstantNotPresentException(type.getDeclaringClass(), type.name());
        }
    }
}
