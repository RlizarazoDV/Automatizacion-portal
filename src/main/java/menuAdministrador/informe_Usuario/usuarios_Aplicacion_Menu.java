package menuAdministrador.informe_Usuario;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import Configuracion.basePage;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;
public class usuarios_Aplicacion_Menu extends basePage {
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;


    private final By linkUsuarioPorAplicacionPorMenu= By.linkText("Usuarios por Aplicación por Menú");
    private final By linkaplicacion = By.xpath("//*[contains(text(),'Aplicación:')]//following::select[1]");
    private final By linkbotonAceptar = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src, '/Portal/imgs/btnAceptar.gif')]");
    private final By aplicacionAdmin = By.xpath("//*[contains(text(),'Administrador')and contains(@class,'iceOutTxt')]");
    private final By linkbotonDetalle = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src, '/Portal/imgs/btnVerDetalle.gif')]");
    private final By imprimirbutton = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src, '/Portal/imgs/btnImprimir.gif')]");

    public usuarios_Aplicacion_Menu(WebDriver webDriver) {
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
                .withTimeout(Duration.ofSeconds(35))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(java.util.NoSuchElementException.class);

    }
    public void ingresarAplicacionPorMenu() {

        WebElement linkAplicacionMenu = wait.until(ExpectedConditions.elementToBeClickable(linkUsuarioPorAplicacionPorMenu));
        linkAplicacionMenu.click();
    }


    public void escogeAplicacion(String aplicacion) {

        Select opcionAplicacion = new Select(waitP.until(ExpectedConditions.elementToBeClickable(linkaplicacion)));
        opcionAplicacion.selectByVisibleText(aplicacion);

        WebElement linkbotonaplicacion= wait.until(ExpectedConditions.elementToBeClickable(linkbotonAceptar));
        linkbotonaplicacion.click();
    }


    public void SeleccinaFiltro() {

        WebElement linkEscogeFiltro= wait.until(ExpectedConditions.elementToBeClickable(aplicacionAdmin));
        linkEscogeFiltro.click();

        WebElement botonAceptarElement = webDriver.findElement(linkbotonDetalle);
        botonAceptarElement.click();
    }

    public void imprimirInforme() {


        String mainTab = webDriver.getWindowHandle();

        WebElement BotonImprimir = waitMensaje.until(ExpectedConditions.elementToBeClickable(imprimirbutton));
        BotonImprimir.click();

        Set<String> allWindows = webDriver.getWindowHandles();


        for (String window : allWindows) {
            if (!window.equals(mainTab)) {
                webDriver.switchTo().window(window);
                System.out.println("urlimpresion");

                break;
            }
        }
            System.out.println("Nueva URL: " + webDriver.getCurrentUrl());
        }

    }


