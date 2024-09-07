package menuEmpleador.datos_Del_Empleador;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import Configuracion.basePage;


import java.time.Duration;

public class Actualizar_Informacion_Afiliado extends basePage{
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;
    private final By tabServicios = By.xpath("//img[contains(@class, 'iceGphImg tabServicios') and contains(@src, '/Portal/imgs/tab_servicios_1.jpg?v=1724853903068')]");
    private final By linkEmpleador = By.linkText("Empleador");
    private final By linkDatosEmpleador = By.xpath("//*[contains(text(),'Datos del Empleador')]");
    private final By opcionActualizarEmpleador = By.linkText("Actualizar Información Empleador");
    private final By RepresentanteLegal = By.xpath("//*[contains(text(),'Representante Legal')]//following::input[1]");
    private final By botonAceptar = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src,'/Portal/imgs/btnAceptar.gif')]");
    private final By mensaje  = By.xpath("//*[contains(text(),'Información del Empleador Actualizada')]");



    public Actualizar_Informacion_Afiliado(WebDriver webDriver) {
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

        WebElement linkDatos = wait.until(ExpectedConditions.elementToBeClickable(linkDatosEmpleador));
        linkDatos.click();


    }

    public void actualizarInformacion(String repre){

        WebElement linkActualizarInfo= wait.until(ExpectedConditions.elementToBeClickable( opcionActualizarEmpleador));
        linkActualizarInfo.click();
        WebElement representanteL = wait.until(ExpectedConditions.visibilityOfElementLocated(RepresentanteLegal));
        representanteL.clear();
        representanteL.sendKeys(repre);
        WebElement Aceptar= wait.until(ExpectedConditions.elementToBeClickable( botonAceptar));
        Aceptar.click();

    }
   public String comparacion(){
       WebElement mensajeExitoso = waitMensaje.until(ExpectedConditions.visibilityOfElementLocated(mensaje));
       String confirmaAsigFil = mensajeExitoso.getText();

       return confirmaAsigFil;
   }

}
