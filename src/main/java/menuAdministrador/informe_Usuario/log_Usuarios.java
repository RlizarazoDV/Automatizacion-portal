package menuAdministrador.informe_Usuario;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import Configuracion.basePage;


import java.time.Duration;

import java.util.Set;


public class log_Usuarios extends basePage {


    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;

    private final By tabServicios = By.xpath("//img[contains(@class, 'iceGphImg tabServicios') and contains(@src, '/Portal/imgs/tab_servicios_1.')]");
    private final By linkAdministrador = By.linkText("Administrador");
    private final By linkinformeUsuario = By.xpath("//*[contains(text(),'Informes Usuarios')]");
    private final By linkLogUsuario = By.linkText("Log Usuarios");
    private final  By tipoIdentificacion =  By.xpath("//*[contains(text(),'Usuario:')]//following::select[1]");
    private final By identificacion = By.xpath("//*[contains(text(),'Usuario:')]//following::input[1]");
    private final By botonAceptar = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src, '/Portal/imgs/btnAceptar.gif')]");
    private final By imprimirbutton = By.xpath("//a[contains(@href,'/Portal/downloadservlet')]");


    public log_Usuarios(WebDriver webDriver) {
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

    public void ingresarMenuAdministrador() {
        WebElement tabServiciosElement = wait.until(ExpectedConditions.elementToBeClickable(tabServicios));
        tabServiciosElement.click();

        WebElement linkAdminElement = wait.until(ExpectedConditions.elementToBeClickable(linkAdministrador));
        linkAdminElement.click();

        WebElement opcionUsuarioElement = wait.until(ExpectedConditions.elementToBeClickable(linkinformeUsuario));
        opcionUsuarioElement.click();
    }

    // Método para seleccionar la opción de solicitar usuario
    public void informeusuario() {

        WebElement linkUsuarioElement = wait.until(ExpectedConditions.elementToBeClickable(linkLogUsuario));
        linkUsuarioElement.click();
    }

    // Método para completar el formulario de solicitud de usuario
    public void llenarFormularioLogUsuario(String tipoIde, String numIde) {
        Select tipoIdentificacionDropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(tipoIdentificacion)));
        tipoIdentificacionDropdown.selectByVisibleText(tipoIde);

        waitP.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

        selectByVisibleTextWithRetry(identificacion, numIde);

        WebElement botonAceptarElement = webDriver.findElement(botonAceptar);
        botonAceptarElement.click();
    }

    public void imprimirInforme() {

        String mainTab = webDriver.getWindowHandle();

        WebElement botonImprimir = waitMensaje.until(ExpectedConditions.elementToBeClickable(imprimirbutton));
        botonImprimir.click();

        Set<String> allWindows = webDriver.getWindowHandles();

        // Cambia a la nueva ventana o pestaña
        for (String window : allWindows) {
            if (!window.equals(mainTab)) {
                webDriver.switchTo().window(window);
                System.out.println("urlimpresion");

                break;
            }
        }
        System.out.println("Nueva URL: " + webDriver.getCurrentUrl());
    }

    private void selectByVisibleTextWithRetry(By selectLocator, String visibleText)
    {
        int attempts = 0;
        boolean success = false;

        while (attempts < 3 && !success) {
            try {
                // Localiza el elemento
                WebElement identificacionU = waitP.until(ExpectedConditions.presenceOfElementLocated(selectLocator));
                String disabledAttribute = identificacionU.getAttribute("disabled");

                if (disabledAttribute != null) {
                    System.out.println("El campo está deshabilitado. Intentando habilitarlo...");

                    // Habilitar el campo utilizando JavaScript
                    ((JavascriptExecutor) webDriver).executeScript("arguments[0].removeAttribute('disabled');", identificacionU);

                    // Revalidar si el campo ahora está habilitado
                    identificacionU = waitP.until(ExpectedConditions.elementToBeClickable(selectLocator));
                    disabledAttribute = identificacionU.getAttribute("disabled");

                    // Si el campo sigue deshabilitado, lanzar excepción para intentar de nuevo
                    if (disabledAttribute != null) {
                        throw new org.openqa.selenium.NoSuchElementException("Campo sigue deshabilitado después de intentar habilitarlo.");
                    }
                }

                // Si el campo está habilitado, proceder a enviar el texto
                System.out.println("El campo está habilitado. Enviando texto...");
                identificacionU = wait.until(ExpectedConditions.elementToBeClickable(selectLocator));
                identificacionU.sendKeys(visibleText);
                success = true; // Si todo salió bien, marcar como exitoso

            } catch (StaleElementReferenceException | org.openqa.selenium.NoSuchElementException e) {
                attempts++;
                System.out.println("Intento fallido, reintentando... (" + attempts + "/3)");

                try {
                    Thread.sleep(2000); // Esperar 2 segundos antes de intentar de nuevo
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // Restablecer el estado de interrupción
                }
            }
        }

        if (!success) {
            throw new org.openqa.selenium.NoSuchElementException("No se pudo habilitar el campo o localizar la opción con texto: " + visibleText + " después de múltiples intentos.");
        }
    }

}