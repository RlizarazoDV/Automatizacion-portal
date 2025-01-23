package menuAfiliadoPos.Aportes_y_Cartera.UPC_Adicional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import Configuracion.basePage;
import java.time.Duration;
public class Pagos_Consistentes  extends basePage{
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;

    private final By linkPConsis = By.xpath("//*[contains(text(),'Pagos Consistentes')]");
    private final By respuesta = By.xpath("//*[contains(text(),'No hay resultados para mostrar')]");

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
    public void ingreso_Pagos_Consistentes() {
        WebElement Pago_Concis = waitMensaje.until(ExpectedConditions.presenceOfElementLocated(linkPConsis));
        Pago_Concis.click();
    }
    public String ComparaRespuesta(){
        WebElement respuestaElement = waitP.until(ExpectedConditions.visibilityOfElementLocated(respuesta));
        return respuestaElement.getText();
    }
}

