package menuIPS.autorizaciones;
import Configuracion.basePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;
public class consulta_direccionamiento extends basePage {
    private final FluentWait<WebDriver> wait;

    private final FluentWait<WebDriver> waitL;
    private final By linkAutorizaciones = By.xpath("//*[@id=\"j_id79\"]/table/tbody/tr[1]/td/div");
    private final By linkConsultaDireccionamiento= By.linkText("Legalización Direccionamiento");
    private final By linkConsultaDi = By.id("j_id35:PreAutorizacionTxt");
    private final By verificarC= By.id("j_id35:cmdverificar");
    private final By mensajeER= By.xpath("//*[@id=\"glbmsg\"]/li/span");

    public consulta_direccionamiento(WebDriver webDriver)
    {
        super(webDriver);
        this.wait = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);



        this.waitL = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(35))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
    }
    public void ingreso_Autorizaciones()

    {
        WebElement linkAuth  = wait.until(ExpectedConditions.elementToBeClickable(linkAutorizaciones));
        linkAuth.click();

    }
    public void ingreso_Consulta_Direccionamiento(String NumeroD )
    {
        WebElement linkConsultaD = wait.until(ExpectedConditions.elementToBeClickable(linkConsultaDireccionamiento));
        linkConsultaD.click();

        switchToIframeById("ifAuto");

        WebElement linkConsulta = wait.until(ExpectedConditions.elementToBeClickable(linkConsultaDi));
        linkConsulta.sendKeys(NumeroD);

        WebElement verificar  = wait.until(ExpectedConditions.elementToBeClickable(verificarC));
        verificar.click();
    }

    public String compara()
    {
        WebElement mensajeRespuesta = waitL.until(ExpectedConditions.visibilityOfElementLocated(mensajeER));
        String mensajeResp=mensajeRespuesta.getText();
        return mensajeResp;

    }

    private void switchToIframeById(String iframeId)
    {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id(iframeId)));
    }

}
