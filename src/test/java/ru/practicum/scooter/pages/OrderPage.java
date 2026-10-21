package ru.practicum.scooter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.practicum.scooter.testdata.OrderData;
import ru.practicum.scooter.testdata.ScooterColor;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderPage extends BasePage {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final Pattern MONTH_PATTERN = Pattern.compile("(\\d{4})-(\\d{2})");
    private static final Pattern TRACK_PATTERN = Pattern.compile("Номер заказа:\\s*(\\d+)");

    // Заголовок первого шага "Для кого самокат"
    // By.xpath("//div[contains(@class,'Order_Header') and normalize-space()='Для кого самокат']")
    private final By firstStepHeader =
            By.xpath("//div[contains(@class,'Order_Header') and normalize-space()='Для кого самокат']");

    // Поле "Имя"
    // By.xpath("//input[contains(@placeholder,'Имя')]")
    private final By firstNameInput = By.xpath("//input[contains(@placeholder,'Имя')]");

    // Поле "Фамилия"
    // By.xpath("//input[contains(@placeholder,'Фамилия')]")
    private final By lastNameInput = By.xpath("//input[contains(@placeholder,'Фамилия')]");

    // Поле "Адрес: куда привезти заказ"
    // By.xpath("//input[contains(@placeholder,'Адрес: куда привезти заказ')]")
    private final By addressInput =
            By.xpath("//input[contains(@placeholder,'Адрес: куда привезти заказ')]");

    // Поле "Станция метро"
    // By.xpath("//input[contains(@placeholder,'Станция метро')]")
    private final By metroInput = By.xpath("//input[contains(@placeholder,'Станция метро')]");

    // Вариант станции метро в выпадающем списке
    // By.xpath("//button[contains(@class,'Order_SelectOption')][.//div[normalize-space()='<станция>']]")
    // Поле "Телефон"
    // By.xpath("//input[contains(@placeholder,'Телефон: на него позвонит курьер')]")
    private final By phoneInput =
            By.xpath("//input[contains(@placeholder,'Телефон: на него позвонит курьер')]");

    // Кнопка "Далее"
    // By.xpath("//button[normalize-space()='Далее']")
    private final By nextButton = By.xpath("//button[normalize-space()='Далее']");

    // Заголовок второго шага "Про аренду"
    // By.xpath("//div[contains(@class,'Order_Header') and normalize-space()='Про аренду']")
    private final By secondStepHeader =
            By.xpath("//div[contains(@class,'Order_Header') and normalize-space()='Про аренду']");

    // Поле "Когда привезти самокат"
    // By.xpath("//input[contains(@placeholder,'Когда привезти самокат')]")
    private final By deliveryDateInput =
            By.xpath("//input[contains(@placeholder,'Когда привезти самокат')]");

    // Календарь выбора даты
    // By.cssSelector(".react-datepicker__month[aria-label]")
    private final By calendarMonth = By.cssSelector(".react-datepicker__month[aria-label]");

    // Кнопка перехода к следующему месяцу в календаре
    // By.cssSelector(".react-datepicker__navigation--next")
    private final By nextMonthButton = By.cssSelector(".react-datepicker__navigation--next");

    // Кнопка перехода к предыдущему месяцу в календаре
    // By.cssSelector(".react-datepicker__navigation--previous")
    private final By previousMonthButton = By.cssSelector(".react-datepicker__navigation--previous");

    // День в календаре
    // By.xpath("//div[contains(@class,'react-datepicker__day--0DD')
    // and not(contains(@class,'outside-month'))]")
    // Выпадающий список "Срок аренды"
    // By.className("Dropdown-control")
    private final By rentalPeriodDropdown = By.className("Dropdown-control");

    // Вариант срока аренды
    // By.xpath("//div[contains(@class,'Dropdown-option') and normalize-space()='<срок>']")
    // Чекбокс "чёрный жемчуг"
    // By.xpath("//label[@for='black']")
    private final By blackColorCheckbox = By.xpath("//label[@for='black']");

    // Чекбокс "серая безысходность"
    // By.xpath("//label[@for='grey']")
    private final By greyColorCheckbox = By.xpath("//label[@for='grey']");

    // Поле "Комментарий для курьера"
    // By.xpath("//input[contains(@placeholder,'Комментарий для курьера')]")
    private final By commentInput =
            By.xpath("//input[contains(@placeholder,'Комментарий для курьера')]");

    // Кнопка "Заказать" на втором шаге
    // By.xpath("//div[contains(@class,'Order_Buttons')]//button[normalize-space()='Заказать']")
    private final By orderButton =
            By.xpath("//div[contains(@class,'Order_Buttons')]//button[normalize-space()='Заказать']");

    // Модальное окно "Хотите оформить заказ?"
    // By.xpath("//div[contains(@class,'Order_ModalHeader') and contains(.,'Хотите оформить заказ?')]")
    private final By confirmationModal =
            By.xpath("//div[contains(@class,'Order_ModalHeader') and contains(.,'Хотите оформить заказ?')]");

    // Кнопка "Да" в модальном окне подтверждения
    // By.xpath("//div[contains(@class,'Order_Modal')]//button[normalize-space()='Да']")
    private final By confirmYesButton =
            By.xpath("//div[contains(@class,'Order_Modal')]//button[normalize-space()='Да']");

    // Модальное окно "Заказ оформлен"
    // By.xpath("//div[contains(@class,'Order_ModalHeader') and contains(.,'Заказ оформлен')]")
    private final By successModal =
            By.xpath("//div[contains(@class,'Order_ModalHeader') and contains(.,'Заказ оформлен')]");

    public OrderPage(WebDriver driver) {
        super(driver);
    }

    public OrderPage waitForLoad() {
        waitForVisible(firstStepHeader);
        return this;
    }

    public void fillCustomerStep(OrderData data) {
        type(firstNameInput, data.getFirstName());
        type(lastNameInput, data.getLastName());
        type(addressInput, data.getAddress());
        selectMetro(data.getMetroStation());
        type(phoneInput, data.getPhone());
        click(nextButton);
        waitForVisible(secondStepHeader);
    }

    public void fillRentStep(OrderData data) {
        selectDeliveryDate(data.getDeliveryDate());
        selectRentalPeriod(data.getRentalPeriod());
        selectColor(data.getColor());
        if (!data.getComment().isEmpty()) {
            type(commentInput, data.getComment());
        }
    }

    public void selectMetro(String station) {
        type(metroInput, station);
        By option = metroOption(station);
        click(option);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(option));
    }

    public void selectDeliveryDate(LocalDate date) {
        click(deliveryDateInput);
        waitForVisible(calendarMonth);
        openMonth(YearMonth.from(date));

        String dayClass = String.format("%03d", date.getDayOfMonth());
        By day = By.xpath("//div[contains(@class,'react-datepicker__day--" + dayClass + "')"
                + " and not(contains(@class,'outside-month'))]");
        click(day);

        String expectedValue = date.format(DATE_FORMAT);
        wait.until(driver -> expectedValue.equals(driver.findElement(deliveryDateInput).getAttribute("value")));
    }

    public void selectRentalPeriod(String period) {
        click(rentalPeriodDropdown);
        click(rentalPeriodOption(period));
        wait.until(driver -> normalize(driver.findElement(rentalPeriodDropdown).getText()).contains(period));
    }

    public void selectColor(ScooterColor color) {
        click(color == ScooterColor.BLACK ? blackColorCheckbox : greyColorCheckbox);
    }

    public void clickOrder() {
        click(orderButton);
        waitForVisible(confirmationModal);
    }

    public void confirmOrder() {
        click(confirmYesButton);
    }

    public boolean isConfirmationVisible() {
        return isVisible(confirmationModal);
    }

    public void waitForSuccess() {
        waitForVisible(successModal);
    }

    public String getSuccessText() {
        return normalizedText(successModal);
    }

    public String getTrackNumber() {
        Matcher matcher = TRACK_PATTERN.matcher(getSuccessText());
        if (!matcher.find()) {
            throw new IllegalStateException("В окне успешного заказа не найден номер заказа");
        }
        return matcher.group(1);
    }

    private void openMonth(YearMonth targetMonth) {
        for (int attempt = 0; attempt < 24; attempt++) {
            WebElement month = waitForVisible(calendarMonth);
            String monthAriaLabel = month.getAttribute("aria-label");
            YearMonth visibleMonth = parseMonth(monthAriaLabel);

            if (visibleMonth.equals(targetMonth)) {
                return;
            }

            By navigationButton = targetMonth.isAfter(visibleMonth) ? nextMonthButton : previousMonthButton;
            WebElement navigationElement = waitForVisible(navigationButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", navigationElement);
            wait.until(driver -> {
                List<WebElement> months = driver.findElements(calendarMonth);
                return !months.isEmpty()
                        && !monthAriaLabel.equals(months.get(0).getAttribute("aria-label"));
            });
        }
        throw new IllegalStateException("Не удалось открыть месяц " + targetMonth);
    }

    private YearMonth parseMonth(String ariaLabel) {
        Matcher matcher = MONTH_PATTERN.matcher(ariaLabel);
        if (!matcher.find()) {
            throw new IllegalStateException("Не удалось определить месяц календаря: " + ariaLabel);
        }
        return YearMonth.of(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
    }

    private By metroOption(String station) {
        return By.xpath("//button[contains(@class,'Order_SelectOption')][.//div[normalize-space()="
                + xpathLiteral(station) + "]]");
    }

    private By rentalPeriodOption(String period) {
        return By.xpath("//div[contains(@class,'Dropdown-option') and normalize-space()="
                + xpathLiteral(period) + "]");
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
