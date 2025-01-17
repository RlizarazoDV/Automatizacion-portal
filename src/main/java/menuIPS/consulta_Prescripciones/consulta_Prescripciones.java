package menuIPS.consulta_Prescripciones;
import Configuracion.basePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;
public class consulta_Prescripciones extends basePage{
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitL;


    private final  By ingresoConsultaPres = By.xpath("//*[contains(text(),'Consulta Prescripciones')]");
    private final  By ingresoConsultarPres= By.xpath("//*[contains(text(),'Consultar Prescripciones')]");
    private final By error = By.xpath("//div[contains(text(),'ha tardado demasiado tiempo en responder.')]");




    public consulta_Prescripciones(WebDriver webDriver) {
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
    public void ingresoPrescripciones() {

        WebElement linkCPrescripciones  = wait.until(ExpectedConditions.elementToBeClickable(ingresoConsultaPres));
        linkCPrescripciones.click();

        WebElement linkConsultarPres = wait.until(ExpectedConditions.elementToBeClickable(ingresoConsultarPres));
        linkConsultarPres.click();



    }
    public String  cargaPrescripciones(){

        switchToIframeById("ifAuto");
        WebElement linkmensaError  = wait.until(ExpectedConditions.visibilityOfElementLocated(error));
        String mensajeEx = linkmensaError.getText();
        return  mensajeEx;

    }
    private void switchToIframeById(String iframeId)
    {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id(iframeId)));
    }
}
