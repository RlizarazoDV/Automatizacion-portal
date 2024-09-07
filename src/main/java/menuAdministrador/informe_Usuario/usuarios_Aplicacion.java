package menuAdministrador.informe_Usuario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import Configuracion.basePage;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class usuarios_Aplicacion extends basePage {

    private final WebDriverWait wait;
    private final WebDriverWait waitMensaje;

    private final By tabServicios = By.id("tabServicios");
    private final By linkAdministrador = By.linkText("Administrador");
    private final By linkinformeUsuario = By.xpath("//div[contains(., 'Informes Usuarios')]");
    private final By linkLogUsuario = By.linkText("Log Usuarios");
    private final By tipoIdentificacion = By.id("j_id112:tipoIdentificacion");
    private final By id = By.id("j_id112:identificacion");
    private final By buscarButton = By.id("j_id112:searchButton");
    private final By imprimirbutton = By.xpath("//a[@href='/Portal/downloadservlet']");

    public usuarios_Aplicacion(WebDriver webDriver) {
        super(webDriver);
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        this.waitMensaje = new WebDriverWait(webDriver, Duration.ofSeconds(30));
    }


    public void ingresarMenuAdministrador() {
        WebElement tabServiciosElement = wait.until(ExpectedConditions.elementToBeClickable(tabServicios));
        tabServiciosElement.click();

        WebElement linkAdminElement = wait.until(ExpectedConditions.elementToBeClickable(linkAdministrador));
        linkAdminElement.click();
    }

    // Método para seleccionar la opción de solicitar usuario
    public void informeusuario() {
        WebElement opcionUsuarioElement = wait.until(ExpectedConditions.elementToBeClickable(linkinformeUsuario));
        opcionUsuarioElement.click();

        WebElement linkUsuarioElement = wait.until(ExpectedConditions.elementToBeClickable(linkLogUsuario));
        linkUsuarioElement.click();
    }

    // Método para completar el formulario de solicitud de usuario
    public void llenarFormularioLogUsuario(String tipoIde, String numIde) {
        Select tipoIdentificacionDropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(tipoIdentificacion)));
        tipoIdentificacionDropdown.selectByVisibleText(tipoIde);

        WebElement campoIdentificacionElement = webDriver.findElement(id);
        campoIdentificacionElement.sendKeys(numIde);

        WebElement botonAceptarElement = webDriver.findElement(buscarButton);
        botonAceptarElement.click();
    }

    public void imprimirInforme() {
        String mainTab = webDriver.getWindowHandle();

        WebElement botonImprimir = waitMensaje.until(ExpectedConditions.elementToBeClickable(imprimirbutton));
        botonImprimir.click();


        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        Set<String> idVentanas = webDriver.getWindowHandles();

        for (String ventanaActual : idVentanas) {
            if (!ventanaActual.equalsIgnoreCase(mainTab)) {
                webDriver.switchTo().window(ventanaActual);


                wait.until(ExpectedConditions.urlContains("/Portal/downloadservlet"));


                WebElement pageTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Log de Usuario')]")));
                if (pageTitle.isDisplayed()) {
                    System.out.println("La página se ha abierto correctamente.");
                } else {
                    System.out.println("La página no se ha abierto correctamente.");
                }

            }
        }
    }
}
