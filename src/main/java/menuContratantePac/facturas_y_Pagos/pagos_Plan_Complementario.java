package menuContratantePac.facturas_y_Pagos;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import Configuracion.basePage;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class pagos_Plan_Complementario extends basePage {
    private final WebDriverWait wait;
    private final By tabServicios = By.id("tabServicios");
    private final By linkContratantePac = By.linkText("Contratante PAC");
    private final By ingresoFacturasYP = By.xpath("//*[@id=\"j_id77\"]/table/tbody/tr[5]/td/div");
    private final By linkActualizarInformacionA = By.linkText("Pagos Plan Complementario");


    public pagos_Plan_Complementario(WebDriver webDriver) {

        super(webDriver);
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

    }

    public void ingresoContratantePAC() {
        WebElement tabServiciosElement = wait.until(ExpectedConditions.elementToBeClickable(tabServicios));
        tabServiciosElement.click();

        WebElement linkContraPac = wait.until(ExpectedConditions.elementToBeClickable(linkContratantePac));
        linkContraPac.click();
    }


    public void clickPagosPlanComplementario() {
        WebElement linkFacturas = wait.until(ExpectedConditions.elementToBeClickable(ingresoFacturasYP));
        linkFacturas.click();

        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(linkActualizarInformacionA));
        link.click();
    }

    public boolean isTooManyRedirectsErrorDisplayed() {
        // Verificar si aparece un error de demasiadas redirecciones
        return webDriver.getTitle().contains("ERR_TOO_MANY_REDIRECTS");
    }
    public boolean isServiceUnavailableErrorDisplayed() {
        // Verificar si aparece un error 503 de servicio temporalmente no disponible
        return webDriver.getTitle().contains("503 Service Temporarily Unavailable");
    }


    public boolean isPageLoaded(String expectedUrl) {
        // Espera hasta que la URL de la página cambie a la esperada
        return wait.until(ExpectedConditions.urlContains(expectedUrl));
    }

}


