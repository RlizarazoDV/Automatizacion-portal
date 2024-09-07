package Configuracion;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;

import java.nio.file.Paths;

public class FirefoxDriverFactory {
    public static WebDriver createFirefoxDriver(String downloadFilepath) {
        System.setProperty("webdriver.gecko.driver","src/test/resources/webDriver/geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();

        if (downloadFilepath != null) {
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("browser.download.folderList", 2);
            profile.setPreference("browser.download.dir", Paths.get(downloadFilepath).toAbsolutePath().toString());
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
            options.setProfile(profile);
        }


        return new FirefoxDriver(options);
    }
}
