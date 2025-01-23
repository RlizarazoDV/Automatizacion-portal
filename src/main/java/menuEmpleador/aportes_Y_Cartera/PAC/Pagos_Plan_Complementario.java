package menuEmpleador.aportes_Y_Cartera.PAC;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import Configuracion.basePage;
import java.time.Duration;
import java.util.Set;
public class Pagos_Plan_Complementario  extends basePage {

    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;

    private final By tabServicios = By.xpath("//img[contains(@class, 'iceGphImg tabServicios')and contains(@src, '/Portal/imgs/tab_servicios_1')]");
    private final By linkEmpleador = By.linkText("Empleador");
    private final By linkAportesCartera  =  By.xpath("//*[contains(text(),'Aportes y Cartera')]");
    private final By linkPac = By.xpath("//*[@id=\"option59\"]/table/tbody/tr[5]/td[2]/div/p");
    private final By linkPagosPlanComplementario = By.xpath("//a[contains(text(),'Pagos Plan Complementario')]");
    private final By errorTooManyRedirects = By.xpath("//*[contains(text(),'503 Service Temporarily Unavailable')]");

    public Pagos_Plan_Complementario(WebDriver webDriver) {
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
    public void ingresoempleador(){
        WebElement tabServiciosElement = wait.until(ExpectedConditions.elementToBeClickable(tabServicios));
        tabServiciosElement.click();

        WebElement linkEmpElement = wait.until(ExpectedConditions.elementToBeClickable(linkEmpleador));
        linkEmpElement.click();


    }

    public void ingreso_Aportes_Cartera(){

        WebElement AportesCartera = waitL.until(ExpectedConditions.elementToBeClickable(linkAportesCartera));
        AportesCartera.click();
    }
    public void ingreso_Pac(){
        WebElement pac = waitL.until(ExpectedConditions.elementToBeClickable(linkPac));
        pac.click();

    }

    public String ingreso_Pagos_Plan_Complementario() {
        // Guarda la ventana actual
        String ventanaPrincipal = webDriver.getWindowHandle();

        // Hace clic en el enlace de "Pagos Plan Complementario"
        WebElement pagosPlanCompLink = wait.until(ExpectedConditions.elementToBeClickable(linkPagosPlanComplementario));
        pagosPlanCompLink.click();

        // Espera a que se abra una nueva ventana o pestaña
        Set<String> todasLasVentanas = webDriver.getWindowHandles();
        for (String ventana : todasLasVentanas) {
            if (!ventana.equals(ventanaPrincipal)) {
                // Cambia a la nueva ventana
                webDriver.switchTo().window(ventana);
                break;
            }
        }

        // Verifica si aparece el error de demasiadas redirecciones
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(errorTooManyRedirects));
            return "Error: Se detectó 'ERR_TOO_MANY_REDIRECTS' en la página de destino.";
        } catch (Exception e) {

            if (webDriver.getCurrentUrl().contains("https://test1.e-collect.com/app_eCollectAgentV2/secure/userExternalLogin.aspx")) {
                return "Navegación exitosa a la página de Pagos Plan Complementario.";
            } else {
                return "Error inesperado al acceder a la página de Pagos Plan Complementario.";
            }
        }
    }
}
