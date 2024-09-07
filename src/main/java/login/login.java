package login;
import Configuracion.basePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class login extends basePage
{
    private final WebDriverWait wait;
    private final WebDriverWait waitP;
    private final By campoUsuarioLogin = By.id("loginForm:id");
    private final By campoContrasenaLogin = By.id("loginForm:clave");
    private final By botonIngresar = By.id("loginForm:loginButton");
    private final By botonSalirDos = By.id("loginForm:saliendo1");
    private final By botonSalir = By.id("headerForm:exitLink");
    private final By validacion = By.xpath("//span[contains(., '¡Bienvenido!')]");


    public login(WebDriver wbDriver)
    {
        super(wbDriver);
        this.wait = new WebDriverWait(wbDriver,Duration.ofSeconds(10));
        this.waitP = new WebDriverWait(wbDriver,Duration.ofSeconds(20));
    }

    public void ingresoUsuario(String user)
    {
        waitP.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

        WebElement element =  wait.until(ExpectedConditions.elementToBeClickable(campoUsuarioLogin));
        element.click();
        element.clear();
        element.sendKeys(user);
    }
    public void ingresoContrasena(String datopass)
    {
        WebElement element = webDriver.findElement(campoContrasenaLogin);
        element.click();
        element.clear();
        element.sendKeys(datopass);
    }
    public void botonIngresar()
    {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(botonIngresar));
        element.click();

    }
    public String validarlogin()
    {
      WebElement valid = waitP.until(ExpectedConditions.visibilityOfElementLocated(validacion));
      String valida = valid.getText();

        return valida;
    }

    //
    public void cerrarSesion()
    {

        try
        {
            // Intenta hacer clic en el primer botón
            WebElement btnSalir = wait.until(ExpectedConditions.elementToBeClickable(botonSalir));
            btnSalir.click();
        }
        catch (Exception e)
        {
            // Si hay una excepción al hacer clic en el primer botón, intenta con el segundo botón
            WebElement btnSalirDos = wait.until(ExpectedConditions.elementToBeClickable(botonSalirDos));
            btnSalirDos.click();
        }
    }
}
