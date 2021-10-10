package ru.ibs.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import ru.ibs.framework.pages.baseclass.BasePage;

import java.util.List;

import static java.lang.Thread.sleep;


public class CalculatorPage extends BasePage {
    @FindBy(xpath = "//span[@class = 'calculator__currency-field-btn']")
    private List<WebElement> currencyList;

    @FindBy(xpath = "//div[@class = 'calculator__content']//label[@class = 'calculator__check-block']")
    private List<WebElement> checkboxList;

    @FindBy(xpath = "//select[@class = 'calculator__slide-input js-slide-value']")
    private WebElement date;

    @FindBy(xpath = "//input[@name = 'amount']")
    private WebElement depositAmount;

    @FindBy(xpath = "//input[@name = 'replenish']")
    private WebElement replenishment;

    @FindBy(xpath = "//span[@class = 'js-calc-earned']")
    private WebElement percent;

    @FindBy(xpath = "//span[@class = 'js-calc-result']")
    private WebElement result;

    @FindBy(xpath = "//span[@class = 'js-calc-replenish']")
    private WebElement replenishmentCheck;

    /**
     * Метод выбора валюты
     *
     * @param currencyName - название валюты
     * @return CalculatorPage - т.е. остаемся на странице
     */
    @Step("Выбрать валюту {currencyName}")
    public CalculatorPage selectCurrency(String currencyName) {
        for (WebElement currency : currencyList) {
            WebElement currencyText = currency.findElement(By.xpath(".//span[@class = 'calculator__currency-field-text']"));
            if (currencyName.equalsIgnoreCase(currencyText.getText())) {
                waitUtilElementToBeClickable(currency).click();
                return this;
            }
        }
        Assertions.fail("Валюты " + currencyName + " нет на странице");
        return this;

    }

    /**
     * Метод заполнения полей формы
     *
     * @param fieldName - название поля
     * @param value     - значение поля
     * @return CalculatorPage - т.е. остаемся на странице
     */
    @Step("Заполнить поле {fieldName} значением {value}")
    public CalculatorPage fillField(String fieldName, String value) {

        switch (fieldName) {
            case "Сумма вклада":
                fillInputField(depositAmount, value);
                boolean checkFlagDepositAmount = wait.until(ExpectedConditions.attributeContains(depositAmount, "value", value));
                Assertions.assertTrue(checkFlagDepositAmount, "Поле 'Сумма вклада' было заполнено некорректно");
                return this;
            case "Ежемесячное пополнение":
                fillInputField(replenishment, value);
                boolean checkFlagReplenishment = wait.until(ExpectedConditions.attributeContains(replenishment, "value", value));
                Assertions.assertTrue(checkFlagReplenishment, "Поле 'Ежемесячное пополнение' было заполнено некорректно");
                return this;
            case "На срок":
                Select dateSelect = new Select(date);
                dateSelect.selectByVisibleText(value);
                return this;

            default:
                Assertions.fail("Поля " + fieldName + " нет на странице");
                return this;
        }

    }

    /**
     * Метод изменения состояния checkbox
     *
     * @param checkboxName - название checkbox
     * @param value        - значение checkbox
     * @return CalculatorPage - т.е. остаемся на странице
     */
    @Step("Изменить состояние {checkboxName} на {value}")
    public CalculatorPage selectCheckbox(String checkboxName, String value) {
        for (WebElement checkbox : checkboxList) {
            WebElement checkboxText = checkbox.findElement(By.xpath(".//span[@class = 'calculator__check-text']"));
            if (checkboxName.equalsIgnoreCase(checkboxText.getText())) {
                WebElement checkboxBtn = checkbox.findElement(By.xpath(".//div[@unselectable = 'on']"));
                if (checkboxBtn.getAttribute("className").contains("checked")) {
                    if (value.equalsIgnoreCase("False")) {
                        checkboxBtn.click();
                        return this;
                    }
                } else if (value.equalsIgnoreCase("True")) {
                    checkboxBtn.click();
                }
            }
        }
        Assertions.fail(checkboxName + " нет на странице");
        return this;
    }

    /**
     * Метод проверки расчета
     *
     * @param name  - название поля для проверки
     * @param value - ожидаемое значение
     * @return CalculatorPage - т.е. остаемся на странице
     */
    @Step("Проверить, что расчет по полю {name} равен {value}")
    public CalculatorPage checkResults(String name, String value) {
        switch (name) {
            case "Начислено %":
                if (!value.equalsIgnoreCase(percent.getText())) {
                    Assertions.fail("Расчет поля 'Начислено %' не совпадает с ожидаемым");
                }
                return this;
            case "Пополнение за 6 месяцев":
                if (!value.equalsIgnoreCase(replenishmentCheck.getText())) {
                    Assertions.fail("Расчет поля 'Пополнение за 6 месяцев' не совпадает с ожидаемым");
                }
                return this;
            case "К снятию через 6 месяцев":
                if (!value.equalsIgnoreCase(result.getText())) {
                    Assertions.fail("Расчет поля 'К снятию через 6 месяцев' не совпадает с ожидаемым");
                }
                return this;
            default:
                Assertions.fail("Расчета по полю " + name + " нет на странице");
                return this;
        }
    }


}

