package menuIPS.radicacion_Digital;
import Configuracion.basePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;

public class consulta_Inconsistencia_RIPS extends basePage
{
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitL;


    private final  By ingresoInconsistencia = By.xpath("//*[contains(text(),'Consultar Inconsistencia RIPS')]");
    private final By error = By.xpath("//div[contains(text(),'ha tardado demasiado tiempo en responder.')]");


    public consulta_Inconsistencia_RIPS(WebDriver webDriver) {
        super(webDriver);

        this.wait = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(java.util.NoSuchElementException.class);

        this.waitL = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(java.util.NoSuchElementException.class);

    }

    public void ingresoInconsistenciasRips() {

        WebElement linkCargaArchivo  = wait.until(ExpectedConditions.elementToBeClickable(ingresoInconsistencia));
        linkCargaArchivo.click();


    }

    public String  cargaInconsistenciaRips() {
        switchToIframeById("ifWindows");
        WebElement linkmensaError  = wait.until(ExpectedConditions.visibilityOfElementLocated(error));
        String mensajeEx = linkmensaError.getText();
        return  mensajeEx;

    }
    private void switchToIframeById(String iframeId)
    {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id(iframeId)));
    }
}

