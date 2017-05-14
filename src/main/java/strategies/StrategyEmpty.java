package strategies;

import entities.Quote;

public class StrategyEmpty implements Strategy {
    @Override
    public void onInit() {
        System.out.println(getClass().getSimpleName() + ".onInit()");
    }

    @Override
    public void onTick(Quote quote) {
        System.out.println(getClass().getSimpleName() + ".onTick()");
        System.out.println(quote);
    }
}
