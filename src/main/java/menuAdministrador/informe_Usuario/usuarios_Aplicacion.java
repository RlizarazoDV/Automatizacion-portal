package menuAdministrador.informe_Usuario;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import Configuracion.basePage;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class usuarios_Aplicacion extends basePage {

    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;

    private final By linkUsuariosPorAplicacion = By.linkText("Usuarios por aplicación");
    private final By linkAplicacion = By.xpath("//*[contains(text(),'Portal')and contains(@class,'iceOutTxt')]");
    private final By botonDetalle = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src, '/Portal/imgs/btnVerDetalle.gif')]");
    private final By linkfecha = By.xpath("//*[contains(text(),'Fecha')]");
    private final By botonIngresar = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src, '/Portal/imgs/btnAceptar.gif')]");
    // fecha inicio
    private final By fechaini=  By.xpath("//*[contains(text(),'Fecha Inicial')]//following::input[2]");
    private final By mes=  By.xpath("//*[contains(text(),'Fecha Inicial')]//following::select[1]");
    private final By año=  By.xpath("//*[contains(text(),'Fecha Inicial')]//following::select[2]");


    // fecha fin
    private final By fechafin=  By.xpath("//*[contains(text(),'Fecha Final')]//following::input[2]");
    private final By mesfin=  By.xpath("//*[contains(text(),'Fecha Final')]//following::select[1]");
    private final By añofin=  By.xpath("//*[contains(text(),'Fecha Final')]//following::select[2]");

    private final By botonAceptar = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src, '/Portal/imgs/btnAceptar.gif')]");

    private final By linkreporte = By.xpath("//*[contains(text(),'Reporte')and contains(@class,'iceSelOneRb radioVertical')]");
    private final By botonAceptar2 = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src, '/Portal/imgs/btnAceptar.gif')]");
    private final By botonImprimir = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src, '/Portal/imgs/btnImprimir.gif')]");



    public usuarios_Aplicacion(WebDriver webDriver) {
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


    public void ingresarUsuariosPorAplicacion() {
        WebElement linkUsuarioApli = waitP.until(ExpectedConditions.elementToBeClickable(linkUsuariosPorAplicacion));
        linkUsuarioApli.click();

    }


    public void ingresoAplicaciones() {

        WebElement opcionaplicaciones = waitP.until(ExpectedConditions.elementToBeClickable(linkAplicacion));
        opcionaplicaciones.click();

        WebElement BotonDetalle = waitP.until(ExpectedConditions.elementToBeClickable(botonDetalle));
        BotonDetalle.click();
    }

    public void EscogeFormaDescarga() {
        int attempts = 0;
        boolean isChecked = false;
        waitP.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
        while (attempts < 3) {
            try {
                WebElement opcionfecha = waitP.until(ExpectedConditions.elementToBeClickable(linkfecha));
               String Check= opcionfecha.getAttribute("checked");
                opcionfecha.click();

                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                js.executeScript("arguments[0].checked = true;", opcionfecha);

                waitP.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

                if(Check!=null){
                    System.out.println("Radio button seleccionado correctamente.");
                    isChecked = true;
                    break;
                } else {
                    System.out.println("Radio button no seleccionado, reintentando...");
                }
            } catch (Exception e) {
                System.out.println("Error al intentar hacer clic en el radio button: " + e.getMessage());
            }
            attempts++;
        }

        if (!isChecked) {
            System.out.println("No se pudo seleccionar el radio button después de " + attempts + " intentos.");
        }

        WebElement BotoningresoFecha = wait.until(ExpectedConditions.elementToBeClickable(botonIngresar));
        BotoningresoFecha.click();

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
        WebElement BotonAcepta = wait.until(ExpectedConditions.elementToBeClickable(botonAceptar));
        BotonAcepta.click();

    }
    public void reporteAplicacion(){
        WebElement opcionreporte = waitP.until(ExpectedConditions.elementToBeClickable(linkreporte));
        opcionreporte.click();

        WebElement Botoningresoimprimir = waitP.until(ExpectedConditions.elementToBeClickable(botonAceptar2));
        Botoningresoimprimir.click();

    }
    public void imprimirInforme() {

        String mainTab = webDriver.getWindowHandle();

        WebElement BotonImprimir = waitMensaje.until(ExpectedConditions.elementToBeClickable(botonImprimir));
        BotonImprimir.click();

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



}
