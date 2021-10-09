import ru.ibs.framework.basetest.BaseTest;

public class Test extends BaseTest {
    @org.junit.jupiter.api.Test

    public void test1() {
        app.getStartPage()
                .selectBaseMenu("Вклады")
                .selectSubMenu("Калькулятор доходности")
                .selectCurrency("Рубли")
                .fillField("Сумма вклада", "300 000")
                .fillField("На срок", "6 месяцев")
                .fillField("Ежемесячное пополнение", "50 000")
                .selectCheckbox("Ежемесячная капитализация", "True")
                .checkResults("Начислено %", "12 781,28")
                .checkResults("Пополнение за 6 месяцев", "250 000")
                .checkResults("К снятию через 6 месяцев", "562 781,28");
    }

    @org.junit.jupiter.api.Test

    public void test2(){
        app.getStartPage()
                .selectBaseMenu("Вклады")
                .selectSubMenu("Калькулятор доходности")
                .selectCurrency("Доллары США")
                .fillField("Сумма вклада", "500 000")
                .fillField("На срок", "12 месяцев")
                .fillField("Ежемесячное пополнение", "20 000")
                .selectCheckbox("Ежемесячная капитализация", "True")
                .checkResults("Начислено %", "920,60")
                .checkResults("Пополнение за 6 месяцев", "220 000")
                .checkResults("К снятию через 6 месяцев", "720 920,60");
    }
}
