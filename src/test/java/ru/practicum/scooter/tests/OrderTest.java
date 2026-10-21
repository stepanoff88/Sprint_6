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
import ru.practicum.scooter.pages.OrderEntryPoint;
import ru.practicum.scooter.pages.OrderPage;
import ru.practicum.scooter.testdata.OrderData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Заказ самоката")
class OrderTest {

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

    static Stream<Arguments> orderScenarios() {
        List<OrderData> dataSets = OrderData.testData();
        return Arrays.stream(OrderEntryPoint.values())
                .flatMap(entryPoint -> dataSets.stream()
                        .map(data -> Arguments.of(
                                entryPointName(entryPoint) + ": " + data,
                                entryPoint,
                                data
                        )));
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("orderScenarios")
    @DisplayName("Позитивный сценарий заказа с разными точками входа и данными")
    void positiveOrderScenario(String scenarioName, OrderEntryPoint entryPoint, OrderData data) {
        OrderPage orderPage = homePage.clickOrderButton(entryPoint);

        orderPage.fillCustomerStep(data);
        orderPage.fillRentStep(data);
        orderPage.clickOrder();

        assertTrue(orderPage.isConfirmationVisible(), "Должно появиться окно подтверждения заказа");
        orderPage.confirmOrder();
        orderPage.waitForSuccess();

        assertTrue(orderPage.getSuccessText().contains("Заказ оформлен"),
                "Должно появиться сообщение об успешном оформлении заказа");
        assertTrue(orderPage.getTrackNumber().matches("\\d+"), "Номер заказа должен состоять из цифр");
    }

    private static String entryPointName(OrderEntryPoint entryPoint) {
        return entryPoint == OrderEntryPoint.HEADER
                ? "Кнопка «Заказать» в шапке"
                : "Кнопка «Заказать» внизу страницы";
    }
}
