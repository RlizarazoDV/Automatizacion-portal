package menuAdministrador.usuario;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import Configuracion.basePage;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.NoSuchElementException;

public class solicitar_Usuario extends basePage
{
    // Localizadores para los elementos del formulario de solicitud de usuario

    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;


    private final By tabServicios = By.xpath("//img[contains(@class, 'iceGphImg tabServicios') and contains(@src, '/Portal/imgs/tab_servicios_1.jpg?v=1724853903068')]");
    private final By linkAdministrador = By.linkText("Administrador");
    private final By opcionSolicitarUsuario = By.xpath("//div[@onclick=\"showOption('option1');\"]");
    private final By linkSolicitarUsuario = By.linkText("Solicitar Usuario");
    private final By listaTipoIdentificacion = By.xpath("//*[contains(text(),'Tipo Identificación')]//following::select[1]");
    private final By campoIdentificacion = By.xpath("//*[contains(text(),'Identificación')]//following::input[1]");
    private final By botonAceptarSolicitarU = By.xpath("//*[contains(text(),'Tipo Identificación')]//following::input[2]");

    // Localizadores para los campos de datos personales del usuario
    private final By campoPrimerNombre = By.xpath("//*[contains(text(),'Primer Nombre')]//following::input[1]");
    private final By campoSdoNombre = By.xpath("//*[contains(text(),'Segundo Nombre')]//following::input[1]");
    private final By campoPrimerApellido = By.xpath("//*[contains(text(),'Primer Apellido')]//following::input[1]");
    private final By campoSdoApellido = By.xpath("//*[contains(text(),'Segundo Apellido')]//following::input[1]");
    private final By campoEmail = By.xpath("//*[contains(text(),'e-mail')]//following::input[1]");
    private final By campoConfirmacionEmail = By.xpath("//*[contains(text(),'Confirmación de e-mail')]//following::input[1]");

