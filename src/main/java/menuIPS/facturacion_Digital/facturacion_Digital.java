package menuIPS.facturacion_Digital;
import Configuracion.basePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;

public class facturacion_Digital extends basePage {
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitL;

    private final  By ingresofacturacion = By.xpath("//*[contains(text(),'Facturación Digital')]");
    private final  By ingresoRegistroFactura= By.xpath("//*[contains(text(),'Registro Factura Digital')]");
    private final By error = By.xpath("//div[contains(text(),'ha tardado demasiado tiempo en responder.')]");
    private final By iframe = By.id("ifAuto");


    public facturacion_Digital(WebDriver webDriver) {
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
    public void ingresoFacturaciondigital() {

        WebElement linkfacturacion = wait.until(ExpectedConditions.elementToBeClickable(ingresofacturacion));
        linkfacturacion.click();


    }
    public void ingresoCargaArchivosRips() {

        WebElement linkCargaArchivo  = wait.until(ExpectedConditions.elementToBeClickable(ingresoRegistroFactura));
        linkCargaArchivo.click();

    }
    public boolean frame(){

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("ifAuto")));
        WebElement linkiframe  = wait.until(ExpectedConditions.elementToBeClickable(iframe));
        return linkiframe.isDisplayed();

    }


}