package ConfiguracionTest;
import Configuracion.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import java.io.File;

public class baseTest
{
    private static final String URL = "https://wildflyqa.famisanar.com.co:7481/Portal/home.jspx";
    protected static WebDriver driver;

    public baseTest()
    {

        System.out.println("BaseTest Thread-id: " + Thread.currentThread().getState());
    }

    @BeforeSuite(alwaysRun = true)
    public void setUpSuite()
    {
        System.out.println("setUpSuite Thread-id: " + Thread.currentThread().getState());
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(String browser ) throws Exception
    {
        System.out.println("setUp Thread-id: " + Thread.currentThread().getState());

        driver = WebDriverFactory.getWebDriver(browser);
        driver.get(URL);
        driver.manage().window().maximize();
    }
    public static WebDriver getWebDriver()
    {
        return driver;
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown()
    {
        if (driver != null)
        {
            driver.quit();
        }
    }

}