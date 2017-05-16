package strategies;

import entities.Quote;

public class StrategyEmpty implements Strategy {
    private int count;

    @Override
    public int onInit() {
        System.out.println(getClass().getSimpleName() + ".onInit()");
        // todo: инициализация индикаторов
        return SUCCESS;
    }

    @Override
    public void onTick(Quote quote) {
        // todo: перерасчёт индикаторов
        // todo: механизм запроса данных других баров, таймсерий
        // todo: механизм запроса данных других инструментов
        // todo: работа с заявками
        // todo: работа с позициями
        // todo: формирование торгового отчёта

        System.out.println(quote);

        /*if (count > 10) {
            throw new RuntimeException("Test exception...");
        }
        count++;*/
    }

    @Override
    public void onDeinit() {
        System.out.println(getClass().getSimpleName() + ".onDeinit()");
    }
}
