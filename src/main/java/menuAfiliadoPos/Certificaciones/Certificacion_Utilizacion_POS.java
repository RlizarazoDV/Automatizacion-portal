package menuAfiliadoPos.Certificaciones;
import Configuracion.basePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class Certificacion_Utilizacion_POS extends basePage {
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;

    private final By linkInformacionA= By.linkText("Certificado Utilización POS");
    private final By iFrame  = By.xpath("//*[contains(@id,'ifAuto')]");

    public Certificacion_Utilizacion_POS(WebDriver webDriver) {
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
    public void Certificado_Utilizacion_Pos(){
        WebElement opcionCertificadoU = waitP.until(ExpectedConditions.visibilityOfElementLocated(linkInformacionA));
        opcionCertificadoU.click();

    }

    public boolean error(){
        try {
            WebElement iframe = waitMensaje.until(ExpectedConditions.presenceOfElementLocated(iFrame));
            webDriver.switchTo().frame(iframe);

            WebElement mensajeError = waitMensaje.until(ExpectedConditions.presenceOfElementLocated(By.id("sub-frame-error")));
            return mensajeError.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {

            return false;

        }
    }
}
