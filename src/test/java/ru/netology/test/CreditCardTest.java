package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.SQLHelper;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.*;

public class CreditCardTest {
    MainPage mainPage = new MainPage();
    PaymentPage paymentPage = new PaymentPage();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        mainPage.shouldPayWithCreditCard();
    }

    @AfterAll
    static void cleanDataBases() {
        SQLHelper.cleanDb();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    public void shouldSubmitIfApprovedCard() {
        val info = getValidApprovedCard();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGetSuccessMessage();
        val paymentId = getPaymentId();
        val expectedAmount = "4500000";
        val actualAmount = getPaymentAmount(paymentId);
        val expectedStatus = "APPROVED";
        val actualStatus = getStatusForPaymentWithDebitCard(paymentId);
        assertEquals(expectedAmount, actualAmount);
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void shouldNotSubmitIfDeclinedCard() {
        val info = getValidDeclinedCard();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGetFailMessage();
        val paymentId = getPaymentId();
        val expectedStatus = "DECLINED";
        val actualStatus = getStatusForPaymentWithDebitCard(paymentId);
        assertEquals(expectedStatus, actualStatus);
    }


    @Test
    void shouldNotSubmitIfFieldCardEmpty() {
        val info = getEmptyCard();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldFillMessage();
    }

    @Test
    void shouldNotSubmitIfCardNotFull() {
        val info = getInvalidCardWith15Numbers();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGetWrongFormatMessage();
    }

    @Test
    void shouldNotSubmitIfAnotherBankCard() {
        val info = getAnotherBankCard();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGetWrongFormatMessage();
    }


    @Test
    void shouldNotSubmitIfEmptyMonth() {
        val info = getEmptyMonth();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldFillMessage();
    }

    @Test
    void shouldNotSubmitIfMonthIsZero() {
        val info = getInvalidFormatMonthIsZero();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGetWrongTermMessage();
    }

    @Test
    void shouldNotSubmitIfMonth1Number() {
        val info = getInvalidFormatMonthIsOneNumber();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGetWrongFormatMessage();
    }

    @Test
    void shouldNotSubmitIfMonthIncorrect() {
        val info = getInvalidFormatMonthIsIncorrect();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGetWrongTermMessage();
    }

    @Test
    void shouldNotSubmitIfYearEmpty() {
        val info = getEmptyYear();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldFillMessage();
    }

    @Test
    void shouldNotSubmitIfYearEarly() {
        val info = getEarlyYear();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGeCardExpiredMessage();
    }

    @Test
    void shouldNotSubmitIfYearFuture() {
        val info = getFutureYear();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGetWrongTermMessage();
    }

    @Test
    void shouldNotSubmitWhenOwnerEmpty() {
        val info = getEmptyOwner();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldFillMessage();
    }

    @Test
    void shouldNotSubmitIfOwnerUsingOneWord() {
        val info = getInvalidOwnerUsingOneWord();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGetWrongFormatMessage();
    }

    @Test
    void shouldNotSubmitIf3Words() {
        val info = getInvalidOwnerWithThreeWords();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGetWrongFormatMessage();
    }

    @Test
    void shouldNotSubmitIfOwnerUsingLowerCase() {
        val info = getInvalidOwnerWithLowerCase();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGetWrongFormatMessage();
    }

    @Test
    void shouldNotSubmitIfOwnerUsingUpperCase() {
        val info = getInvalidOwnerWithUpperCase();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGetWrongFormatMessage();
    }

    @Test
    void shouldNotSubmitIfOwnerRu() {
        val info = getInvalidOwnerRu();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGetWrongFormatMessage();
    }

    @Test
    void shouldNotSubmitOwnersWithNumbers() {
        val info = getInvalidOwnerWithNumbers();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGetWrongFormatMessage();
    }

    @Test
    void shouldNotSubmitOwnersWithSymbols() {
        val info = getInvalidOwnerWithSymbols();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGetWrongFormatMessage();
    }

    @Test
    void shouldSubmitIfСVVIsEmpty() {
        val info = getEmptyCVV();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldFillMessage();
    }

    @Test
    void shouldNotSubmitIfCVVOneNumber() {
        val info = getInvalidCVVWith1Number();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGetWrongFormatMessage();
    }

    @Test
    void shouldNotSubmitIfCVVTwoNumbers() {
        val info = getInvalidCVVWith2Numbers();
        paymentPage.shouldFillForm(info);
        paymentPage.shouldGetWrongFormatMessage();
    }
}
