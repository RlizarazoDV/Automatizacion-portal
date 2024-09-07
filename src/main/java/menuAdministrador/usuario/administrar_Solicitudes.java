package menuAdministrador.usuario;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import Configuracion.basePage;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class administrar_Solicitudes extends basePage
{

    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;


    // ingresar a administrar solicitudes
    private final By linkAdministrarSolicitudes = By.linkText("Administrar Solicitudes");

    //Formulario Administrar usuario
    private final  By tipoIdentificacion =  By.id("SearchPetitionsForm:tipoIdentificacion");
    private final By identificacion = By.id("SearchPetitionsForm:identificacion");
    private final By estadoSolicitud = By.id("SearchPetitionsForm:petitionStatus");
    private final By botonAceptar = By.id("SearchPetitionsForm:searchButton");
    private final By linkRespuesta = By.xpath("//span[contains(., 'No hay resultados para mostrar')]");
    private final By tabla = By.xpath("//label[contains(@class,'iceOutLbl') and contains(text(),'Fecha Revisión') ]");

    public administrar_Solicitudes(WebDriver webDriver)
    {
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
    }

    public void ingresoAdministrarSolicitudes()
    {
        WebElement administrarUsuario =  wait.until(ExpectedConditions.elementToBeClickable(linkAdministrarSolicitudes));
        administrarUsuario.click();
    }

    //Metodo para llenar los datos de usuario y realizar la busqueda
    public void formularioAdministrarSolicitudes(String tipoid,String numid,String estado) {
        Select tipoId = new Select(wait.until(ExpectedConditions.elementToBeClickable(tipoIdentificacion)));
        tipoId.selectByVisibleText(tipoid);
        waitL.until(ExpectedConditions.presenceOfElementLocated(identificacion));

        selectByVisibleTextWithRetry(identificacion,numid);

        Select EstadoSolicitud = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(estadoSolicitud)));
        EstadoSolicitud.selectByVisibleText(estado);

        WebElement botonAceptarS = webDriver.findElement(botonAceptar);
        botonAceptarS.click();
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
                        throw new NoSuchElementException("Campo sigue deshabilitado después de intentar habilitarlo.");
                    }
                }

                // Si el campo está habilitado, proceder a enviar el texto
                System.out.println("El campo está habilitado. Enviando texto...");
                identificacionU = wait.until(ExpectedConditions.elementToBeClickable(selectLocator));
                identificacionU.sendKeys(visibleText);
                success = true; // Si todo salió bien, marcar como exitoso

            } catch (StaleElementReferenceException | NoSuchElementException e) {
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
            throw new NoSuchElementException("No se pudo habilitar el campo o localizar la opción con texto: " + visibleText + " después de múltiples intentos.");
        }
    }
    public void validarTabla() {

        try {
            // Esperar hasta que el elemento esté presente en el DOM y visible
            WebElement elementoTabla = wait.until(ExpectedConditions.visibilityOfElementLocated(tabla));

            if (elementoTabla != null) {
                // Imprimir que se encontró el elemento y realizar la acción adicional
                System.out.println("El elemento de la tabla está presente. ");




            }
        } catch (TimeoutException e) {
            // Si no se encuentra el elemento dentro del tiempo de espera, lanzar excepción o manejarlo como quieras
            System.out.println("El elemento de la tabla no apareció.");

        }

    }


    public String compararRespuesta()
    {
        WebElement mensajeRespuesta = wait.until(ExpectedConditions.visibilityOfElementLocated(linkRespuesta));
        String mensajeResp=mensajeRespuesta.getText();


        return mensajeResp;
    }
}
