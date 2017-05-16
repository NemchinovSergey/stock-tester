import entities.QuotesList;
import strategies.Strategy;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Tester {
    private Strategy strategy;
    private QuotesList quotes;
    private LocalDateTime startTestingTime;
    private LocalDateTime endTestingTime;

    public Tester(Strategy strategy, QuotesList quotes) {
        this.strategy = strategy;
        this.quotes = quotes;
    }

    public void startTest(LocalDateTime startDate, LocalDateTime endDate) {
        int startIndex = quotes.getStartIndex(startDate);
        int endIndex = quotes.getEndIndex(endDate);

        if (startIndex == -1 || endIndex  == -1) {
            throw new IllegalArgumentException(String.format("No such history data (%s - %s)", startDate, endDate));
        }

        System.out.println(String.format("start index %d at %s", startIndex, quotes.get(startIndex).getDateTime()));
        System.out.println(String.format("end index %d at %s", endIndex, quotes.get(endIndex).getDateTime()));

        startTestingTime = LocalDateTime.now();

        System.out.println(String.format("Start testing from %s to %s", startDate, endDate));

        try {
            strategy.onInit();

            for (int i = startIndex; i <= endIndex; i++) {
                strategy.onTick(quotes.get(i));
            }

            strategy.onDeinit();
        }
        finally {
            System.out.println("Testing finished!");
            endTestingTime = LocalDateTime.now();
            System.out.println("Testing duration: " + getDuration(startTestingTime, endTestingTime));
        }
    }

    private String getDuration(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);

        long years = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears(years);

        long months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS);
        tempDateTime = tempDateTime.plusMonths(months);

        long days = tempDateTime.until(toDateTime, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays(days);

        long hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours(hours);

        long minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes(minutes);

        long seconds = tempDateTime.until(toDateTime, ChronoUnit.SECONDS);

        long mills = tempDateTime.until(toDateTime, ChronoUnit.MILLIS);

        return years + " years " +
                months + " months " +
                days + " days " +
                hours + " hours " +
                minutes + " minutes " +
                seconds + " seconds " +
                mills + " ms.";
    }
}
