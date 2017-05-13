import entities.QuotesList;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));

        QuotesList quotes = new QuotesList("yyyymmdd", "hhmmss");

        quotes.loadFromFile("quotes\\Si_150101_170513.txt");
        System.out.println(quotes.getFileName());
        System.out.println(quotes.getQuotes().size());

        quotes.loadFromFile("quotes\\Si_160101_170513_1M.txt");
        System.out.println(quotes.getFileName());
        System.out.println(quotes.getQuotes().size());

        quotes.loadFromFile("quotes\\SPFB.Si_160101_170513.txt");
        System.out.println(quotes.getFileName());
        System.out.println(quotes.getQuotes().size());
    }
}
