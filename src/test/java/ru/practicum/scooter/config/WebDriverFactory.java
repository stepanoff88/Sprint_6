package ru.practicum.scooter.config;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Locale;

public final class WebDriverFactory {

    private WebDriverFactory() {
    }

    public static WebDriver createDriver() {
        String browser = System.getProperty("browser", "firefox").toLowerCase(Locale.ROOT);
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        WebDriver driver;
        switch (browser) {
            case "chrome":
                driver = new ChromeDriver(chromeOptions(headless));
                break;
            case "firefox":
                driver = new FirefoxDriver(firefoxOptions(headless));
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser
                        + ". Use -Dbrowser=chrome or -Dbrowser=firefox.");
        }

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        if (headless) {
            driver.manage().window().setSize(new Dimension(1440, 1000));
        } else {
            driver.manage().window().maximize();
        }
        return driver;
    }

    private static ChromeOptions chromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1440,1000", "--no-sandbox", "--disable-dev-shm-usage");
        if (headless) {
            options.addArguments("--headless=new", "--disable-gpu");
        }
        return options;
    }

    private static FirefoxOptions firefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1440", "--height=1000");
        if (headless) {
            options.addArguments("-headless");
        }
        return options;
    }
}
