package menuIPS.autorizaciones;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import Configuracion.basePage;


import java.time.Duration;
public class anulacion_Autorizacion extends basePage{
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;
    private final By anulacionAutorizacion = By.linkText("Anulación Autorización");

    private final By iFrame  = By.xpath("//*[contains(@id,'ifAuto')]");

    public anulacion_Autorizacion(WebDriver webDriver) {
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

    public void ingreso_Anulacion_Autorizacion(){
        WebElement linkAnulacion = wait.until(ExpectedConditions.elementToBeClickable(anulacionAutorizacion));
        linkAnulacion.click();
    }

    public boolean error(){
        try {
            WebElement iframe = waitMensaje.until(ExpectedConditions.presenceOfElementLocated(iFrame));
            webDriver.switchTo().frame(iframe);

            WebElement mensajeError = waitMensaje.until(ExpectedConditions.presenceOfElementLocated(By.id("sub-frame-error")));
            return mensajeError.isDisplayed();
        } catch (TimeoutException | org.openqa.selenium.NoSuchElementException e) {

            return false;

        }
    }

}
