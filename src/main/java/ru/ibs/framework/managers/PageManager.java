package ru.ibs.framework.managers;

import ru.ibs.framework.pages.CalculatorPage;
import ru.ibs.framework.pages.StartPage;

public class PageManager {
    /**
     * Менеджер страничек
     */
    private static PageManager pageManager;

    /**
     * Стартовая страничка
     */
    private static StartPage startPage;

    /**
     * Страничка "Калькулятор доходности"
     */
    private static CalculatorPage calculatorPage;

    /**
     *  privat конструктор PageManager (singleton паттерн)
     *
     * @see PageManager#getPageManager()
     */
    private PageManager() {
    }

    /**
     * Метод ленивой инициализации PageManager
     *
     * @return PageManager
     */
    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    /**
     * Метод ленивой инициализации StartPage
     *
     * @return StartPage
     */
    public StartPage getStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
        }
        return startPage;
    }

    /**
     * Метод ленивой инициализации CalculatorPage
     *
     * @return CalculatorPage
     */
    public CalculatorPage getCalculatorPage() {
        if (calculatorPage == null) {
            calculatorPage = new CalculatorPage();
        }
        return calculatorPage;
    }
}
