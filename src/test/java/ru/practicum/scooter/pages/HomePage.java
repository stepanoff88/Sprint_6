package ru.practicum.scooter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class HomePage extends BasePage {

    // Заголовок главной страницы
    // By.xpath("//div[contains(@class,'Home_Header') and contains(.,'Самокат')]")
    private final By pageHeader = By.xpath("//div[contains(@class,'Home_Header') and contains(.,'Самокат')]");

    // Кнопка "Заказать" в шапке страницы
    // By.xpath("//div[contains(@class,'Header_Nav')]//button[normalize-space()='Заказать']")
    private final By headerOrderButton =
            By.xpath("//div[contains(@class,'Header_Nav')]//button[normalize-space()='Заказать']");

    // Кнопка "Заказать" в нижней части страницы
    // By.xpath("//div[contains(@class,'Home_FinishButton')]//button[normalize-space()='Заказать']")
    private final By bottomOrderButton =
            By.xpath("//div[contains(@class,'Home_FinishButton')]//button[normalize-space()='Заказать']");

    // Заголовок раздела "Вопросы о важном"
    // By.xpath("//div[contains(@class,'Home_FourPart')]//div[normalize-space()='Вопросы о важном']")
    private final By faqTitle =
            By.xpath("//div[contains(@class,'Home_FourPart')]//div[normalize-space()='Вопросы о важном']");

    // Контейнер раздела "Вопросы о важном"
    // By.xpath("//div[contains(@class,'Home_FAQ')]")
    private final By faqSection = By.xpath("//div[contains(@class,'Home_FAQ')]");

    // Кнопка закрытия баннера с cookie
    // By.xpath("//button[contains(@class,'App_CookieButton') or normalize-space()='да все привыкли']")
    private final By cookieButton =
            By.xpath("//button[contains(@class,'App_CookieButton') or normalize-space()='да все привыкли']");

    // Вопрос в аккордеоне: By.xpath("//div[contains(@class,'accordion__button')
    // and normalize-space()='<текст вопроса>']")
    // Ответ в аккордеоне: By.xpath("//div[contains(@class,'accordion__button')
    // and normalize-space()='<текст вопроса>']/ancestor::div[contains(@class,'accordion__item')]
    // //div[contains(@class,'accordion__panel')]")

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(BASE_URL);
        waitForVisible(pageHeader);
        dismissCookieBanner();
        return this;
    }

    public void dismissCookieBanner() {
        if (isVisible(cookieButton)) {
            click(cookieButton);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(cookieButton));
        }
    }

    public OrderPage clickOrderButton(OrderEntryPoint entryPoint) {
        By locator = entryPoint == OrderEntryPoint.HEADER ? headerOrderButton : bottomOrderButton;
        click(locator);
        waitForUrlContains("/order");

        OrderPage orderPage = new OrderPage(driver);
        orderPage.waitForLoad();
        return orderPage;
    }

    public void clickFaqQuestion(String question) {
        By questionLocator = faqQuestion(question);
        WebElement questionElement = waitForVisible(questionLocator);
        scrollIntoView(questionElement);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", questionElement);
        wait.until(driver -> "true".equals(questionElement.getAttribute("aria-expanded")));
    }

    public boolean isFaqAnswerVisible(String question) {
        List<WebElement> panels = driver.findElements(faqAnswer(question));
        return !panels.isEmpty() && panels.get(0).isDisplayed()
                && !normalize(panels.get(0).getText()).isEmpty();
    }

    public String waitForFaqAnswer(String question) {
        WebElement panel = waitForVisible(faqAnswer(question));
        return normalize(panel.getText());
    }

    public boolean isFaqSectionVisible() {
        return isVisible(faqTitle) && isVisible(faqSection);
    }

    private By faqQuestion(String question) {
        return By.xpath("//div[contains(@class,'accordion__button') and normalize-space()="
                + xpathLiteral(question) + "]");
    }

    private By faqAnswer(String question) {
        return By.xpath("//div[contains(@class,'accordion__button') and normalize-space()="
                + xpathLiteral(question) + "]"
                + "/ancestor::div[contains(@class,'accordion__item')]"
                + "//div[contains(@class,'accordion__panel')]");
    }

    private static String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }
        return "concat('" + value.replace("'", "',\"'\",'") + "')";
    }
}
