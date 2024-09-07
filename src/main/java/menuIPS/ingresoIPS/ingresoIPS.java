package menuIPS.ingresoIPS;

import Configuracion.basePage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class ingresoIPS extends basePage {

    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final By tabServicios = By.id("//img[contains(@class, 'iceGphImg tabServicios') and contains(@src, '/Portal/imgs/tab_servicios_1.jpg?v=1724853903068')]");
    private final By IPs = By.linkText("IPS");
    private final By selectIPS = By.id("j_id114:ips");
    private final By selectSucursal = By.id("j_id114:sucIps");
    private final By botonAceptar = By.id("j_id114:acceptButton");
    private final By mensaje = By.id("prmID");



    public ingresoIPS(WebDriver webDriver) {
        super(webDriver);
        this.wait = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        this.waitP = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        this.waitL = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(35))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
    }

    public void ingresoIPS(String Ips, String SucIps) {
        try {
            WebElement tabServiciosElement = wait.until(ExpectedConditions.elementToBeClickable(tabServicios));
            tabServiciosElement.click();

            WebElement ingresoIPS = waitP.until(ExpectedConditions.elementToBeClickable(IPs));
            ingresoIPS.click();


            selectByVisibleTextWithRetry(selectIPS, Ips);

            // Espera adicional para asegurarse de que selectSucursal esté completamente cargado
            wait.until(ExpectedConditions.presenceOfElementLocated(selectSucursal));
            waitL.until(ExpectedConditions.elementToBeClickable(selectSucursal));

            selectByVisibleTextWithRetry(selectSucursal, SucIps);

            WebElement botonACEP = waitL.until(ExpectedConditions.elementToBeClickable(botonAceptar));
            botonACEP.click();

        } catch (Exception e) {
            System.out.println("Error during ingresoIPS: " + e.getMessage());
        }
    }

    private void selectByVisibleTextWithRetry(By selectLocator, String visibleText) {
        int attempts = 0;
        boolean success = false;

        while (attempts < 3 && !success)
        {
            try
            {
                Select selectElement = new Select(waitL.until(ExpectedConditions.elementToBeClickable(selectLocator)));
                boolean optionExists = selectElement.getOptions().stream()
                        .anyMatch(option -> option.getText().equals(visibleText));
                if (optionExists)
                {
                    selectElement.selectByVisibleText(visibleText);
                    success = true;
                } else {
                    throw new NoSuchElementException("Option not found: " + visibleText);
                }
            } catch (StaleElementReferenceException | NoSuchElementException e)
            {
                attempts++;
                try
                {
                    Thread.sleep(2000); // Espera 2 segundos antes de intentar de nuevo
                } catch (InterruptedException ie)
                {
                    Thread.currentThread().interrupt(); // Restablecer el estado de interrupción
                }
            }
        }
        if (!success)
        {
            throw new NoSuchElementException("Could not locate option with text: " + visibleText + " after multiple attempts");
        }
    }
    public String compara()
    {
        WebElement valid = waitP.until(ExpectedConditions.visibilityOfElementLocated(mensaje));
        String valida = valid.getText();

        return valida;
    }

}
