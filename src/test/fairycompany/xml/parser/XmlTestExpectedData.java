package test.fairycompany.xml.parser;

import com.fairycompany.xml.entity.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class XmlTestExpectedData {
    public Set<AbstractPaper> papers = new HashSet<>();
    private PaperProperties paperProperties = new PaperProperties(1503, 100, 3.50,
            LocalDate.of(2021, 06, 16));

    XmlTestExpectedData() {
        papers.add(new Newspaper("Аиф", "i13137", "www.aif.by", AgeCategory.ANYONE,
                60000, paperProperties, true, Frequency.DAILY));

        papers.add(new Newspaper("СБ", "i00001", null, AgeCategory.ADULT, 25000,
                paperProperties, false, Frequency.WEEKLY));

        papers.add(new Magazine("Cool", "i20056", null, AgeCategory.TEENAGER,
                43000, paperProperties, Direction.ENTERTAINMENT));

        papers.add(new Magazine("Игромания", "i76398", "www.igromania.ru", AgeCategory.CHILD,
                180000, paperProperties, Direction.GAMING));
    }
}
