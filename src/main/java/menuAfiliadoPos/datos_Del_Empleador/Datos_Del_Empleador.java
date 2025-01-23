package menuAfiliadoPos.datos_Del_Empleador;
import Configuracion.basePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;
public class Datos_Del_Empleador extends basePage {
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;


    private final By tabServicios = By.xpath("//img[contains(@class, 'iceGphImg tabServicios') and contains(@src, '/Portal/imgs/tab_servicios_1.')]");
    private final By LinkAfiliadoPos = By.xpath("//*[contains(text(),'Afiliado POS')]");
    private final By datosEmp = By.xpath("//*[contains(text(),'Datos del Empleador')]");
    private final By valida = By.className("option50");

    public Datos_Del_Empleador(WebDriver webDriver) {
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

    public void Ingreso_Servicios_En_Linea(){
        WebElement linkServicios = wait.until(ExpectedConditions.elementToBeClickable(tabServicios));
        linkServicios.click();

        WebElement linkAfiliadoPos = wait.until(ExpectedConditions.elementToBeClickable(LinkAfiliadoPos));
        linkAfiliadoPos.click();

    }
    public void ingreso_Datos_Empleador() {
        WebElement linkDatosEmp = wait.until(ExpectedConditions.elementToBeClickable(datosEmp));
        linkDatosEmp.click();

        WebElement linkvalida = wait.until(ExpectedConditions.visibilityOfElementLocated(valida));
        linkvalida.isDisplayed();
    }
}


