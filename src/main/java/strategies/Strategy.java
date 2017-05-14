package strategies;

import entities.Quote;

public interface Strategy {
    void onInit();
    void onTick(Quote quote);
}
