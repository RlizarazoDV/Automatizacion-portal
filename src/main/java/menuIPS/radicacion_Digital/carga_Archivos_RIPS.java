package menuIPS.radicacion_Digital;
import Configuracion.basePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;

public class carga_Archivos_RIPS extends basePage {
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitL;


    private final  By ingresoradicacion = By.xpath("//*[contains(text(),'Radicación Digital')]");
    private final  By ingresocargarArchivo= By.xpath("//*[contains(text(),'Cargar Archivos RIPS')]");
    private final By error = By.xpath("//div[contains(text(),'ha tardado demasiado tiempo en responder.')]");


    public carga_Archivos_RIPS(WebDriver webDriver) {
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
    public void ingresoradicacion() {

        WebElement linkradicacion = wait.until(ExpectedConditions.elementToBeClickable(ingresoradicacion));
        linkradicacion.click();


    }
    public void ingresoCargaArchivosRips() {

        WebElement linkCargaArchivo  = wait.until(ExpectedConditions.elementToBeClickable(ingresocargarArchivo));
        linkCargaArchivo.click();


    }

    public String  cargaArchivoRips() {
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