    // Localizadores para los campos de asignación de perfil
    private final By campoGrupoUno =  By.xpath("//*[contains(text(),'Grupo Id 1')]//following::input[1]");
    private final By campoGrupoDos =  By.xpath("//*[contains(text(),'Grupo Id 2')]//following::input[1]");
    private final By campoGrupoTres =  By.xpath("//*[contains(text(),'Grupo Id 3')]//following::input[1]");
    private final By campoGrupoCuatro =  By.xpath("//*[contains(text(),'Grupo Id 4')]//following::input[1]");
    private final By listaAplicacion =  By.xpath("//*[contains(text(),'Id Aplicación')]//following::select[1]");
    private final By listaMenu =  By.xpath("//*[contains(text(),'Id Menú')]//following::select[1]");
    private final By botonAceptar =  By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src, '/Portal/imgs/btnAceptar.gif')]");
    private final By mensajeusuariocreado = By.cssSelector("ul#form\\:mens > li:nth-child(1) > span.iceMsgsInfo.mensajesInfo");
    private final By mensajeperfilAsignado = By.cssSelector("ul#form\\:mens > li:nth-child(2) > span.iceMsgsInfo.mensajesInfo");





    public solicitar_Usuario(WebDriver webDriver)
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

        this.waitMensaje = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(35))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(java.util.NoSuchElementException.class);
    }


    // Método para ingresar al menú de administrador
    public void ingresarMenuAdministrador()
    {
        WebElement tabServiciosElement = wait.until(ExpectedConditions.elementToBeClickable(tabServicios));
        tabServiciosElement.click();

        WebElement linkAdminElement = wait.until(ExpectedConditions.elementToBeClickable(linkAdministrador));
        linkAdminElement.click();

        WebElement opcionUsuarioElement = wait.until(ExpectedConditions.elementToBeClickable(opcionSolicitarUsuario));
        opcionUsuarioElement.click();
    }


    // Método para seleccionar la opción de solicitar usuario
    public void solicitarUsuario()
    {

        WebElement linkUsuarioElement = wait.until(ExpectedConditions.elementToBeClickable(linkSolicitarUsuario));
        linkUsuarioElement.click();
    }

    // Método para completar el formulario de solicitud de usuario
    public void llenarFormularioSolicitud(String tipoIdentificacion, String numIdentificacion)
    {
        Select tipoIdentificacionDropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(listaTipoIdentificacion)));
        tipoIdentificacionDropdown.selectByVisibleText(tipoIdentificacion);

        WebElement campoIdentificacionElement = webDriver.findElement(campoIdentificacion);
        campoIdentificacionElement.sendKeys(numIdentificacion);

        WebElement botonAceptarElement = webDriver.findElement(botonAceptarSolicitarU);
        botonAceptarElement.click();
    }

    // Métodos para completar los datos personales del usuario
    public void completarDatosPersonales(String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String email)
    {
        WebElement campoPrimerNombreElement = wait.until(ExpectedConditions.visibilityOfElementLocated(campoPrimerNombre));
        if (campoPrimerNombreElement.getAttribute("value").isEmpty()) {
            campoPrimerNombreElement.sendKeys(primerNombre);
        }

        WebElement campoSegundoNombreElement = webDriver.findElement(campoSdoNombre);
        if (campoSegundoNombreElement.getAttribute("value").isEmpty()) {
            campoSegundoNombreElement.sendKeys(segundoNombre);
        }

        WebElement campoPrimerApellidoElement = webDriver.findElement(campoPrimerApellido);
        if (campoPrimerApellidoElement.getAttribute("value").isEmpty()) {
            campoPrimerApellidoElement.sendKeys(primerApellido);
        }

        WebElement campoSegundoApellidoElement = webDriver.findElement(campoSdoApellido);
        if (campoSegundoApellidoElement.getAttribute("value").isEmpty()) {
            campoSegundoApellidoElement.sendKeys(segundoApellido);
        }

        WebElement campoEmailElement = webDriver.findElement(campoEmail);
        if (campoEmailElement.getAttribute("value").isEmpty()) {
            campoEmailElement.sendKeys(email);
        }

        WebElement campoConfirmacionEmailElement = webDriver.findElement(campoConfirmacionEmail);
        if (campoConfirmacionEmailElement.getAttribute("value").isEmpty()) {
            campoConfirmacionEmailElement.sendKeys(email);
        }
    }

    // Método para completar la asignación de perfil del usuario
    public void completarAsignacionPerfil(String idGrupo1, String idGrupo2, String idGrupo3, String idGrupo4, String aplicacion, String menu)
    {
        WebElement campoGrupoUnoElement = webDriver.findElement(campoGrupoUno);
        campoGrupoUnoElement.sendKeys(idGrupo1);

        WebElement campoGrupoDosElement = webDriver.findElement(campoGrupoDos);
        campoGrupoDosElement.sendKeys(idGrupo2);

        WebElement campoGrupoTresElement = webDriver.findElement(campoGrupoTres);
        campoGrupoTresElement.sendKeys(idGrupo3);

        WebElement campoGrupoCuatroElement = webDriver.findElement(campoGrupoCuatro);
        campoGrupoCuatroElement.sendKeys(idGrupo4);

        Select listaAplicacionElement = new Select(waitP.until(ExpectedConditions.visibilityOfElementLocated(listaAplicacion)));
        listaAplicacionElement.selectByVisibleText(aplicacion);

        waitP.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

        selectByVisibleTextWithRetry(listaMenu, menu);

    }

    // Método para hacer clic en el botón de aceptar

    public void botonAceptar()
    {
        WebElement botonAceptarElement =waitL.until(ExpectedConditions.elementToBeClickable(botonAceptar));
        botonAceptarElement.click();
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


    //Método para obtener los mensajes de confirmación
    public String[] validarRespuestas()
    {

         WebElement mensajeUsuarioCreado = waitMensaje.until(ExpectedConditions.visibilityOfElementLocated(mensajeusuariocreado));
         String mensajeusuarioCreado=mensajeUsuarioCreado.getText();

         WebElement mensajePerfilAsignado =waitMensaje.until(ExpectedConditions.visibilityOfElementLocated(mensajeperfilAsignado));
         String mensajePerfilCreado=mensajePerfilAsignado.getText();

         return  new String[]
                 {
                         mensajeusuarioCreado, mensajePerfilCreado
                 };
    }
}
