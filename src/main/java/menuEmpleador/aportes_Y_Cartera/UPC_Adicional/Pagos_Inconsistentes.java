package menuEmpleador.aportes_Y_Cartera.UPC_Adicional;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import Configuracion.basePage;
import java.time.Duration;
public class Pagos_Inconsistentes extends basePage {
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;

    private final By linkPagosConsistentes =  By.xpath("//*[contains(text(),'Pagos Inconsistentes')]");
    private final By mensaje =  By.xpath("//*[contains(text(),'No hay resultados para mostrar')]");


    public Pagos_Inconsistentes(WebDriver webDriver) {
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

    public void ingreso_Pagos_InConsistentes(){

        WebElement linkPagosconsistentes = waitMensaje.until(ExpectedConditions.visibilityOfElementLocated(linkPagosConsistentes));
        linkPagosconsistentes.click();

        WebElement mensajef = waitL.until(ExpectedConditions.visibilityOfElementLocated(mensaje));
        mensajef.isDisplayed();
    }





}
