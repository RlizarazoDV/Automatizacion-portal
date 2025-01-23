package menuEmpleador.aportes_Y_Cartera.UPC_Adicional;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import Configuracion.basePage;
import java.time.Duration;
public class Pagos_Consistentes extends basePage {

    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;

    private final By tabServicios = By.xpath("//img[contains(@class, 'iceGphImg tabServicios') and contains(@src, '/Portal/imgs/tab_servicios_1.')]");
    private final By linkEmpleador = By.linkText("Empleador");
    private final By linkAportesCartera  =  By.xpath("//*[contains(text(),'Aportes y Cartera')]");
    private final By linkUpc = By.xpath("//*[contains(text(),'UPC Adicional')]");
    private final By linkPagosConsistentes =  By.xpath("//*[contains(text(),'Pagos Consistentes')]");
    private final By mensaje =  By.xpath("//*[contains(text(),'La información que puede consultar corresponde a los últimos tres (3) años')]");


    public Pagos_Consistentes(WebDriver webDriver) {
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

    public void ingresoempleador(){
        WebElement tabServiciosElement = wait.until(ExpectedConditions.elementToBeClickable(tabServicios));
        tabServiciosElement.click();

        WebElement linkEmpElement = wait.until(ExpectedConditions.elementToBeClickable(linkEmpleador));
        linkEmpElement.click();


    }
    public void ingreso_Aportes_Cartera(){

        WebElement AportesCartera = waitL.until(ExpectedConditions.elementToBeClickable(linkAportesCartera));
        AportesCartera.click();
    }
    public void ingreso_UPC(){
        WebElement upc = waitL.until(ExpectedConditions.elementToBeClickable(linkUpc));
        upc.click();

    }
    public void ingreso_Pagos_Consistentes(){

        WebElement linkPagosconsistentes = waitMensaje.until(ExpectedConditions.visibilityOfElementLocated(linkPagosConsistentes));
        linkPagosconsistentes.click();

        WebElement mensajef = waitL.until(ExpectedConditions.visibilityOfElementLocated(mensaje));
        mensajef.isDisplayed();
    }

}

