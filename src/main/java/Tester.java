import entities.QuotesList;
import strategies.Strategy;

import java.time.LocalDateTime;

public class Tester {
    private Strategy strategy;
    private QuotesList quotes;

    public Tester(Strategy strategy, QuotesList quotes) {
        this.strategy = strategy;
        this.quotes = quotes;
    }

    public void startTest(LocalDateTime startDate, LocalDateTime endDate) {
        System.out.println(String.format("Start testing from %s to %s", startDate, endDate));
        strategy.onInit();

        int startIndex = quotes.getStartIndex(startDate);
        int endIndex = quotes.getEndIndex(endDate);

        System.out.println(String.format("start index %d at %s", startIndex, quotes.get(startIndex).getDateTime()));
        System.out.println(String.format("end index %d at %s", endIndex, quotes.get(endIndex).getDateTime()));

    }
}
