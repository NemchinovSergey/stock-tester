import entities.QuotesList;
import strategies.StrategyEmpty;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws IOException {
        //System.out.println(System.getProperty("user.dir"));

        QuotesList quotes = new QuotesList("yyyyMMdd", "HHmmss");

        quotes.loadFromFile("quotes\\Si_150101_170513.txt");
        System.out.println(quotes.getFileName());
        System.out.println(quotes.getQuotes().size());

        /*quotes.loadFromFile("quotes\\Si_160101_170513_1M.txt");
        System.out.println(quotes.getFileName());
        System.out.println(quotes.getQuotes().size());

        quotes.loadFromFile("quotes\\SPFB.Si_160101_170513.txt");
        System.out.println(quotes.getFileName());
        System.out.println(quotes.getQuotes().size());
        */

        Tester tester = new Tester(new StrategyEmpty(), quotes);
        tester.startTest(LocalDateTime.of(2016, 5, 1, 0, 0), LocalDateTime.now());
    }
}
