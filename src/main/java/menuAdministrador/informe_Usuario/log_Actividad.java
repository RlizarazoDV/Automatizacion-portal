package menuAdministrador.informe_Usuario;
import org.openqa.selenium.*;
import Configuracion.basePage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class log_Actividad extends basePage{

    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;


    private final By linkLogActividad = By.linkText("Log Actividad");
    private final  By tipoIdentificacion =  By.xpath("//*[contains(text(),'Usuario:')]//following::select[1]");
    private final By identificacion = By.xpath("//*[contains(text(),'Usuario:')]//following::input[1]");
    private final By tipoNovedad = By.xpath("//*[contains(text(),'Tipo Novedad:')]//following::select[1]");

    // fecha inicio
    private final By fechaini=  By.xpath("//*[contains(text(),'Fecha Inicial')]//following::input[2]");
    private final By mes=  By.xpath("//*[contains(text(),'Fecha Inicial')]//following::select[1]");
    private final By año=  By.xpath("//*[contains(text(),'Fecha Inicial')]//following::select[2]");


    // fecha fin
    private final By fechafin=  By.xpath("//*[contains(text(),'Fecha Final')]//following::input[2]");
    private final By mesfin=  By.xpath("//*[contains(text(),'Fecha Final')]//following::select[1]");
    private final By añofin=  By.xpath("//*[contains(text(),'Fecha Final')]//following::select[2]");


    private final By botonAceptar = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src, '/Portal/imgs/btnAceptar.gif')]");

    private final By imprimirbutton = By.xpath("//a[contains(@href,'/Portal/downloadservlet')]");




    public log_Actividad(WebDriver webDriver )
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

    public void ingresarlogActividad()
    {
        WebElement linkLogAct = wait.until(ExpectedConditions.elementToBeClickable(linkLogActividad));
        linkLogAct.click();

    }


    public void llenarFormularioLogAct(String tipoIde, String numIde,String tipoNov)
    {

        Select tipoIdentificacionDropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(tipoIdentificacion)));
        tipoIdentificacionDropdown.selectByVisibleText(tipoIde);

        waitP.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

        selectByVisibleTextWithRetry(identificacion,numIde);

        waitP.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

        Select tipoNove= new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(tipoNovedad)));
        tipoNove.selectByVisibleText(tipoNov);

    }
    public void fechaInicio(String Dia,String Mes,String Año){
        int attempts = 0;
        boolean success = false;

        WebElement linkfechaIni = waitP.until(ExpectedConditions.elementToBeClickable(fechaini));
        linkfechaIni.click();
        Select linkaño = new Select(waitP.until(ExpectedConditions.elementToBeClickable(año)));
        linkaño.selectByVisibleText(Año);


        while (attempts < 3 && !success)
        {
            try {
                Select linkmes = new Select(waitP.until(ExpectedConditions.elementToBeClickable(mes)));
                linkmes.selectByVisibleText(Mes);
                WebElement linkdia = waitP.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'iceOutTxt') and contains(text(),'" + Dia + "')]")));
                linkdia.click();
                success = true;

            } catch (org.openqa.selenium.StaleElementReferenceException e)
            {
                System.out.println("Elemento obsoleto, reintentando... (" + attempts + ")");
                attempts++;
            }
        }
        if (!success)
        {
            throw new RuntimeException("No se pudo seleccionar la fecha inicial después de 3 intentos.");
        }

    }

    public void fechaFin(String Diafin,String Mesfin,String Añofin) {
        boolean success = false;
        int attempts = 0;
        waitP.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
        WebElement linkfechafin = wait.until(ExpectedConditions.elementToBeClickable(fechafin));
        linkfechafin.click();
        Select linkañofin = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(añofin)));
        linkañofin.selectByVisibleText(Añofin);
        waitP.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
        while (attempts < 3 && !success) {
            try {
                Select linkmesfin = new Select(waitP.until(ExpectedConditions.visibilityOfElementLocated(mesfin)));
                linkmesfin.selectByVisibleText(Mesfin);
                WebElement linkdiafin = waitL.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'iceOutTxt') and contains(text(),'" + Diafin + "')]")));
                linkdiafin.click();
                success = true;

            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                System.out.println("Elemento obsoleto, reintentando... (" + attempts + ")");
                attempts++;
            }
            if (!success) {
                throw new RuntimeException("No se pudo seleccionar la fecha inicial después de 3 intentos.");
            }
        }
    }

    public void botonIngresar()
    {

        WebElement BotonIng = wait.until(ExpectedConditions.elementToBeClickable(botonAceptar));
        BotonIng.click();

    }


    public void imprimirInforme()
    {

        String mainTab = webDriver.getWindowHandle();

        WebElement botonImprimir = waitL.until(ExpectedConditions.elementToBeClickable(imprimirbutton));
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
                identificacionU = waitP.until(ExpectedConditions.elementToBeClickable(selectLocator));
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
