package menuContratantePac.certificaciones;

import Configuracion.basePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class certificado_Utilizacion_Pac extends basePage {

    private final WebDriverWait wait;

    private final By linkCertificadoUtilizaciónPAC = By.linkText("Certificado Utilización PAC");
    private final By errorMensaje = By.id("sub-frame-error");

    public certificado_Utilizacion_Pac(WebDriver webDriver) {
        super(webDriver);
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    }


    public void ingresarCertificadoUtilizacionPac()
    {
        WebElement linkcedrtificadoUtilizacionpac  = wait.until(ExpectedConditions.elementToBeClickable(linkCertificadoUtilizaciónPAC));
        linkcedrtificadoUtilizacionpac.click();
    }

    public String mensaje()
    {
        WebElement mensajeError = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMensaje));
        String mensaje = mensajeError.getText();
        return mensaje;
    }

}