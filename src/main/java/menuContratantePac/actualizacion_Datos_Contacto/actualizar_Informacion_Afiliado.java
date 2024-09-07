package menuContratantePac.actualizacion_Datos_Contacto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import Configuracion.basePage;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class actualizar_Informacion_Afiliado  extends basePage {

    private final WebDriverWait wait;
    private final WebDriverWait waitl;

    private final By tabServicios = By.id("tabServicios");
    private final By linkContratantePac = By.linkText("Contratante PAC");
    private final By ingresoAC = By.xpath("//*[@id=\"j_id77\"]/table/tbody/tr[3]/td/div");
    private final By linkActualizarInformacionA = By.linkText("Contratante PAC");
    private final By compararespuesta=By.id("sub-frame-error-details");


    public actualizar_Informacion_Afiliado(WebDriver webDriver)
    {

        super(webDriver);
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        this.waitl = new WebDriverWait(webDriver,Duration.ofSeconds(20));

    }


    public void ingresoContratantePAC()
    {
        WebElement tabServiciosElement = wait.until(ExpectedConditions.elementToBeClickable(tabServicios));
        tabServiciosElement.click();

        WebElement linkContraPac = wait.until(ExpectedConditions.elementToBeClickable(linkContratantePac));
        linkContraPac.click();
    }

    public void IngresoDatosContacto()
    {
        WebElement SubMenuAC = wait.until(ExpectedConditions.elementToBeClickable(ingresoAC));
        SubMenuAC.click();

        WebElement linkActInfoA = wait.until(ExpectedConditions.elementToBeClickable(linkActualizarInformacionA));
        linkActInfoA.click();
    }
    public String Compara()
    {
        WebElement mensajeRespuesta = waitl.until(ExpectedConditions.visibilityOfElementLocated(compararespuesta));
        String mensajeResp=mensajeRespuesta.getText();
        return mensajeResp;
    }
}
