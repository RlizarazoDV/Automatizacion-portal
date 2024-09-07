package menuContratantePac.afiliaciones;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import Configuracion.basePage;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class grupo_Familiar  extends basePage
{
    private final WebDriverWait wait;
    private final WebDriverWait waitMensaje;

    private final By tabServicios = By.id("tabServicios");
    private By linkContratantePac = By.linkText("Contratante PAC");
    private By LinkAfiliaciones = By.xpath("//*[@id='j_id77']/table/tbody/tr[1]/td/div");
    private By LinkGrupoFamiliar = By.linkText("Grupo Familiar");
    private By compararmensa = By.xpath("//span[contains(., 'Contratante PAC:')]");



    public grupo_Familiar(WebDriver webDriver)
    {
        super(webDriver);
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        this.waitMensaje = new WebDriverWait(webDriver, Duration.ofSeconds(30));
    }

    public void ingresarMenuContratantePac()
    {
        WebElement tabServiciosElement = wait.until(ExpectedConditions.elementToBeClickable(tabServicios));
        tabServiciosElement.click();

        WebElement linkContraPac = wait.until(ExpectedConditions.elementToBeClickable(linkContratantePac));
        linkContraPac.click();
    }

    // Método para seleccionar la opción de solicitar usuario
    public void afiliacionesGrupoFamiliar()
    {
        WebElement SubMenuAf = wait.until(ExpectedConditions.elementToBeClickable(LinkAfiliaciones));
        SubMenuAf.click();

        WebElement linkGrupoF = wait.until(ExpectedConditions.elementToBeClickable(LinkGrupoFamiliar));
        linkGrupoF.click();
    }
    public String Compara()
    {
        WebElement mensajeRespuesta = wait.until(ExpectedConditions.visibilityOfElementLocated(compararmensa));
        String mensajeResp=mensajeRespuesta.getText();
        return mensajeResp;
    }
}
