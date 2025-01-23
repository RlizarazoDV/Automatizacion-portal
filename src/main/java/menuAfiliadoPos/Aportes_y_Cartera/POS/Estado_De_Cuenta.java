package menuAfiliadoPos.Aportes_y_Cartera.POS;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import Configuracion.basePage;
import java.time.Duration;

public class Estado_De_Cuenta extends basePage{
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;

    private final By tabServicios = By.xpath("//img[contains(@class, 'iceGphImg tabServicios')and contains(@src, '/Portal/imgs/tab_servicios_1')]");
    private final By linkAfiliadoP = By.linkText("Afiliado POS");
    private final By linkAportesCartera  =  By.xpath("//*[contains(text(),'Aportes y Cartera')]");
    private final By linkPos = By.xpath("//*[@id=\"option24\"]/table/tbody/tr[1]/td[2]/div/p");
    private final By Estadocuenta =  By.xpath("//*[contains(text(),'Estado Cuenta')]");
    private final By mensaje =  By.xpath("//*[contains(text(),'(1-25) de 34 registros encontrados.')]");


    public Estado_De_Cuenta(WebDriver webDriver) {
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
    public void ingreso_Aportes_Cartera(){

        WebElement AportesCartera = waitL.until(ExpectedConditions.elementToBeClickable(linkAportesCartera));
        AportesCartera.click();
    }
    public void ingreso_POS (){
        WebElement Pos = waitL.until(ExpectedConditions.elementToBeClickable(linkPos));
        Pos.click();

    }
    public void ingreso_Estado_Cuenta () {
        WebElement EstadoCuenta = waitMensaje.until(ExpectedConditions.presenceOfElementLocated(Estadocuenta));
        EstadoCuenta.click();

        WebElement mensajef = waitL.until(ExpectedConditions.visibilityOfElementLocated(mensaje));
        mensajef.isDisplayed();
    }


}
