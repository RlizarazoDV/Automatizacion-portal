package menuIPS.licencias_E_Incapacidades;

import Configuracion.basePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;
public class licencias_E_incapacidades extends basePage {
    private final FluentWait<WebDriver> wait;
    private final  By ingresoLicencias = By.xpath("//*[contains(text(),'Licencias e Incapacidades')]");
    private final By valida = By.className("option70");

    public licencias_E_incapacidades(WebDriver webDriver) {
        super(webDriver);

        this.wait = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(java.util.NoSuchElementException.class);

    }
    public void ingresoLicenciasEIncapacidades() {
        WebElement linkAfilia = wait.until(ExpectedConditions.elementToBeClickable(ingresoLicencias));

        WebElement linkvalida = wait.until(ExpectedConditions.visibilityOfElementLocated(valida));
        linkvalida.isDisplayed();
    }
}
