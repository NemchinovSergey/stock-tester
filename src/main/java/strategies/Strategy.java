package strategies;

import entities.Quote;

public interface Strategy {
    int SUCCESS = 0;
    int FAIL = 1;

    int onInit();
    void onTick(Quote quote);
    void onDeinit();
}
