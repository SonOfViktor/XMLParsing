package com.fairycompany.xml.validator;

import com.fairycompany.xml.handler.PaperErrorHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class PaperXmlValidator {
    private static Logger logger = LogManager.getLogger();

    public static Boolean validatePaperXml(String fileName) {
        boolean isXmlRight = false;
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        String schemaName = "resources/data/papers.xsd";         // fixme if local var must be class var
        SchemaFactory factory = SchemaFactory.newInstance(language);
        File schemaLocation = new File(schemaName);

        try {
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(fileName);
            validator.setErrorHandler(new PaperErrorHandler());
            validator.validate(source);
            isXmlRight = true;
            logger.log(Level.DEBUG, "File is valid");
        } catch (SAXException e) {    // null and empty catch
            logger.log(Level.ERROR, "{} or {} is not correct of valid", fileName, schemaName);
        } catch (IOException e) {
            logger.log(Level.ERROR, "{} can't be read", fileName);
        }
        return isXmlRight;
    }
}
