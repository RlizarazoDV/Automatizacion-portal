package menuEmpleador.afiliaciones;
import Configuracion.basePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class afiliados_Por_Ips extends basePage {
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;

    private final By linkafliados_Por_Ips  =  By.xpath("//*[contains(text(),'Afiliados por IPS')]");
    private final By linkEsta =  By.xpath("//*[contains(text(),'CENTRO MEDICO COLSUBSIDIO CALLE 26')]//following::a[1]");
    private final By ipsPrimaria= By.xpath("//*[contains(text(),'CENTRO MEDICO COLSUBSIDIO CALLE 26')]");


    public afiliados_Por_Ips(WebDriver webDriver) {
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
    public void ingreso_afiliados_Por_Ips(){

        WebElement linkEA = wait.until(ExpectedConditions.elementToBeClickable(linkafliados_Por_Ips));
        linkEA.click();

    }
    public void consulta_afiliados_Por_Ips(){

        WebElement linkEstadisticas = wait.until(ExpectedConditions.elementToBeClickable(linkEsta));
        linkEstadisticas.click();


    }
    public String  resultado(){
        WebElement resultado = waitP.until(ExpectedConditions.visibilityOfElementLocated(ipsPrimaria));
        String result = resultado.getText();
        return result;
    }



}
