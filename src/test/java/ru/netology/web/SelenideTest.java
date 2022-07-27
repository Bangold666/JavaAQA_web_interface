package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class SelenideTest {


    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");
    }

    @Test
    void ShouldGoWithSingleName() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type = 'button']").click();
        $("[data-test-id='order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш" +
                " менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void ShouldGoWithDoubleName() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Эмилия-Анна Васечкина");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type = 'button']").click();
        $("[data-test-id='order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш" +
                " менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void ShouldNotGoWithNameAndSymbols() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Василий+$@ Васечкин");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type = 'button']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные" +
                " неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void ShouldNotGoWithNameAndNumbers() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Василий 1 Васечкин 2");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type = 'button']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные" +
                " неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void ShouldNotGoWithNameOnLatin() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Vasya Vasechkin");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type = 'button']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные" +
                " неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void ShouldNotGoWithEmptyName() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("");
        form.$("[data-test-id=phone] input").setValue("Василий");
        form.$("[data-test-id=agreement]").click();
        form.$("[type = 'button']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"
        ));
    }

    @Test
    void ShouldNotGoWithPhoneUnderLimit() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Василий Васечкин");
        form.$("[data-test-id=phone] input").setValue("+7927000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type = 'button']").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан" +
                " неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void ShouldNotGoWithPhoneOverLimit() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Василий Васечкин");
        form.$("[data-test-id=phone] input").setValue("+792700000001");
        form.$("[data-test-id=agreement]").click();
        form.$("[type = 'button']").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан" +
                " неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void ShouldNotGoWithPhoneWithText() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Василий Васечкин");
        form.$("[data-test-id=phone] input").setValue("Василий");
        form.$("[data-test-id=agreement]").click();
        form.$("[type = 'button']").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан" +
                " неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void ShouldNotGoWithEmptyNumber() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("");
        form.$("[data-test-id=agreement]").click();
        form.$("[type = 'button']").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"
        ));
    }

    @Test
    void ShouldNotGoWithoutAgreementClick() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("+792700000001");
        form.$("[type = 'button']").click();
        $("[data-test-id='agreement'].input_invalid").shouldHave(exactText("Я соглашаюсь с условиями" +
                " обработки и использования моих персональных данных и разрешаю сделать запрос в бюро" +
                " кредитных историй"));
    }
}

