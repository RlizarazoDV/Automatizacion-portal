package menuIPS.cartera;
import Configuracion.basePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;

public class pagos_A_La_Ips extends basePage {

    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;

    private final By Cartera = By.xpath("//*[contains(text(),'Cartera')]");
    private final By pagosIps = By.xpath("//*[contains(text(),'Pagos a la IPS')]");
    private final By error = By.xpath("//*[contains(text(),'La página que estas buscando no existe')]");


    public pagos_A_La_Ips(WebDriver webDriver) {
        super(webDriver);

        this.wait = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(java.util.NoSuchElementException.class);

        this.waitP = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(java.util.NoSuchElementException.class);

        this.waitL = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(35))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(java.util.NoSuchElementException.class);

        this.waitMensaje = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(40))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(java.util.NoSuchElementException.class);
    }
    public void ingreso_Cartera() {
        WebElement linkcartera  = waitMensaje.until(ExpectedConditions.elementToBeClickable(Cartera));
        linkcartera.click();
    }
    public boolean ingreso_Pagos_Ips(){
        WebElement linkPagosIps  = waitMensaje.until(ExpectedConditions.elementToBeClickable(pagosIps));
        linkPagosIps.click();
        switchToIframeById("ifWindows");
        WebElement linkmensaError  = waitMensaje.until(ExpectedConditions.visibilityOfElementLocated(error));
       return  linkmensaError.isDisplayed();

    }
    private void switchToIframeById(String iframeId)
    {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id(iframeId)));
    }

}
