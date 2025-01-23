package menuEmpleador.aportes_Y_Cartera.POS;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.*;
import Configuracion.basePage;

import java.time.Duration;
import java.util.Set;

public class Certificacion_Planilla_Unica extends basePage {
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;

    private final By Planilla_Unica= By.xpath("//*[contains(text(),'Certificación planilla única')]");
    private final By valor_Recibido= By.xpath("//*[@id=\"j_id114:data:1\"]/td[1]");
    private final By boton = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src,'/Portal/imgs/btnImprimir.gif')]");
    private final By errorTooManyRedirects = By.xpath("//*[contains(text(),'No se puede acceder a este sitio web')]");


    public Certificacion_Planilla_Unica(WebDriver webDriver) {
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
    private void waitForPageLoadComplete(int timeoutInSeconds) {
        new WebDriverWait(webDriver, Duration.ofSeconds(timeoutInSeconds)).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }



    public void ingreso_Certificacion_Planilla_Unica() {
        WebElement pagosPlanillaU = waitP.until(ExpectedConditions.elementToBeClickable(Planilla_Unica));
        pagosPlanillaU.click();
        waitForPageLoadComplete(30);
    }


    public String Impresion_Planilla_Unica() {


        WebElement Valor_Recibido = waitL.until(ExpectedConditions.visibilityOfElementLocated(valor_Recibido));
        Valor_Recibido.click();
        waitForPageLoadComplete(30);

        WebElement Boton = waitL.until(ExpectedConditions.visibilityOfElementLocated(boton));
        Boton.click();
        waitForPageLoadComplete(30);
        wait.until(driver -> webDriver.getWindowHandles().size() > 1);
        String ventanaPrincipal = webDriver.getWindowHandle();

        Set<String> todasLasVentanas = webDriver.getWindowHandles();
        for (String ventana : todasLasVentanas) {
            if (!ventana.equals(ventanaPrincipal)) {

                webDriver.switchTo().window(ventana);
                break;
            }
        }

        try {
            wait.until(ExpectedConditions.urlContains("certipagos/muestrapdfcertipagos?"));
            wait.until(ExpectedConditions.presenceOfElementLocated(errorTooManyRedirects));
            return "Error: Se detectó No se puede acceder a este sitio web";
        } catch (Exception e) {

            if (webDriver.getCurrentUrl().contains("https://172.30.16.17:8473/certipagos/muestrapdfcertipagos?")) {
                return "Error: Se detectó No se puede acceder a este sitio web";
            } else {
                return "Error inesperado al acceder a la página de Certipagos.";
            }
        }
    }




}
