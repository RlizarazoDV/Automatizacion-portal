package Configuracion;
import org.openqa.selenium.WebDriver;


public class WebDriverFactory {
    public static WebDriver getWebDriver(String browser,String downloadFilepath) {
        switch (browser.toLowerCase()) {
            case "chrome":
                return ChromeDriverFactory.createChromeDriver(downloadFilepath);
            case "firefox":
                return FirefoxDriverFactory.createFirefoxDriver(downloadFilepath);
            case "edge":
                return EdgeDriverFactory.createEdgeDriver(downloadFilepath);
            default:
                throw new IllegalArgumentException("Tipo de navegador no válido: " + browser);
        }

    }
    public static WebDriver getWebDriver(String browser)
    {
        return getWebDriver(browser, null);
    }
    }

