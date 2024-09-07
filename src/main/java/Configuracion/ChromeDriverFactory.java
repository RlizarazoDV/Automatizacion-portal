package Configuracion;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class ChromeDriverFactory
{
    public static WebDriver createChromeDriver(String downloadFilepath)
    {
        System.setProperty("webdriver.chrome.driver","src/test/resources/webDriver/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches",new String[]{"enable-automation","load-extension"});
        options.addArguments("--remote-allow-origins=*");

        if (downloadFilepath != null) {
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_settings.popups", 0);
            prefs.put("download.default_directory", downloadFilepath);
            options.setExperimentalOption("prefs", prefs);
        }

        return new ChromeDriver(options);
    }
}
