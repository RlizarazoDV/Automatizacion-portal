package menuEmpleador.movilidad_A_Subsidiado;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import Configuracion.basePage;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class descargar_Formulario extends basePage  {
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;
    private final By tabServicios = By.xpath("//img[contains(@class, 'iceGphImg tabServicios') and contains(@src, '/Portal/imgs/tab_servicios_1.jpg?v=1724853903068')]");
    private final By linkEmpleador = By.linkText("Empleador");
    private final By opcionMovilidadAlSubsidiado = By.xpath("//div[contains(@class, 'handPointer')and contains(text(), 'Movilidad a Subsidiado')]");
    private final By linkDescargarFormulario = By.linkText("Descargar Formulario");
    private final By mensaError  = By.xpath("//div[contains(@class,'icon-generic')]");
    private final By iFrame  = By.xpath("//*[@id=\"tabla_contenido_panel\"]/tbody/tr[2]/td/iframe");

    private final By respuestaError  = By.xpath("//*[contains(text(),'Es posible que la página web')]");
    private final By campoIdentificacion = By.xpath("//*[contains(text(),'Identificación')]//following::input[1]");
    private final By botonAceptarSolicitarU = By.xpath("//*[contains(text(),'Tipo Identificación')]//following::input[2]");


    public descargar_Formulario(WebDriver webDriver) {
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

    public void ingresarMenuEmpleador()
    {
        WebElement tabServiciosElement = wait.until(ExpectedConditions.elementToBeClickable(tabServicios));
        tabServiciosElement.click();

        WebElement linkEmpElement = wait.until(ExpectedConditions.elementToBeClickable(linkEmpleador));
        linkEmpElement.click();

        WebElement opcionMovElement = wait.until(ExpectedConditions.elementToBeClickable(opcionMovilidadAlSubsidiado));
        opcionMovElement.click();
    }
    public void Descargar_Formulario(){
        WebElement opcionDescFormulario = wait.until(ExpectedConditions.elementToBeClickable(linkDescargarFormulario));
        opcionDescFormulario.click();
    }

    public void  error()
    {
        WebElement iframe = waitP.until(ExpectedConditions.visibilityOfElementLocated(iFrame));
        webDriver.switchTo().frame(iframe);
        WebElement mensajeError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sub-frame-error")));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(mensajeError).perform();

    }







}
