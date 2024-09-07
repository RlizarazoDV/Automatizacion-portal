package Configuracion;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.util.HashMap;
import java.util.Map;

public class EdgeDriverFactory
{
    public static WebDriver createEdgeDriver(String downloadFilepath)
    {
        System.setProperty("webdriver.edge.driver","src/test/resources/webDriver/msedgedriver.exe");

        EdgeOptions options = new EdgeOptions();
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.addArguments("--remote-allow-origins=*");
        options.setCapability("ignore-certificate-errors", true);
        if (downloadFilepath != null) {
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_settings.popups", 0);
            prefs.put("download.default_directory", downloadFilepath);
            options.setExperimentalOption("prefs", prefs);
        }



        return new EdgeDriver(options);
    }
}
