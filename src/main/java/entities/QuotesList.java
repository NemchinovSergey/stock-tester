package entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static utils.ParsingUtils.detectSeparator;
import static utils.ParsingUtils.prepareDoubleValue;


public class QuotesList {
    private List<Quote> quotes;
    private String fileName;
    private String separator;
    private DateTimeFormatter dateFormat;
    private DateTimeFormatter timeFormat;

    public QuotesList(String datePattern, String timePattern) {
        quotes = new ArrayList<>();
        dateFormat = DateTimeFormatter.ofPattern(datePattern);
        timeFormat = DateTimeFormatter.ofPattern(timePattern);
    }

    public int loadFromFile(String fileName) throws IOException {
        quotes.clear();
        int count = 0;
        BufferedReader reader = Files.newBufferedReader(Paths.get(fileName));
        try {
            String line = reader.readLine();
            if (line.toUpperCase().contains("TICKER") || line.toUpperCase().contains("DATE")) {
                separator = detectSeparator(line);
            } else {
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

        String value = lines[0];
        quote.setTicker(value);

        value = lines[1];
        quote.setPeriod(Integer.parseInt(value));

        value = lines[2];
        quote.setDate(LocalDate.from(dateFormat.parse(value)));

        value = lines[3];
        quote.setTime(LocalTime.from(timeFormat.parse(value)));

        quote.updateDateTime();

        /*System.out.println(String.format("Date: %s, Time: %s", lines[2], lines[3]));
        System.out.println(String.format("Parsed date: %s, time %s", quote.getDate().toString(), quote.getTime().toString()));
        System.out.println(String.format("Parsed dateTime: %s", quote.getDateTime().toString()));*/

        value = lines[4];
        String doubleValue = prepareDoubleValue(value);
        quote.setOpen(Double.parseDouble(doubleValue));

        value = lines[5];
        doubleValue = prepareDoubleValue(value);
        quote.setHigh(Double.parseDouble(prepareDoubleValue(doubleValue)));

        value = lines[6];
        doubleValue = prepareDoubleValue(value);
        quote.setLow(Double.parseDouble(prepareDoubleValue(doubleValue)));

        value = lines[7];
        doubleValue = prepareDoubleValue(value);
        quote.setClose(Double.parseDouble(prepareDoubleValue(doubleValue)));

        value = lines[8];
        doubleValue = prepareDoubleValue(value);
        quote.setVol(Double.parseDouble(prepareDoubleValue(doubleValue)));

        return quote;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public String getFileName() {
        return fileName;
    }

    public int getStartIndex(LocalDateTime startDate) {
        for (int i = 0; i < quotes.size(); i++) {
            Quote quote = quotes.get(i);
            if (quote.getDateTime().isEqual(startDate) || quote.getDateTime().isAfter(startDate)) {
                return i;
            }
        }
        return -1;
    }

    public int getEndIndex(LocalDateTime endDate) {
        for (int i = quotes.size() - 1; i >= 0; i--) {
            Quote quote = quotes.get(i);
            if (quote.getDateTime().isEqual(endDate) || quote.getDateTime().isBefore(endDate)) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return quotes.size();
    }

    public Quote get(int index) {
        return quotes.get(index);
    }
}
