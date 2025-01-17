package menuEmpleador.afiliaciones;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import Configuracion.basePage;
import java.time.Duration;
public class estadisticas_De_Afiliacion extends basePage{
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;

    private final By linkestadisticasSfiliacion  =  By.xpath("//*[contains(text(),'Estadísticas de Afiliación')]");
    private final By linkcotizantes =  By.xpath("//*[contains(text(),'Cotizantes')]//following::a[1]");
    private final By mensajeconf= By.xpath("//*[contains(text(),'Tipo Afiliados: Cotizantes')]");




    public estadisticas_De_Afiliacion(WebDriver webDriver) {
        super(webDriver);
        this.wait = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(java.util.NoSuchElementException.class);

        this.waitP = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(java.util.NoSuchElementException.class);
    }
    public void ingreso_Estadisticas_Afiliacion(){

        WebElement linkE = wait.until(ExpectedConditions.elementToBeClickable(linkestadisticasSfiliacion));
        linkE.click();

    }
    public void consulta_Estadisticas_Afiliacion(){

        WebElement linkcotizante = wait.until(ExpectedConditions.elementToBeClickable(linkcotizantes));
        linkcotizante.click();


    }
    public String  resultado(){
        WebElement resultado = waitP.until(ExpectedConditions.visibilityOfElementLocated(mensajeconf));
        String result = resultado.getText();
        return result;
    }


}
