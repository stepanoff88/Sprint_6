package ru.practicum.scooter.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import ru.practicum.scooter.config.WebDriverFactory;
import ru.practicum.scooter.pages.HomePage;
import ru.practicum.scooter.testdata.FaqData;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Вопросы о важном")
class FaqTest {

    private WebDriver driver;
    private HomePage homePage;

    @BeforeEach
    void setUp() {
        driver = WebDriverFactory.createDriver();
        homePage = new HomePage(driver).open();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    static Stream<Arguments> faqItems() {
        return FaqData.items().stream()
                .map(item -> Arguments.of(item.getQuestion(), item.getAnswer()));
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("faqItems")
    @DisplayName("При клике на вопрос открывается соответствующий ответ")
    void questionOpensAnswer(String question, String expectedAnswer) {
        assertTrue(homePage.isFaqSectionVisible(), "Раздел «Вопросы о важном» должен быть виден");
        assertFalse(homePage.isFaqAnswerVisible(question), "Ответ не должен быть виден до клика");

        homePage.clickFaqQuestion(question);

        String actualAnswer = homePage.waitForFaqAnswer(question);
        assertTrue(homePage.isFaqAnswerVisible(question), "Ответ должен открыться после клика");
        assertEquals(expectedAnswer, actualAnswer);
    }
}
