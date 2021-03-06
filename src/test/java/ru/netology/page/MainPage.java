package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    private static final SelenideElement buyWithDebitCardButton = $(byText("Купить"));
    private static final SelenideElement buyWithCreditCardButton = $(byText("Купить в кредит"));
    private static final SelenideElement headerForSelectedWay = $("#root > div > h3");

    public void shouldPayWithDebitCard(){
        buyWithDebitCardButton.click();
        headerForSelectedWay.shouldHave(Condition.exactText("Оплата по карте"));

    }
    public void shouldPayWithCreditCard(){
        buyWithCreditCardButton.click();
        headerForSelectedWay.shouldHave(Condition.exactText("Кредит по данным карты"));
    }
}
