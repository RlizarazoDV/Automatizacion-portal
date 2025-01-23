package menuAfiliadoPos.Certificaciones;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import Configuracion.basePage;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Set;
public class Afiliacion extends basePage {
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;

    private final By tabServicios = By.xpath("//img[contains(@class, 'iceGphImg tabServicios')and contains(@src, '/Portal/imgs/tab_servicios_1')]");
    private final By linkAfiliadoP = By.linkText("Afiliado POS");
    private final By linkCertificaciones  =  By.xpath("//*[contains(text(),'Certificaciones')]");
    private final By linkAfiliaciones= By.xpath("//*[contains(text(),'Afiliación')]");
    private final By checkboxAfiliado = By.xpath("//input[@class='iceSelBoolChkbx' and @name='form:allSelection']");
    private final By filaAfiliado = By.xpath("//tr[contains(@class,'filaSeleccionada')]");

    private final By botonImprimir = By.xpath("//input[@type='image' and contains(@src,'btnImprimir.gif')]");
    private final By errorTooManyRedirects = By.xpath("//*[contains(text(),'ERR_TOO_MANY_REDIRECTS')]");

    public Afiliacion(WebDriver webDriver) {
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

    public void ingresarMenuAfiliadoPos()
    {
        WebElement tabServiciosElement = wait.until(ExpectedConditions.elementToBeClickable(tabServicios));
        tabServiciosElement.click();

        WebElement linkEmpElement = wait.until(ExpectedConditions.elementToBeClickable(linkAfiliadoP));
        linkEmpElement.click();
    }
    public void ingreso_Certificaciones(){

        WebElement ingresoCertificaciones = waitL.until(ExpectedConditions.elementToBeClickable(linkCertificaciones));
        ingresoCertificaciones.click();
    }
    public void ingreso_Afiliaciones(){
        WebElement afiliaciones = waitL.until(ExpectedConditions.elementToBeClickable(linkAfiliaciones));
        afiliaciones.click();

    }

    public void seleccionarAfiliado() {

        WebElement checkboxTodos = wait.until(ExpectedConditions.elementToBeClickable(checkboxAfiliado));
        checkboxTodos.click();


        WebElement fila = wait.until(ExpectedConditions.elementToBeClickable(filaAfiliado));
        fila.click();
    }

    public void imprimirCertificacion() {
        WebElement imprimirBtn = wait.until(ExpectedConditions.elementToBeClickable(botonImprimir));
        imprimirBtn.click();

        // Cambia a la nueva ventana o pestaña
        String ventanaPrincipal = webDriver.getWindowHandle();
        Set<String> todasLasVentanas = webDriver.getWindowHandles();

        // Esperar un poco para que se abra la nueva ventana
        waitP.until(driver -> webDriver.getWindowHandles().size() > 1);

        for (String ventana : webDriver.getWindowHandles()) {
            if (!ventana.equals(ventanaPrincipal)) {
                webDriver.switchTo().window(ventana);
                break;
            }
        }
    }
    public boolean verificarDescarga() {
        try {
            // Esperar hasta que la URL contenga 'Portal/downloadservlet'
            waitP.until(ExpectedConditions.urlContains("Portal/downloadservlet"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}