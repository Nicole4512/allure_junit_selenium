package ru.ibs.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.ibs.framework.pages.baseclass.BasePage;

import java.util.List;

public class StartPage extends BasePage {

    @FindBy(xpath = "//a[@class = 'site-header__content-bottom-nav-link nav__link nav__link_parent']")
    private List<WebElement> menuList;

    @FindBy(xpath = "//div[@class = 'nav__item-sub-nav']//a[@class = 'nav__link']")
    private List<WebElement> subMenuList;

    /**
     * Метод клина на главное меню
     *
     * @param nameBaseMenu - название меню
     * @return StartPage - т.е. остаемся на странице
     */
    @Step("Выбор главного меню '{nameBaseMenu}'")
    public StartPage selectBaseMenu(String nameBaseMenu){
        for(WebElement menuBtn : menuList){
            WebElement menuText = menuBtn.findElement(By.xpath(".//span[not(@class)]"));
            if (nameBaseMenu.equalsIgnoreCase(menuText.getText())){
                waitUtilElementToBeClickable(menuBtn).click();
                return this;
            }
        }
        Assertions.fail("Меню " + nameBaseMenu + " нет на странице");
        return this;
    }

    /**
     * Метод клика на подменю
     *
     * @param nameSubMenu - название подменю
     * @return CalculatorPage - т.е. переходим на страничку "Калькулятор доходности"
     */
    @Step("Выбор подменю '{nameSubMenu}'")
    public CalculatorPage selectSubMenu(String nameSubMenu){
        for(WebElement subMenu : subMenuList){
            if (subMenu.getText().contains(nameSubMenu)){
                waitUtilElementToBeClickable(subMenu).click();
                return pageManager.getCalculatorPage();
            }
        }
        Assertions.fail("Подменю " + nameSubMenu + " нет на странице");
        return pageManager.getCalculatorPage();
    }


}
