package entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static utils.ParsingUtils.detectSeparator;
import static utils.ParsingUtils.prepareDoubleValue;


public class QuotesList {
    private List<Quote> quotes;
    private String fileName;
    private String separator;
    private DateFormat dateFormat;
    private DateFormat timeFormat;

    public QuotesList(String datePattern, String timePattern) {
        quotes = new ArrayList<>();
        dateFormat = new SimpleDateFormat(datePattern);
        timeFormat = new SimpleDateFormat(timePattern);
    }

    public int loadFromFile(String fileName) throws IOException {
        quotes.clear();
        int count = 0;
        BufferedReader reader = Files.newBufferedReader(Paths.get(fileName));
        try {
            String line = reader.readLine();
            if (line.toUpperCase().contains("TICKER") || line.toUpperCase().contains("DATE")) {
                separator = detectSeparator(line);
            }
            else {
                Quote quote = parseQuote(line);
                quotes.add(quote);
                count++;
            }

            while ((line = reader.readLine()) != null) {
                quotes.add(parseQuote(line));
                count++;
            }
            this.fileName = fileName;
            return count;
        } catch (Exception e) {
            this.fileName = null;
            e.printStackTrace();
            return -1;
        }
    }

    private Quote parseQuote(String str) throws ParseException {
        // <TICKER>;<PER>;<DATE>;<TIME>;<OPEN>;<HIGH>;<LOW>;<CLOSE>;<VOL>
        // Si;60;20150105;100000;56.854.0000000;60.696.0000000;56.854.0000000;60.487.0000000;245.605
        // Si;60;20150105;110000;60.487.0000000;60.499.0000000;59.302.0000000;59.472.0000000;97.944
        String[] lines = str.split(separator);

        Quote quote = new Quote();
        quote.setTicker(lines[0]);
        quote.setPeriod(Integer.parseInt(lines[1]));
        quote.setDate(dateFormat.parse(lines[2]));
        quote.setTime(timeFormat.parse(lines[3]));

        String doubleValue = prepareDoubleValue(lines[4]);

        quote.setOpen(Double.parseDouble(doubleValue));
        quote.setHigh(Double.parseDouble(prepareDoubleValue(lines[5])));
        quote.setLow(Double.parseDouble(prepareDoubleValue(lines[6])));
        quote.setClose(Double.parseDouble(prepareDoubleValue(lines[7])));
        quote.setVol(Double.parseDouble(prepareDoubleValue(lines[8])));

        return quote;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public String getFileName() {
        return fileName;
    }
}
