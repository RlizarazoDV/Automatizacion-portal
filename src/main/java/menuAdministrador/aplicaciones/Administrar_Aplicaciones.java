package menuAdministrador.aplicaciones;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import Configuracion.basePage;


import java.time.Duration;

import java.util.Set;

public class Administrar_Aplicaciones extends basePage {


    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;

    private final By tabServicios = By.xpath("//img[contains(@class, 'iceGphImg tabServicios') and contains(@src, '/Portal/imgs/tab_servicios_1.')]");
    private final By linkAdministrador = By.linkText("Administrador");
    private final By linkaplicaciones = By.xpath("//*[contains(text(),'Aplicaciones')]");
    private final By linkAdministrarAplicaciones = By.linkText("Administrar Aplicaciones");


    private final By botoncrear = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src, '/Portal/imgs/btnCrear.gif')]");

    private final By linknombre = By.xpath("//*[contains(text(),'Nombre:')]//following::input[1]");
    private final By linkDescripcion = By.xpath("//*[contains(text(),'Descripción:')]//following::textarea[1]");
    private final By servidorInput = By.xpath("//label[contains(text(),'Servidor:')]/following::input[1]");
    private final By rutaInput = By.xpath("//label[contains(text(),'Ruta:')]/following::input[1]");
    private final By botonaceptar  = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src, '/Portal/imgs/btnAceptar.gif')]");
    private final By linkmensaje = By.xpath("//*[contains(text(),'Creación exitosa')]");



    public Administrar_Aplicaciones(WebDriver webDriver) {
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
                .withTimeout(Duration.ofSeconds(35))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(java.util.NoSuchElementException.class);

    }
    public void ingresarMenuAdministrador() {
        WebElement tabServiciosElement = wait.until(ExpectedConditions.elementToBeClickable(tabServicios));
        tabServiciosElement.click();

        WebElement linkAdminElement = wait.until(ExpectedConditions.elementToBeClickable(linkAdministrador));
        linkAdminElement.click();
    }


    public void aplicaciones() {

        WebElement linkAplicaciones = wait.until(ExpectedConditions.elementToBeClickable(linkaplicaciones));
        linkAplicaciones.click();
        WebElement linkADAplicaciones = wait.until(ExpectedConditions.elementToBeClickable(linkAdministrarAplicaciones));
        linkADAplicaciones.click();
    }
    public void escogeaplicaciones() {


        WebElement linkAmodiciarApp = wait.until(ExpectedConditions.elementToBeClickable(botoncrear));
        linkAmodiciarApp.click();

    }
     public void ingresa_Modificacion(String nombre,String descripcion,String servidor,String ruta ){
         WebElement linkNombre = wait.until(ExpectedConditions.visibilityOfElementLocated(linknombre));
         linkNombre.sendKeys(nombre);

         WebElement linkDesc = wait.until(ExpectedConditions.visibilityOfElementLocated(linkDescripcion));
         linkDesc.sendKeys(descripcion);
         WebElement linkservidor = wait.until(ExpectedConditions.visibilityOfElementLocated(servidorInput));
         linkservidor.sendKeys(servidor);
         WebElement linkruta = wait.until(ExpectedConditions.visibilityOfElementLocated(rutaInput));
         linkruta.sendKeys(ruta);

         WebElement botonM = wait.until(ExpectedConditions.elementToBeClickable(botonaceptar));
         botonM.click();

     }
    public String ComparaRespuesta(){
        WebElement respuestaElement = waitP.until(ExpectedConditions.visibilityOfElementLocated(linkmensaje));
        return respuestaElement.getText();
    }



}
