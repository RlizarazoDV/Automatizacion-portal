package menuAfiliadoPos.movilidad_A_Subsidiado;

import Configuracion.basePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class descargar_Formulario_Movilidad extends basePage
{

    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;

    private final By tabServicios = By.xpath("//img[contains(@class, 'iceGphImg tabServicios')and contains(@src, '/Portal/imgs/tab_servicios_1')]");
    private final By linkAfiliadoP = By.linkText("Afiliado POS");
    private final By opcionMovilidadAlSubsidiado = By.xpath("//div[contains(@class, 'handPointer')and contains(text(), 'Movilidad a')]");
    private final By linkDescargarFormulario = By.linkText("Descargar Formulario Movilidad");
    private final By mensaError  = By.xpath("//div[contains(@class,'icon-generic')]");
    private final By iFrame  = By.xpath("//*[@id=\"tabla_contenido_panel\"]/tbody/tr[2]/td/iframe");




    public descargar_Formulario_Movilidad(WebDriver webDriver) {
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

    public void ingresarMenuAfiliadoPos()
    {
        WebElement tabServiciosElement = wait.until(ExpectedConditions.elementToBeClickable(tabServicios));
        tabServiciosElement.click();

        WebElement linkEmpElement = wait.until(ExpectedConditions.elementToBeClickable(linkAfiliadoP));
        linkEmpElement.click();

    }
    public void Descargar_Formulario(){
        WebElement opcionMovElement = wait.until(ExpectedConditions.elementToBeClickable(opcionMovilidadAlSubsidiado));
        opcionMovElement.click();
        WebElement opcionDescFormulario = waitP.until(ExpectedConditions.visibilityOfElementLocated(linkDescargarFormulario));
        opcionDescFormulario.click();
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
