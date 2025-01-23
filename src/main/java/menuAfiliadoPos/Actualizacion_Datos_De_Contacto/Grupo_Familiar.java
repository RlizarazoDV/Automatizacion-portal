package menuAfiliadoPos.Actualizacion_Datos_De_Contacto;
import Configuracion.basePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;
public class Grupo_Familiar extends basePage {

    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;



    private final By Grupo_Familiar = By.xpath("//*[contains(text(),'Grupo Familiar')]");
    private final By respuesta = By.xpath("//*[contains(text(),'Cabeza de Familia: CC 375596 JOSE ANTONIO DIAZ MARTINEZ')]");

    public Grupo_Familiar(WebDriver webDriver) {
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

    public void Grupo_Familiar() {
        WebElement opcionGrupoF = waitP.until(ExpectedConditions.visibilityOfElementLocated(Grupo_Familiar));
        opcionGrupoF.click();
    }
    public String ComparaRespuesta(){
        WebElement respuestaElement = waitP.until(ExpectedConditions.visibilityOfElementLocated(respuesta));
        return respuestaElement.getText();
    }

}
