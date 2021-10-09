package ru.ibs.framework.pages.baseclass;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ibs.framework.managers.DriverManager;
import ru.ibs.framework.managers.PageManager;
import ru.ibs.framework.managers.TestPropManager;

/**
 * Базовый класс всех страничек
 */
public class BasePage {

    /**
     * Менеджер WebDriver
     *
     * @see DriverManager#getDriverManager()
     */
    protected final DriverManager driverManager = DriverManager.getDriverManager();

    /**
     * Менеджер страничек
     *
     * @see PageManager
     */
    protected PageManager pageManager = PageManager.getPageManager();


    /**
     * Объект для имитации реального поведения мыши или клавиатуры
     *
     * @see Actions
     */
    protected Actions action = new Actions(driverManager.getWebDriver());


    /**
     * Объект для выполнения любого js кода
     *
     * @see JavascriptExecutor
     */
    protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getWebDriver();


    /**
     * Объект явного ожидания
     * При применении будет ожидать заданного состояния 10 секунд с интервалом в 1 секунду
     *
     * @see WebDriverWait
     */
    protected WebDriverWait wait = new WebDriverWait(driverManager.getWebDriver(), 10, 1000);


    /**
     * Менеджер properties
     *
     * @see TestPropManager#getTestPropManager()
     */
    private final TestPropManager props = TestPropManager.getTestPropManager();


    /**
     * Конструктор позволяющий инициализировать все странички и их элементы помеченные аннотацией {@link FindBy}
     * Подробнее можно просмотреть в класс {@link org.openqa.selenium.support.PageFactory}
     *
     * @see FindBy
     * @see PageFactory
     * @see PageFactory#initElements(WebDriver, Object)
     */
    public BasePage() {
        PageFactory.initElements(driverManager.getWebDriver(), this);
    }


    /**
     * Функция позволяющая производить scroll до любого элемента с помощью js
     *
     * @param element - веб-элемент странички
     * @see JavascriptExecutor
     */
    protected WebElement scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }



    /**
     * Явное ожидание состояния clickable элемента
     *
     * @param element - веб-элемент который требует проверки clickable
     * @return WebElement - возвращаем тот же веб элемент что был передан в функцию
     * @see WebDriverWait
     * @see org.openqa.selenium.support.ui.FluentWait
     * @see org.openqa.selenium.support.ui.Wait
     * @see ExpectedConditions
     */
    protected WebElement waitUtilElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Явное ожидание того что элемент станет видемым
     *
     * @param element - веб элемент который мы ожидаем что будет  виден на странице
     */
    protected WebElement waitUtilElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }


    /**
     * Общий метод по заполнения полей ввода
     *
     * @param field - веб-элемент поле ввода
     * @param value - значение вводимое в поле
     */
    protected void fillInputField(WebElement field, String value) {
        field.click();
        action.keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .sendKeys(Keys.BACK_SPACE)
                .build()
                .perform();
        field.sendKeys(value);
        action.sendKeys(Keys.ENTER);
    }


    /**
     * Общий метод по заполнению полей с датой
     *
     * @param field - веб-элемент поле с датой
     * @param value - значение вводимое в поле с датой
     */
    protected void fillDateField(WebElement field, String value) {
        scrollToElementJs(field);
        field.sendKeys(value);
    }

    /**
     * Общий метод по заполнению полей с номером телефона
     *
     * @see BasePage#convertValuePhone
     * @param phone - веб-элемент поле с номером телефона
     * @param value - значение вводимое в поле
     */
    protected void fillPhoneField(WebElement phone, String value) {
        scrollToElementJs(phone);
        wait.until(ExpectedConditions.elementToBeClickable(phone));
        phone.click();
        phone.sendKeys(value);
        boolean checkFlagPhone = wait.until(ExpectedConditions.attributeContains(phone, "value", convertValuePhone(value)));
        Assertions.assertTrue(checkFlagPhone, "Поле было заполнено некорректно");
    }

    /**
     * Метод для конвертации номера телефона
     *
     * @param value - воодимое значение номера телефона
     * @return - корректное значения номера телефона
     */
    private String convertValuePhone(String value) {
        return "+7 (" + value.substring(0, 3) + ") " + value.substring(3, 6) + "-" + value.substring(6, 8) + "-" + value.substring(8);
    }
}