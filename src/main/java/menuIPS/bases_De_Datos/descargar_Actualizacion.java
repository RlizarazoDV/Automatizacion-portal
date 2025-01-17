package menuIPS.bases_De_Datos;
import Configuracion.basePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class descargar_Actualizacion extends basePage{
    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;

    private final By linkBaseDatos  = By.xpath("//*[contains(text(),'Bases de Datos')]");
    private final By linkDescargarA = By.xpath("//*[contains(text(),'Descargar Actualización')]");
    private final By fechaini=  By.xpath("//*[contains(text(),'Fecha Inicial')]//following::input[2]");
    private final By mes=  By.xpath("//*[contains(text(),'Fecha Inicial')]//following::select[1]");
    private final By año=  By.xpath("//*[contains(text(),'Fecha Inicial')]//following::select[2]");
    private final By fechafin=  By.xpath("//*[contains(text(),'Fecha Final')]//following::input[2]");
    private final By mesfin=  By.xpath("//*[contains(text(),'Fecha Final')]//following::select[1]");
    private final By añofin=  By.xpath("//*[contains(text(),'Fecha Final')]//following::select[2]");
    private final By boton =  By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src,'/Portal/imgs/btnAceptar.gif')]");
    private final By mensaje =  By.xpath("//*[contains(text(),'No hay resultados para mostrar')]");


    public descargar_Actualizacion(WebDriver webDriver) {
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
                .withTimeout(Duration.ofSeconds(40))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(java.util.NoSuchElementException.class);
    }
    public void ingresarBasesDeDatos()
    {
        WebElement linkBaseD = wait.until(ExpectedConditions.elementToBeClickable(linkBaseDatos));
        linkBaseD.click();
        WebElement linkdescargarAct = waitP.until(ExpectedConditions.elementToBeClickable(linkDescargarA));
        linkdescargarAct.click();

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

    public void fechaFin(String Diafin,String Mesfin,String Añofin){
        boolean success = false;
        int attempts = 0;

        WebElement linkfechafin = wait.until(ExpectedConditions.elementToBeClickable(fechafin));
        linkfechafin.click();
        Select linkañofin = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(añofin)));
        linkañofin.selectByVisibleText(Añofin);

        while (attempts < 3 && !success) {
            try {
                Select linkmesfin = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(mesfin)));
                linkmesfin.selectByVisibleText(Mesfin);
                WebElement linkdiafin = waitP.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'iceOutTxt') and contains(text(),'" + Diafin + "')]")));
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
    public void aceptar(){
        WebElement acepta = wait.until(ExpectedConditions.elementToBeClickable(boton));
        acepta.click();

    }
    public String  resultado(){
        WebElement resultado = waitP.until(ExpectedConditions.visibilityOfElementLocated(mensaje));
        String result = resultado.getText();
        return result;
    }

}
