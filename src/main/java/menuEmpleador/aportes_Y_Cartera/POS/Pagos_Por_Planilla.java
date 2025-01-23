package menuEmpleador.aportes_Y_Cartera.POS;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import Configuracion.basePage;
import java.time.Duration;

public class Pagos_Por_Planilla extends basePage {
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;


    private final By PagosPorPlanilla = By.xpath("//*[contains(text(),'Pagos por Planilla')]");
    private final By mensaje = By.xpath("//*[contains(text(),'Señor Aportante: Su pago se verá reflejado una vez el')]");


    public Pagos_Por_Planilla(WebDriver webDriver) {
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



    public void Ingreso_Pagos_Por_Planilla() {
        WebElement LinkPagoPorPlanilla = waitMensaje.until(ExpectedConditions.visibilityOfElementLocated(PagosPorPlanilla));
        LinkPagoPorPlanilla.click();

        WebElement mensajef = waitL.until(ExpectedConditions.visibilityOfElementLocated(mensaje));
        mensajef.isDisplayed();
    }
}