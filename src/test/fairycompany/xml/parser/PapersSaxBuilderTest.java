package test.fairycompany.xml.parser;

import com.fairycompany.xml.entity.AbstractPaper;
import com.fairycompany.xml.exception.XmlTaskException;
import com.fairycompany.xml.parser.AbstractPaperBuilder;
import com.fairycompany.xml.parser.PaperBuilderFactory;
import org.testng.annotations.*;

import java.util.Set;

import static org.testng.Assert.*;

public class PapersSaxBuilderTest {

    @DataProvider(name = "parsers")
    public Object[][] parsers() {
        Object[][] object = {{PaperBuilderFactory.createPaperBuilder("sax")},
                {PaperBuilderFactory.createPaperBuilder("dom")},
                {PaperBuilderFactory.createPaperBuilder("stax")}};

        return object;
    }

    @Test (dataProvider = "parsers")
    public void testBuildSetPapers(AbstractPaperBuilder builder) throws XmlTaskException {
        builder.buildSetPapers("resources/testdata/papers.xml");
        Set<AbstractPaper> actual = builder.getPapers();
        Set<AbstractPaper> expected = new XmlTestExpectedData().papers;
        assertEquals(expected, actual);
    }

    @Test (expectedExceptions = XmlTaskException.class, dataProvider = "parsers")
    public void testBuildSetPapersNullFile(AbstractPaperBuilder builder) throws XmlTaskException {
        builder.buildSetPapers("resources/testdata/null.xml");
    }

    @Test (expectedExceptions = XmlTaskException.class, dataProvider = "parsers")
    public void testBuildSetPapersEmptyFile(AbstractPaperBuilder builder) throws  XmlTaskException {
        builder.buildSetPapers("resources/testdata/empty.xml");
    }

    @Test (expectedExceptions = XmlTaskException.class, dataProvider = "parsers")
    public void testBuildSetPapersNotWellFormedFile(AbstractPaperBuilder builder) throws XmlTaskException {
        builder.buildSetPapers("resources/testdata/not_well_formed.xml");
    }

    @Test (expectedExceptions = XmlTaskException.class, dataProvider = "parsers")
    public void testBuildSetPapersNotValidFile(AbstractPaperBuilder builder) throws XmlTaskException {
        builder.buildSetPapers("resources/testdata/not_valid.xml");
    }
}