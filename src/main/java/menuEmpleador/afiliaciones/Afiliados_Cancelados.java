package menuEmpleador.afiliaciones;
import Configuracion.basePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class Afiliados_Cancelados extends basePage {
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;

    private final By linkAfiliadosC= By.linkText("Afiliados Cancelados");
    private final By fechaini= By.xpath("//*[contains(text(),'Fecha Inicial')]//following::input[2]");
    private final By mes= By.xpath("//*[contains(text(),'Fecha Inicial')]//following::select[1]");
    private final By año= By.xpath("//*[contains(text(),'Fecha Inicial')]//following::select[2]");
    private final By fechafin= By.xpath("//*[contains(text(),'Fecha Final')]//following::input[2]");
    private final By mesfin= By.xpath("//*[contains(text(),'Fecha Final')]//following::select[1]");
    private final By añofin= By.xpath("//*[contains(text(),'Fecha Final')]//following::select[2]");
    private final By boton = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src,'/Portal/imgs/btnAceptar.gif')]");
    private final By respuesta = By.xpath("//*[contains(text(),'Total Cancelados')]");




    public Afiliados_Cancelados(WebDriver webDriver) {
        super(webDriver);

        this.wait = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        this.waitP = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(30))  // Aumentar tiempo de espera
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);

        this.waitL = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(35))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        this.waitMensaje = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(40))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
    }
    private void waitForPageLoadComplete(int timeoutInSeconds) {
        new WebDriverWait(webDriver, Duration.ofSeconds(timeoutInSeconds)).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public void Afiliados_Cancelados() {
        WebElement opcionCPagos = waitP.until(ExpectedConditions.visibilityOfElementLocated(linkAfiliadosC));
        opcionCPagos.click();
        waitForPageLoadComplete(40);
    }
    public void fechaInicio(String Dia, String Mes, String Año) {
        int attempts = 0;
        boolean success = false;

        waitForPageLoadComplete(40);
        waitP.until(ExpectedConditions.visibilityOfElementLocated(fechaini));
        WebElement linkfechaIni = waitMensaje.until(ExpectedConditions.elementToBeClickable(fechaini));
        linkfechaIni.click();

        waitForPageLoadComplete(40);
        Select selectAño = new Select(waitMensaje.until(ExpectedConditions.elementToBeClickable(año)));
        selectAño.selectByVisibleText(Año);

        while (attempts < 3 && !success) {
            try {
                waitForPageLoadComplete(40);
                Select selectMes = new Select(waitMensaje.until(ExpectedConditions.elementToBeClickable(mes)));
                selectMes.selectByVisibleText(Mes);

                // Confirmar que el mes seleccionado es el esperado antes de continuar
                WebElement selectedMonth = selectMes.getFirstSelectedOption();
                if (!selectedMonth.getText().equals(Mes)) {
                    System.out.println("Mes no seleccionado correctamente, reintentando...");
                    attempts++;
                    continue; // Reinicia si el mes no es el correcto
                }

                WebElement linkdia = waitMensaje.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[contains(@class,'iceOutTxt') and contains(text(),'" + Dia + "')]")
                ));
                linkdia.click();
                success = true;
            } catch (StaleElementReferenceException e) {
                System.out.println("Elemento obsoleto, reintentando... (" + attempts + ")");
                attempts++;
            }
        }

        if (!success) {
            throw new RuntimeException("No se pudo seleccionar la fecha inicial después de 3 intentos.");
        }
    }

    public void fechaFin(String Diafin, String Mesfin, String Añofin) {
        boolean success = false;
        int attempts = 0;

        waitForPageLoadComplete(40);
        waitP.until(ExpectedConditions.visibilityOfElementLocated(fechafin));
        WebElement linkfechafin = waitMensaje.until(ExpectedConditions.elementToBeClickable(fechafin));
        linkfechafin.click();

        waitForPageLoadComplete(40);
        Select selectAñoFin = new Select(waitMensaje.until(ExpectedConditions.elementToBeClickable(añofin)));
        selectAñoFin.selectByVisibleText(Añofin);

        while (attempts < 3 && !success) {
            try {
                waitForPageLoadComplete(40);
                Select selectMesFin = new Select(waitMensaje.until(ExpectedConditions.elementToBeClickable(mesfin)));
                selectMesFin.selectByVisibleText(Mesfin);

                // Confirmar que el mes seleccionado es el esperado antes de continuar
                WebElement selectedMonth = selectMesFin.getFirstSelectedOption();
                if (!selectedMonth.getText().equals(Mesfin)) {
                    System.out.println("Mes fin no seleccionado correctamente, reintentando...");
                    attempts++;
                    continue; // Reinicia si el mes no es el correcto
                }

                WebElement linkdiafin = waitMensaje.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[contains(@class,'iceOutTxt') and contains(text(),'" + Diafin + "')]")
                ));
                linkdiafin.click();
                success = true;
            } catch (StaleElementReferenceException e) {
                System.out.println("Elemento obsoleto, reintentando... (" + attempts + ")");
                attempts++;
            }
        }

        if (!success) {
            throw new RuntimeException("No se pudo seleccionar la fecha final después de 3 intentos.");
        }
    }

    public void aceptar() {
        WebElement botonAceptar = waitP.until(ExpectedConditions.elementToBeClickable(boton));
        botonAceptar.click();
    }

    public String ComparaRespuesta(){
        WebElement respuestaElement = waitP.until(ExpectedConditions.visibilityOfElementLocated(respuesta));
        return respuestaElement.getText();
    }


}
