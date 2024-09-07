package menuAdministrador.usuario;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import Configuracion.basePage;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class administrar_Usuario extends basePage {
    private final WebDriverWait wait;
    private final WebDriverWait waitP;
    private final WebDriverWait waitcomp;
    private final WebDriverWait waitL;
    // ingresar a administrar usuario
    private final By linkAdministrarUsuario = By.linkText("Administrar Usuarios");

    //Formulario Administrar usuario
    private final By tipoIdentificacion = By.xpath("//*[contains(text(),'Usuario')]//following::select[1]");
    private final By identificacion =  By.xpath("//*[contains(text(),'Usuario')]//following::select//following::input[1]");
    private final By botonAceptar = By.xpath("//*[contains(@src, '/Portal/imgs/btnAceptar.gif')]");
    private final By linkSeleccionarUsuario = By.xpath("//span[@class='iceOutTxt' and contains(@id, 'userTypeId')]");
    private final By botonModificar = By.xpath("//input[contains(@class,'iceCmdBtn') and contains(@src,'/Portal/imgs/btnModificar.gif')]");
    private final By botonRecordarContraseña = By.id("j_id112:rememberPassword");
    private final By botonCambiarEstado = By.id("j_id112:lock");
    //opcion modificar
    private final By campoModificarSapellido = By.xpath("//*[contains(text(),'Segundo Apellido')]//following::input[1]");

    private final By botonActualizar = By.xpath("//input[contains(@class,'iceCmdBtn') and contains(@src,'/Portal/imgs/btnActualizar.gif')]");
    private final By mensaje = By.xpath("//span[contains(., 'Actualización exitosa')]");


    public administrar_Usuario(WebDriver webDriver)
    {

        super(webDriver);
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        this.waitP = new WebDriverWait(webDriver, Duration.ofSeconds(40));
        this.waitcomp = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        this.waitL = new WebDriverWait(webDriver, Duration.ofSeconds(60));

    }

    // Metodo para ingresar a la opcion administrar usuario
    public void ingresoAdministrarUsuario() {
        WebElement administrarUsuario = wait.until(ExpectedConditions.visibilityOfElementLocated(linkAdministrarUsuario));
        administrarUsuario.click();
    }

    //Metodo para llenar los datos de usuario y realizar la busqueda
    public void formularioAdministrarUsuario(String tipoid, String id)
    {

        Select tipoId = new Select(wait.until(ExpectedConditions.elementToBeClickable(tipoIdentificacion)));
        tipoId.selectByVisibleText(tipoid);



        waitP.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

        selectByVisibleTextWithRetry(identificacion,id);

            waitP.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));



            WebElement botonAceptarS = waitcomp.until(ExpectedConditions.elementToBeClickable(botonAceptar));
            boolean clickSuccess = false;
            for (int i = 0; i < 3; i++) { // Intentar clic hasta 5 veces
                try {
                    botonAceptarS.click();
                    botonAceptarS.click();
                    // Esperar una condición que confirme que el clic ha tenido éxito
                    WebElement confirmacion = waitcomp.until(ExpectedConditions.visibilityOfElementLocated(linkSeleccionarUsuario));
                    if (confirmacion != null && confirmacion.isDisplayed()) {
                        clickSuccess = true;
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Intento de clic fallido: " + (i + 1));
                    // Reintentar localizar y hacer clic en el botón
                    botonAceptarS = waitcomp.until(ExpectedConditions.elementToBeClickable(botonAceptar));
                    botonAceptarS.click();
                }
            }

            if (!clickSuccess) {
                throw new RuntimeException("El clic en el botón de aceptar falló después de múltiples intentos.");
            }
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



    //Metodo para modificar el correo del usuario
    public void modificarUsuario(String segundoN)
    {

        waitP.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

      WebElement linkSelectU = waitP.until(ExpectedConditions.visibilityOfElementLocated(linkSeleccionarUsuario));
      linkSelectU.click();


      WebElement botonModificarU = waitP.until(ExpectedConditions.visibilityOfElementLocated(botonModificar));
      botonModificarU.click();

      WebElement campoModificarE = wait.until(ExpectedConditions.visibilityOfElementLocated(campoModificarSapellido));
      campoModificarE.clear();
      campoModificarE.sendKeys(segundoN);



      WebElement botonActualizarU =  wait.until(ExpectedConditions.visibilityOfElementLocated(botonActualizar));
      botonActualizarU.click();
    }

     public String compara()
     {
         waitL.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

         WebElement msjAcepFiltro2 = waitP.until(ExpectedConditions.visibilityOfElementLocated(mensaje));
         String confirmaAsigFil = msjAcepFiltro2.getText();

         return confirmaAsigFil;
     }
}
