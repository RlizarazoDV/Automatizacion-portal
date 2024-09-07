package menuContratantePac.certificaciones;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import Configuracion.basePage;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class certificacion_Valor_Pagado extends basePage {

    private final WebDriverWait wait;
    private final WebDriverWait waitP;
    private final By tabServicios = By.id("tabServicios");
    private final By linkContratantePac = By.linkText("Contratante PAC");
    private final By linkCertificaciones = By.xpath("//*[@id=\"j_id77\"]/table/tbody/tr[7]/td/div");
    private final By linkCertificadoValorPagado= By.linkText("Certificado Valor Pagado");
    private final By seleccionAño =By.id("j_id112:paramYears");
    private final By ContratoV = By.id("j_id112:j_id165:0:j_id168");
    private final By aceptar =By.id("j_id112:j_id189");

    private final By errorMessage = By.xpath("//*[@id='main-message']/p");

    public certificacion_Valor_Pagado(WebDriver webDriver) {
        super(webDriver);

        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        this.waitP = new WebDriverWait(webDriver, Duration.ofSeconds(20));

    }

    public void ingresoContratantePAC()
    {
        WebElement tabServiciosElement = wait.until(ExpectedConditions.elementToBeClickable(tabServicios));
        tabServiciosElement.click();

        WebElement linkContraPac = wait.until(ExpectedConditions.elementToBeClickable(linkContratantePac));
        linkContraPac.click();
        WebElement linkcertificaciones  = wait.until(ExpectedConditions.elementToBeClickable(linkCertificaciones));
        linkcertificaciones.click();
    }
    public void ingresoCertificaciones (String año)
    {

        WebElement linkcertificacionValor  = wait.until(ExpectedConditions.elementToBeClickable(linkCertificadoValorPagado));
        linkcertificacionValor.click();

        Select linkCertificadoValorPa = new Select(wait.until(ExpectedConditions.elementToBeClickable(seleccionAño)));
        linkCertificadoValorPa.selectByVisibleText(año);

    }
    public void CertificadoValorPagado()
    {
        WebElement Contrato = waitP.until(ExpectedConditions.elementToBeClickable(ContratoV));
        Contrato.click();

        WebElement botonAceptar =wait.until(ExpectedConditions.elementToBeClickable(aceptar));
        botonAceptar.click();
    }
    public void switchToNewTab() {
        String originalWindow = webDriver.getWindowHandle();
        List<String> windowHandles = new ArrayList<>(webDriver.getWindowHandles());

        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(originalWindow)) {
                webDriver.switchTo().window(windowHandle);
                break;
            }
        }
    }


    public boolean isPageLoaded(String expectedUrl) {

        return wait.until(ExpectedConditions.urlContains(expectedUrl));
    }
    public boolean isErrorDisplayed() {
        try {
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return errorElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }



}
