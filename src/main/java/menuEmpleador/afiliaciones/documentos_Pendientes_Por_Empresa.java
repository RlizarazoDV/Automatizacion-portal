package menuEmpleador.afiliaciones;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import Configuracion.basePage;
import java.time.Duration;
public class documentos_Pendientes_Por_Empresa extends basePage{

    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;

    private final By linkdocumentoEmpresa  =  By.xpath("//*[contains(text(),'Documentos Pendientes por Empresa')]");
    private final By idEmpleadoTable = By.xpath("//*[contains(text(),'Id Empleado')]");


    public documentos_Pendientes_Por_Empresa(WebDriver webDriver) {
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
    public void ingreso_Documentos_Pendientes_Por_Empresa(){

        WebElement linkD = wait.until(ExpectedConditions.elementToBeClickable(linkdocumentoEmpresa));
        linkD.click();
        WebElement Table = wait.until(ExpectedConditions.visibilityOfElementLocated(idEmpleadoTable));
        Table.isDisplayed();
    }

}
