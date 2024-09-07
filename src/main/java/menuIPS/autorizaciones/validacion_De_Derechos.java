package menuIPS.autorizaciones;

import Configuracion.basePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class validacion_De_Derechos extends basePage {
    private final FluentWait<WebDriver> wait;

    private final FluentWait<WebDriver> waitL;

    private final By linkAutorizaciones = By.xpath("//*[@id=\"j_id79\"]/table/tbody/tr[1]/td/div");
    private final By linkValidacionDeDerechos = By.linkText("Validación de Derechos (Estado Afiliación)");
    private final By linkValidacionEstadoAfiliacionT = By.xpath("//*[@id=\"ConsultarEstadoAfiliacionFORM:tipoIdCmb\"]");
    private final By linkValidacionEstadoAfiliacionI = By.xpath("//*[@id=\"ConsultarEstadoAfiliacionFORM:idTxt\"]");


    private final By botonAcep = By.id("ConsultarEstadoAfiliacionFORM:cmdautorizar");

    private final By mensaje = By.id("j_id35:j_id48");

    private final By errormensaje = By.xpath("//*[@id=\"sub-frame-error-details\"]");



    public validacion_De_Derechos(WebDriver webDriver)
    {
        super(webDriver);
        this.wait = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);



        this.waitL = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(35))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
    }
    public void ingresoAutorizaciones()
    {
        WebElement linkAuth  = wait.until(ExpectedConditions.elementToBeClickable(linkAutorizaciones));
        linkAuth.click();

    }

    public void ingresoValidacionDeDerechos(String TipoId,String ID)
    {

        waitForPageLoad();
        WebElement linkVal  = wait.until(ExpectedConditions.elementToBeClickable(linkValidacionDeDerechos));
        linkVal.click();

        switchToIframeById("ifAuto");

        selectByVisibleTextWithRetry(linkValidacionEstadoAfiliacionT,TipoId);

        WebElement  Id  = wait.until(ExpectedConditions.elementToBeClickable(linkValidacionEstadoAfiliacionI));
        Id.sendKeys(ID);

        WebElement Aceptar  = wait.until(ExpectedConditions.elementToBeClickable(botonAcep));
        Aceptar.click();


    }
    public void selectByVisibleTextWithRetry(By selectLocator, String visibleText)
    {
        int attempts = 0;
        boolean success = false;

        while (attempts < 3 && !success)
        {
            try {
                WebElement selectElement = waitL.until(ExpectedConditions.elementToBeClickable(selectLocator));
                Select select = new Select(selectElement);

                boolean optionExists = select.getOptions().stream()
                        .anyMatch(option -> option.getText().equals(visibleText));
                if (optionExists)
                {
                    select.selectByVisibleText(visibleText);
                    success = true;
                } else

                {
                    throw new NoSuchElementException("Option not found: " + visibleText);
                }
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                attempts++;
                try
                {
                    Thread.sleep(3000); // Espera 3 segundos antes de intentar de nuevo
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (!success)
        {
            throw new NoSuchElementException("Could not locate option with text: " + visibleText + " after multiple attempts");
        }
    }

    private void waitForPageLoad()
    {
        new WebDriverWait(webDriver, Duration.ofSeconds(30)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }
    private void switchToIframeById(String iframeId)
    {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id(iframeId)));
    }

    public String comparaExitoso()
    {
        WebElement mensajeexito = wait.until(ExpectedConditions.visibilityOfElementLocated(mensaje));
        String mensajeEx = mensajeexito.getText();

        return  mensajeEx;
    }

    public String compara()
    {
        WebElement mensajeError = wait.until(ExpectedConditions.visibilityOfElementLocated(errormensaje));
        String mensaje = mensajeError.getText();

        return mensaje;
    }

}
